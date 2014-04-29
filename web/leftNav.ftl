
<style>
    #feedback { font-size: 1.4em; }
    #leftNav .ui-selecting { background:url(css/cupertino/images/ui-bg_glass_50_3baae3_1x400.png) #3baae3 repeat-x 50% 50%; }
    #leftNav .ui-selected { background:url(css/cupertino/images/ui-bg_glass_50_3baae3_1x400.png) #3baae3 repeat-x 50% 50%;color: white; }
    #leftNav { list-style-type: none; margin: 0; padding: 0; width: 98%; }
    #leftNav li { margin: 3px; padding: 0.4em; font-size: 1.4em; height: 18px; }
</style>
<script>
$(function() {
	var leftNav = '';
	$.get('/member/nav' , null,
	function(data){
		for (key in data) {
			leftNav += '<li class="ui-widget-content" url="'+key+'">' + data[key] + '</li>';	
		}
		$('#leftNav').html(leftNav);
	},"json");

    $("#leftNav").selectable({
            selected: function(event, ui) {
                window.location = $(ui.selected).attr('url');
            }
    });
});
</script>
<div style="width:145px;height:600px;float: left;">
    <div class="ui-widget-content ui-corner-all" style="min-height: 600px;">
        <div >
        <ol id="leftNav">
        </ol>
        </div>
    </div>
</div>
