<%
String callback= request.getParameter("callback");
%>
<%=callback %>({"counters":{"mysql: findTypeDictionary":5,"RPC:TotalRequestsReceived":5,"request:findTypeDictionary":5,"total_stats_requests":1726,"zk hit: findTypeDictionary":5},"jvm":{"jvm_gc_ConcurrentMarkSweep_cycles":0,"jvm_nonheap_used":25072640,"jvm_heap_committed":2120679424,"jvm_gc_ConcurrentMarkSweep_msec":0,"jvm_thread_count":125,"jvm_uptime":4164472,"jvm_post_gc_used":136053784,"jvm_gc_ParNew_cycles":3,"jvm_num_cpus":2,"jvm_thread_daemon_count":9,"jvm_gc_ParNew_msec":135,"jvm_heap_max":2120679424,"jvm_nonheap_max":136314880,"jvm_gc_cycles":3,"jvm_gc_msec":135,"jvm_nonheap_committed":25493504,"jvm_thread_peak_count":125,"jvm_heap_used":110981144,"jvm_start_time":1330937602537},"gauges":{},"labels":{},"piegrams":{"jvm_heap":[{"label":"used","data":1.10981144E8},{"label":"free","data":2.00969828E9}],"jvm_nonheap":[{"label":"used","data":2.507264E7},{"label":"free","data":1.1124224E8}]},"distributions":{"jvm_heap":{"free":2.00969828E9,"used":1.10981144E8},"jvm_nonheap":{"free":1.1124224E8,"used":2.507264E7}}})