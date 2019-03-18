<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="Head.jsp" %>
<%
String Start = request.getParameter("Start");//起始数
String Termination = request.getParameter("Termination");//终止数
String Number = request.getParameter("Number");//中奖数


int iStart = Integer.parseInt(Start, 10);
int iTermination = Integer.parseInt(Termination, 10);
int iNumber = Integer.parseInt(Number, 10);

int i=0,j=0,g=0;
int[] SJ=new int[iNumber];
String SJS=new String();
while(i<iNumber){
	j=(int)(Math.round(Math.random()*(iTermination-iStart)+iStart));
	boolean flag = true;  
    for (g= 0; g < iNumber; g++) {  
        if(j == SJ[g]){  
            flag = false;  
            break;  
        }  
    } 
    if(flag){
			SJ[i]=j;
			i++;
    
    }
		
	
	
}



for(j=0;j<iNumber;j++){
	SJS=SJS+SJ[j]+"    ";
}



 %>
 
<body>
<div style="font-size:24px;color:#4f2f4f,font-family:宋体;">
<%=SJS%>

</div>
</body>
<%@include file="Foot.jsp" %>