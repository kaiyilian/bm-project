<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2016/9/6
  Time: 19:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/operation/fk_good_manage.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/operation/fk_good_manage.js"></script>

<div class="fk_good_manage_container container">

    <div class="head border-bottom">
        <div class="txt">福库商品管理</div>
    </div>

    <div class="content">

        <div class="table_container">
            <table class="table table-striped table-bordered table-hover dataTable">
                <thead>
                <tr>
                    <td>缩略图</td>
                    <td>轮播图</td>
                    <td>商品名</td>
                    <td>活动价</td>
                    <td>市场价</td>
                    <td>商品详情</td>
                    <td>是否上架</td>
                    <td>操作</td>
                </tr>
                </thead>
                <tbody>
                <%--<tr class="item fk_good_item">--%>
                <%--<td class="good_thumb_img">--%>
                <%--<img src="">--%>
                <%--</td>--%>
                <%--<td class="good_shuffling_img">轮播图</td>--%>
                <%--<td class="good_name">商品名</td>--%>
                <%--<td class="good_deal_price" data-price="12">￥12</td>--%>
                <%--<td class="good_marketed_price" data-price="35">￥35</td>--%>
                <%--<td class="good_detail">商品详情</td>--%>
                <%--<td class="good_is_enable">已上架</td>--%>
                <%--<td class="good_operate">--%>

                <%--<span class="btn btn-sm btn-primary"--%>
                <%--onclick="fk_good_manage.goodModifyModalShow(this)">编辑</span>--%>
                <%--<span class="btn btn-sm btn-danger" onclick="fk_good_manage.goodDel(this)">删除</span>--%>

                <%--</td>--%>
                <%--</tr>--%>
                </tbody>
            </table>
        </div>

    </div>

    <div class="foot">
        <div class="btn_list">
            <div class="btn btn-sm btn-primary btn_add" onclick="fk_good_manage.goodAddModalShow()">新增</div>
        </div>
    </div>

    <div class="pager_container">
        <ul class="pagenation" style="float:right;"></ul>
    </div>

</div>

<div class="modal fade good_info_modal" style="background-color:rgba(0,0,0,0.50);overflow-y:auto; ">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新增商品信息</h4>
            </div>
            <div class="modal-body">

                <%--<input type="file" class="img_upload" id="img_upload" onchange="">--%>

                <div class="row margin_b_10">
                    <div class="col-xs-12">

                        <span class="col-xs-2">缩略图</span>

                        <span class="col-xs-10 thumb_img">
							<label class="thumbnail">
								<img data-src="holder.js/100%x180" alt="...">
							</label>
						</span>

                    </div>
                </div>

                <div class="row margin_b_10">
                    <div class="col-xs-12">

                        <span class="col-xs-2">轮播图</span>

                        <span class="col-xs-10 shuffling_img_list">
							<a href="#" class="thumbnail col-xs-6">
								<img data-src="holder.js/100%x180" alt="...">
							</a>
							<a href="#" class="thumbnail col-xs-6">
								<img data-src="holder.js/100%x180" alt="...">
							</a>
							<a href="#" class="thumbnail col-xs-6">
								<img data-src="holder.js/100%x180" alt="...">
							</a>
							<a href="#" class="thumbnail col-xs-6">
								<img data-src="holder.js/100%x180" alt="...">
							</a>
						</span>

                    </div>
                </div>

                <div class="row margin_b_10">
                    <div class="col-xs-12">

                        <span class="col-xs-2 line-height-34">商品名称</span>

                        <span class="col-xs-10">
							<input type="text" class="form-control good_name"
                                   placeholder="请输入商品名称" maxlength="20"/>
						</span>

                    </div>
                </div>

                <div class="row margin_b_10">
                    <div class="col-xs-12">

                        <span class="col-xs-2 line-height-34">品牌</span>

                        <span class="col-xs-10">
							<input type="text" class="form-control good_brand"
                                   placeholder="请输入品牌名称" maxlength="32"/>
						</span>

                    </div>
                </div>

                <div class="row margin_b_10">
                    <div class="col-xs-12">

                        <span class="col-xs-2 line-height-34">活动价</span>

                        <span class="col-xs-10">
							<input type="text" class="form-control good_deal_price"
                                   placeholder="请输入活动价(小数点后两位)"
                                   onkeyup="if(isNaN(value))execCommand('undo')"/>
						</span>

                    </div>
                </div>

                <div class="row margin_b_10">
                    <div class="col-xs-12">

                        <span class="col-xs-2 line-height-34">市场价</span>

                        <span class="col-xs-10">
							<input type="text" class="form-control good_marketed_price"
                                   placeholder="请输入市场价(小数点后两位)"
                                   onkeyup="if(isNaN(value))execCommand('undo')"/>
						</span>

                    </div>
                </div>


                <div class="row margin_b_10">

                    <div class="col-xs-12">

                        <div class="col-xs-2 line-height-34">库存</div>

                        <div class="col-xs-10">
                            <input type="number" class="form-control good_stock"
                                   placeholder="请输入库存" min="0" max="999999"
                                   onblur="fk_good_manage.checkGoodStock(this)"/>
                        </div>

                    </div>

                </div>

                <div class="row margin_b_10">

                    <div class="col-xs-12">

                        <div class="col-xs-2 line-height-34">最大购买量</div>

                        <div class="col-xs-10">
                            <input type="number" class="form-control good_buy_limit"
                                   placeholder="请输入最多购买量" min="0" max="999999"
                                   onblur="fk_good_manage.checkGoodBuyCount(this)"/>
                        </div>

                    </div>

                </div>

                <div class="row margin_b_10">
                    <div class="col-xs-12">

                        <span class="col-xs-2 line-height-34">发货时间描述</span>

                        <span class="col-xs-10">
							<input type="text" class="form-control delivery_time_desc"
                                   placeholder="请输入发货时间" maxlength="32"/>
						</span>

                    </div>
                </div>

                <div class="row margin_b_10">
                    <div class="col-xs-12">

                        <span class="col-xs-2 line-height-34">分类选择</span>

                        <span class="col-xs-10">

							<span class="category_list">
								<span class="item btn btn-sm btn-default" data-id="color"
                                      onclick="fk_good_manage.getCategoryInfo(this)">颜色</span>
								<span class="item btn btn-sm btn-default" data-id="size">大小</span>
							</span>

						</span>

                    </div>
                </div>

                <div class="row margin_b_10">
                    <div class="col-xs-12">

                        <span class="col-xs-2 line-height-34">商品详情</span>

                        <span class="col-xs-10">
							<textarea class="form-control good_info_detail" placeholder="请输入商品详情"
                                      maxlength="140"></textarea>
						</span>

                    </div>
                </div>

                <div class="row margin_b_10">
                    <div class="col-xs-12">

                        <span class="col-xs-2 line-height-34">是否启用</span>

                        <span class="col-xs-10">
							<div class="good_is_enable" data-is_enable="true">
								<div class="togglebutton">
									<label>
										<input type="checkbox"/>
										<span class="toggle"></span>
									</label>
								</div>
							</div>
						</span>

                    </div>
                </div>


            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="fk_good_manage.goodInfoSave();">保存</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="modal fade category_info_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">分类</h4>
            </div>
            <div class="modal-body">
                <div class="btn btn-primary btn_clear" onclick="fk_good_manage.clearUnitByChoosed()">清空</div>
                <div class="unit_list">

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="fk_good_manage.unitSave()">保存</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

