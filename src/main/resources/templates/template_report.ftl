<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Order Book on ${date}</title>
</head>
<body>
   <h2>Order Book on ${time}</h2>

<table border="1">
  <tr bgcolor="grey">
    <th width="50">Price</th>
    <th width="50">Size</th>
  </tr>

<#assign name>
  ${bestAskPrice}
</#assign>


  <#list orders as key, order>
     <tr class="tableBody">
         <#assign color="yellow">
         <#if order.typeOrder == "ASK">
           <#assign color="red">
         <#elseif order.typeOrder == "BID">
             <#assign color="green">
         </#if>


         <td style='background-color:${color}' align="right">${key}</td>
         <td style='background-color:${color}' align="center">${order.size}</td>
     </tr>
  </#list>
</table>

<p>${comment}</p>
<a href ="https://www.linkedin.com/in/mykola-nick-skorobahatyi-00731b160/">${developer}</a>
</a>

</body>
</html>