/*
 *This javascript file is used to 
 *1. Add a record
 *2. Show the top ten records
 */
 
//Open the database connection  
var dbRecord; 
dbRecord = openDatabase('record','','local database',2*1024*1024);

/*
 *Add a record to the database
 */  
 function addRecord(name, score, time){
	var createSql="create table if not exists record(name text,score INTEGER, time INTEGER, date INTEGER)";
	var insertSql="insert into record values(?,?,?,?)"; 
	var date=new Date().getTime();
	dbRecord.transaction(function(tx)
	{  		
	 	tx.executeSql(createSql,[])		
		tx.executeSql(insertSql,[name,score,time,date]);	
    });
	
 }
 
 
/*
 *Show the top ten records
 */
 function showRecord(){
	var list = document.getElementById("rankingBoard"); 
	var createSql="create table if not exists record(name text,score INTEGER, time INTEGER, date INTEGER)";
	var showListSql="select * from record order by score desc,time,name,date";
	dbRecord.transaction(function(tx){
		tx.executeSql(createSql,[])	
		tx.executeSql(showListSql,[],function(tx,rs){
			if(rs.rows.length>0){
				var result="<table><tr><td class='recordText1' width='25%'>Name</td><td class='recordText1' width='25%'>Score</td><td class='recordText1' width='25%'>Time</td><td class='recordText1' width='25%'>Date</td></tr>";
				var rowsLength=rs.rows.length;				
				if(rs.rows.length>=10){
					rowsLength=10;
				}
				for(var i=0;i<rowsLength;i++){
					var row = rs.rows.item(i);
					var time=new Date();
					time.setTime(row.date);
					var dateTime=time.format("dd/MM/yyyy");
					result+="<tr><td class='recordText2' width='25%'>"+row.name+"</td><td class='recordText2' width='25%'>"+row.score+"</td><td align='center' class='recordText2' width='25%' >"+row.time+"</td><td class='recordText2' width='25%'>"+dateTime+"</td></tr>";
				}
				list.innerHTML=result;
			}
		});
	});	 
 }
 
/*
 *Change the date format
 */ 
Date.prototype.format = function(format)  
{  
    var o = {  
    "M+" : this.getMonth()+1, //month  
    "d+" : this.getDate(),    //day  
    "h+" : this.getHours(),   //hour  
    "m+" : this.getMinutes(), //minute  
    "s+" : this.getSeconds(), //second  
    "S" : this.getMilliseconds() //millisecond  
    }  
    if(/(y+)/.test(format)) format=format.replace(RegExp.$1,  
    (this.getFullYear()+"").substr(4 - RegExp.$1.length));  
    for(var k in o)if(new RegExp("("+ k +")").test(format))  
    format = format.replace(RegExp.$1,  
    RegExp.$1.length==1 ? o[k] :  
    ("00"+ o[k]).substr((""+ o[k]).length));  
    return format;  
}  