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
    setEditorText(textarea.val());
    editor.getSession().on('change', function(){
        textarea.val(editor.getSession().getValue());
    });
    editor.setTheme("ace/theme/github");
    var SqlServerMode = ace.require("ace/mode/sqlserver").Mode;
    editor.getSession().setMode(new SqlServerMode());
};

getEditorText = function () {
    var editor = ace.edit("sql_ace");
    return editor.getValue();
};

setEditorText = function (content) {
    var editor = ace.edit("sql_ace");
    editor.setValue(content);
    editor.gotoLine(0, 0, 0);
};