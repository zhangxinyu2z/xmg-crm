/**
 * Created by vnruxc on 16-9-6.
 */

var netdiskDatagrid,netdiskForm,netdiskDialog,netdiskSearchform,uploadForm;
var editIndex = undefined;

$(function () {
    netdiskDatagrid=$("#datagrid_netdisk");
    netdiskForm=$("#form_netdisk");
    uploadForm=$("#form_upload");
    netdiskDialog=$("#dialog_netdisk");
    netdiskSearchform=$("#searchform_netdisk");

    netdiskDatagrid.datagrid({
        singleSelect: true,
        toolbar: "#tb"

    });
    //========================

    //绑定事件 按钮的点击事件
    $("[data-cmd]").on("click",function(){
        //console.log($(this).data("cmd"));
        var cmd=$(this).data("cmd");
        cmds[cmd]();
    } );

    //绑定文件上传控件的change事件 用户选择文件后就就提交上传表单
    $("#file_upload").on("change",function () {
        uploadForm.form("submit", {
            url: '/netdisk_upload',
            onSubmit: function (param) {
                //获得上传的目录的id
                var pid=$("#currentDirId").val();
                 param.pid = pid;
            },
            success: function (data) {
                data = $.parseJSON(data);
                $.messager.alert('提示', data.msg, 'info', function () {
                   // console.log(data);
                    netdiskDatagrid.datagrid("reload");
                });
            }
        });

    })


});
//============根据文件类型 返回相应的图标
function fileTypeFormatter(value, row, index) {
    if(row.dir){
        return "<img src='/filetype/folder.jpg'>";
    }else{
                return "<img src='/filetype/customwiz.ico'>";
    }

}

// 返回用户的文件名
function usernameFormatter(value, row, index) {

    if (value) {
        return value.username;
    } else {
        return null;
    }
}


var cmds;
cmds = {
    //查询操作
    search: function () {
        var ps = netdiskSearchform.serializeArray();
        var jsonparam = {};
        for (var i = 0; i < ps.length; i++) {
            // console.log(ps[i]);
            jsonparam[ps[i].name] = ps[i].value;
        }
        netdiskDatagrid.datagrid("load", jsonparam);
    },

    //打开上传控件
    upload: function () {
        console.log("upload");
       $("#file_upload").trigger("click");

    },
    //点击文件名单元格 进行重命名操作
    rename: function () {
      console.log("rename");
        var row = netdiskDatagrid.datagrid("getSelected");
        var index=netdiskDatagrid.datagrid("getRowIndex",row);
        var field="name";
        clickCell(index, field);

    },


    //删除
   /* del: function () {
        var row = netdiskDatagrid.datagrid("getSelected");
        if (row) {
            $.messager.confirm('提示', "你确定要删除码?", function (r) {
                if (r) {
                    $.get("/netdisk_delete", {id: row.id}, function (res) {
                        $.messager.alert('提示', res.msg, 'info', function () {
                            netdiskDatagrid.datagrid("reload");
                        })
                    });
                }
            });

        } else {
            $.messager.alert('提示', "请选择要删除的数据", 'info')
        }
    },*/
    //刷新操作
    reload: function () {
        netdiskDatagrid.datagrid("reload");
    },


    //取消
    cancel: function () {
        netdiskDialog.dialog("close");
    },

    //新建文件夹
    mkdir:function () {
        var rowData={};
        rowData.name="新建文件夹";
        rowData.uploadtime="--";
        rowData.user={name:"--"};
        rowData.dir=true;
        netdiskDatagrid.datagrid("appendRow",rowData);

        var index=netdiskDatagrid.datagrid("getRows").length-1;
        netdiskDatagrid.datagrid("selectRow",index);
        netdiskDatagrid.datagrid('editCell', {index:index,field:"name"});
        editIndex = index;

    },
    //返回根目录
    toRoot:function () {
        netdiskDatagrid.datagrid("load",{pid:null});

    },

    //返回上级目录
    toParent:function () {
        var currentId=$("#currentDirId").val();
        netdiskDatagrid.datagrid("load",{currentId:currentId});
        var data=netdiskDatagrid.datagrid("getData");//拿到服务器响应的数据
        $("#currentDirId").val(data.data.currentId);  //拿到数据中的当前目录的id 添加到页面的隐藏域  用于返回上级操作

    },
    //下载
    download:function () {
        //在内存中定义一个form 添加到文档中 在填充相关数据 提交表单
        var row=netdiskDatagrid.datagrid("getSelected");
        if(row&&!row.dir){
            var form=$("<form>");
            form.attr("style","display:none");
            form.attr("target","");
            form.attr("method","post");
            form.attr("action","/netdisk_download");
            var input1=$("<input>");
            input1.attr("type","hidden");
            input1.attr("name","id");
            input1.attr("value",row.id);
            $("body").append(form);
            form.append(input1);
            form.submit();//表单提交
        }
    }

};


$.extend($.fn.datagrid.methods, {
    editCell: function(jq,param){
        return jq.each(function(){
            var opts = $(this).datagrid('options');
            var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor1 = col.editor;
                if (fields[i] != param.field){
                    col.editor = null;
                }
            }
            $(this).datagrid('beginEdit', param.index);
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor = col.editor1;
            }
        });
    }
});


//结束编辑操作
function endEditing(){
    if (editIndex == undefined){return true}
    if (netdiskDatagrid.datagrid('validateRow', editIndex)){
        netdiskDatagrid.datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}

//点击单元格事件
function clickCell(index, field){

    netdiskDatagrid.datagrid('selectRow', index);
    if (endEditing()){
        //点击了name单元格 重命名
        if(field=="name"){
            netdiskDatagrid.datagrid('editCell', {index:index,field:field});
            editIndex = index;
        }else if(field=="type"){

            //点击了文件类型单元格  如果是文件夹则打开
            netdiskDatagrid.datagrid('selectRow', index);
            var row=netdiskDatagrid.datagrid("getSelected");
            if(!row.dir){
                return;
            }
            var param={};
            var pid=row.id;
            param.pid=pid;
            netdiskDatagrid.datagrid("load", param);
            var cu=$("#currentDirId").val();
            //将当前目录的id修改为请求的id
            $("#currentDirId").val(pid);
            $("#pid").val(cu);


        }
    }
}


//结束编辑后的操作：提交发生更改的内容到数据库
function afterEdit(rowIndex, rowData, changes) {
    if($.isEmptyObject(changes)){
        return;
    }
    var url = "/netdisk_mkdir";
    if (rowData.id) {
        var id=rowData.id;
        var newname=rowData.name;
        url="/netdisk_rename?id="+id+"&name="+newname;
    }else{
        var name=rowData.name;
        var pid=$("#currentDirId").val();
        url="/netdisk_mkdir?name="+name+"&pid="+pid;
    }
    $.get(url,function (data) {
        netdiskDatagrid.datagrid("reload");
    })

}

