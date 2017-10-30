if (window.console) {
    console.log("Welcome to your Play application's JavaScript!");
}

$(document).ready(function () {
    initEditor();
});

initEditor = function () {
    var editor = ace.edit("sql_ace");
    editor.setOptions({
        //maxLines: Infinity
        // fontFamily: "Inconsolata",
        //fontFamily: "Open sans",
        //fontFamily: "Source Code Pro",
        // fontSize: "10pt",
        wrap: true
    });
    var textarea = $('textarea[id="sql"]').hide();
    setEditorText(editor, textarea.val());
    editor.getSession().on('change', function(){
        textarea.val(editor.getSession().getValue());
    });
    editor.setTheme("ace/theme/github");
    editor.commands.addCommand({
        name: 'save',
        bindKey: {win: "Ctrl-Enter", "mac": "Cmd-Enter"},
        exec: function(editor) {
            textarea.parents('form').submit();
        }
    })
    var SqlServerMode = ace.require("ace/mode/sqlserver").Mode;
    editor.getSession().setMode(new SqlServerMode());


    var jsonViewer = ace.edit("jsonEditor");
    jsonViewer.setOptions({
        //maxLines: Infinity
        // fontFamily: "Inconsolata",
        //fontFamily: "Open sans",
        //fontFamily: "Source Code Pro",
        // fontSize: "10pt",
        wrap: true
    });
    setEditorText(jsonViewer, $('#json').html());
    $('#json').hide();
    jsonViewer.setTheme("ace/theme/github");
    var jsonMode = ace.require("ace/mode/json").Mode;
    jsonViewer.getSession().setMode(new jsonMode());
};

getEditorText = function () {
    var editor = ace.edit("sql_ace");
    return editor.getValue();
};

setEditorText = function (editor, content) {
    editor.setValue(content);
    editor.gotoLine(0, 0, 0);
};