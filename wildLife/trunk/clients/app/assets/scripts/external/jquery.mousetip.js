/**
* plugin: jquery.BA.ToolTip.js
* author: kt.cheung @ Brandammo
* website: www.brandammo.co.uk
* version: 1.0
* date: 14th july 2012
* description: brandammo custom tool tip

Copyright (c) 2012 KT Cheung

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

**/

(function($){

  $.fn.BAToolTip = function(options) {  
  
	//set up default options 
	var defaults = { 
		tipClass: 'tip', //the class name for your tip
		tipFadeEasing: 'easeOutQuint', //easing method
		tipFadeDuration: 200, //fade duration
		tipOpacity: 1, //opacity of tip when mouseover
		tipOffset: 10 //offset the tip relative to mouse cursor in pixels
	}; 
  	
	var opts = $.extend({}, defaults, options); 	

    return this.each(function() {  
	  var $this = $(this);
	  
	  $this.mousemove(function(e){
	  	var parentElementOffset = $this.parent().offset();
	  	var xPos = e.pageX - parentElementOffset.left;
   		var yPos = e.pageY - parentElementOffset.top;
   		$(this).parent().find('.'+opts.tipClass).css({'top': yPos+opts.tipOffset, 'left' : xPos+opts.tipOffset});
	  	$(this).parent().find('.'+opts.tipClass).css('display', 'block').stop().animate({opacity:opts.tipOpacity},{duration:opts.tipFadeDuration, easing: opts.tipFadeEasing});
	  })
	  
	  $this.mouseout(function(){
	  	$('.'+opts.tipClass).stop().animate({opacity:0},{duration:opts.tipFadeDuration, easing: opts.tipFadeEasing, complete: function(){ $(this).css('display', 'none') } });
	  })
	 
    });
  };
})(jQuery);