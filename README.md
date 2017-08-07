# logdemo
1. 数据自动脱敏
2. 数据手工脱敏或加密
3. log4j log4j2 logback 准备50条线程同时记录1000000条数据，然后统计时间, 测试结果:  
**log4j:**  
syn 13706ms  12187ms 13478ms 14694ms  
asyn 7820ms 8507ms 10530ms 10352ms  
**log4j2:**  
syn 18273ms  17382ms 17188ms 17005ms  
asyn 4144ms 4325ms 4211ms 4338ms  
**logback:**  
syn 15214ms 14225ms 16312ms 14304ms  
asyn 10568ms 10339ms 10535ms 10429ms
