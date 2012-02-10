var LaserLogger = {
	
	_url: 'http://laserlogger.appspot.com/logger',
	
	_channel: '',
	
	_pusher: null,
	
	/**
	 * Override console.log function if condition is met.
	 * @param {string} channel
	 * @param {function: boolean} when When does logger start?
	 */
	start: function (channel, when) {
		if (typeof when !== 'function') {
			when = function () {
				return true;
			}
		}
		this._channel = channel;
		
		if (when()) {
			this._overrideConsole();
			this._initPusher();
		}
	},
	
	stop: function () {
		
	},
	
	_overrideConsole: function () {
		var self = this;
		window.console = {
			log: function () {
				self._log.apply(self, arguments);
			}
		};
	},
	
	_initPusher: function () {
		this._pusher = new Pusher('d03a114b02555f531883');
		
	    var channel = this._pusher.subscribe(this._getLoggerChannel());
		var self = this;
	    channel.bind('log_event', function(data) {
			// data: { command: {String} }
	    	try {
				eval(data.command);
			} catch (e) {
				if (typeof e === 'string') {
					self._log(e);
				} else {
					self._log(e.message);
				}
			}
	    });
	},
	
	_getLoggerChannel: function () {
		return this._channel + '@logger';
	},
	
	_getViewerChannel: function () {
		return this._channel + '@viewer';
	},
	
	/**
	 * @param {...Object} args
	 */
	_log: function () {
		var data = {};
		for (var i = 0; i < arguments.length; i++) {
			data['arg' + i] = arguments[i];
		}
		
		var src = [
			this._url,
			'?channel=' + this._getViewerChannel(),
			'&t=' + new Date().getTime(),
			'&data=' + JSON.stringify(data)			
		].join('');

		new Image().src = src;
	}
	
};
