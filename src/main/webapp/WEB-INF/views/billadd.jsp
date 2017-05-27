<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="common/head.jsp"%>

<div class="right">
     <div class="location">
         <strong>你现在所在的位置是:</strong>
         <span>订单管理页面 >> 订单添加页面</span>
     </div>
     <div class="providerAdd">
         <form:form id="billForm" name="billForm" modelAttribute="bill" method="post" action="${pageContext.request.contextPath }/bill/addbill">
             <!--div的class 为error是验证错误，ok是验证成功-->
             <!-- <input type="hidden" name="method" value="add"> -->
             <div class="">
                 <label for="billCode">订单编码：</label>
                 <form:input path="billcode" name="billCode" class="text" id="billCode" value=""/> 
				 <!-- 放置提示信息 -->
				 <font color="red"></font>
             </div>
             <div>
                 <label for="productName">商品名称：</label>
                 <form:input path="productname" name="productName" id="productName" value=""/> 
				 <font color="red"></font>
             </div>
             <div>
                 <label for="productUnit">商品单位：</label>
                 <form:input path="productunit" name="productUnit" id="productUnit" value=""/> 
				 <font color="red"></font>
             </div>
             <div>
                 <label for="productCount">商品数量：</label>
                 <form:input path="productcount" name="productCount" id="productCount" value=""/> 
				 <font color="red"></font>
             </div>
             <div>
                 <label for="totalPrice">总金额：</label>
                 <form:input path="totalprice" name="totalPrice" id="totalPrice" value=""/> 
				 <font color="red"></font>
             </div>
             <div>
                 <label >供应商：</label>
                 <select name="providerid" id="providerId">
		         </select>
				 <font color="red"></font>
             </div>
             <div>
                 <label >是否付款：</label>
                 <input type="radio" name="ispayment" value="1" checked="checked">未付款
				 <input type="radio" name="ispayment" value="2" >已付款
             </div>
             <div class="providerAddBtn">
                  <input type="button" name="add" id="add" value="保存">
				  <input type="button" id="back" name="back" value="返回" >
             </div>
         </form:form>
     </div>
 </div>
</section>
<%@include file="common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/billadd.js"></script>