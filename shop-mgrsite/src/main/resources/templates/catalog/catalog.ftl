<link rel="stylesheet" href="/js/plugins/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="/js/plugins/ztree/js/jquery.ztree.core.js"></script>
<script src="/js/plugins/sortable/Sortable.min.js"></script>
<script type="text/javascript">

    var zTreeObj;

    var setting = {
        async: {
            enable: true,
            url:"/catalog/children",
            type: "get",
            autoParam:["id"]
        },
        callback:{
            onClick:getChildCatalog//点击某个分类树节点后执行的函数
        }
    };

    function getChildCatalog(event, treeId, treeNode) {
        $("#childCatalog").load("/catalog/children?id="+treeNode.id);
    }
    $(function(){
        zTreeObj = $.fn.zTree.init($("#catalogTree"), setting);

        //保存按钮
        $("#saveButton").click(function () {
            $("#catalogSaveForm").ajaxSubmit({
                type:"POST",
                success:function (data) {
                    if(data.code == 0){
                        $.messager.popup("保存成功");
                        refresh();
                    }else{
                        $.messager.alert("提示", data.msg);
                    }
                    $("#myModal").modal("hide");
                }
            })
        })

        //新增分类按钮
        $("#addButton").click(function () {
            editCatalog(null)
        })

    })

    //回显分类数据用于编辑分类
    function editCatalog(obj) {
        var json;
        //判断obj是否为null
        // 如果if表达式中的值是null、0、undefined就返回false，否则返回true
        if(!obj){
            //自定义json数据
            json = {"sn":"","isParent":0,"name":"","id":"","sort":0,"parentId":""}
        }else{
            //获取分类对象的属性
            json = $(obj).data("json");
        }

        $("#myModal").modal("show");
        //设置上级分类的名字
        var nodes = zTreeObj.getSelectedNodes();
//        if(nodes.length == 0) {
//            $.messager.alert("提示","请选择分类");
//        }
        $("#parentCatalog").val(nodes[0].name);
        //回显分类数据
        $("#id").val(json.id);
        $("#name").val(json.name);
        $("#sort").val(json.sort);
        $("#parentId").val(nodes[0].id);
        $("#isParent").val(json.isParent);
        $("#sn").val(json.sn);

    }

    //删除分类
    function deleteCatalog(catalogId) {
        $.messager.confirm("提示","是否确定删除",function () {
            $.ajax({
                url:"/catalog/"+catalogId,
                type: "DELETE",
                success:function (data) {
                    if(data.code == 0) {
                        $.messager.popup("删除成功");
                    }else{
                        $.messager.alert("提示",data.msg);
                    }
                    refresh();
                }
            })
        })

    }

    //更新一下界面（树结构，和子分类的div代码块）
    function refresh() {
        var nodes = zTreeObj.getSelectedNodes();
        if (nodes.length>0) {
            zTreeObj.reAsyncChildNodes(nodes[0], "refresh");
            $("#childCatalog").load("/catalog/children?id="+nodes[0].id);
        }
    }
</script>


<div class="container-fluid cm-container-white">
    <div class="row">
        <div class="col-sm-3">
            <div class="panel panel-default">
                <div class="panel-body">
                    <button type="button" class="btn btn-primary" id="addButton">添加分类</button>
                    <ul id="catalogTree" class="ztree"></ul>
                </div>
            </div>

        </div>
        <div class="col-sm-9" id="childCatalog">

        </div>
    </div>

    <!-- 添加分类模态框 -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">添加分类</h4>
                </div>
                <form action="/catalog" method="post" id="catalogSaveForm">
                    <input type="hidden" name="id" id="id"/>
                    <input type="hidden" name="sort" id="sort" value="1"/>
                    <input type="hidden" name="isParent" id="isParent" value="false"/>
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="exampleInputEmail1">上级分类</label> <input
                                type="hidden" name="parentId" id="parentId"/> <input
                                class="form-control" value="顶级分类" readonly="readonly"
                                id="parentCatalog">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">分类名</label> <input
                                class="form-control" name="name" id="name">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">分类编号</label> <input
                                class="form-control" name="sn" id="sn"/>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default"
                                    data-dismiss="modal">关闭
                            </button>
                            <button type="button" class="btn btn-primary" id="saveButton">保存</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
