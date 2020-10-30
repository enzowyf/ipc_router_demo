# ipc_router_demo


|方案 | 业务使用 | 跨进程媒介 |性能表现|框架实现 |
|:---:|:---:|:---:|:---:|:---:|
| 1 | 需要java + aidl接口双实现、有一定学习成本 | contentprovider.call + aidl/binder | * 非ipc调用无影响 </br> * ipc调用约3ms | 借助binder，实现简单 | 
| 2 |无需aidl接口，使用简单 | contentprovider.call作为万能接口 |  * 非ipc调用无影响 </br> * ipc调用约6ms（bundler耗时） | 需要跨进程方法的map查表，实现复杂 | 
| 3 |无需aidl接口，使用简单 | contentprovider.query作为万能接口 |  * 非ipc调用无影响 </br> * ipc调用约20ms（cursor带来两次ipc） | 在方案2的基础上，还需要处理cursor，非常复杂 | 




* `router_and_contentprovider_with_aidl` -- 方案1
*  `router_and_contentprovider_without_aidl ` -- 方案2
*  `router_and_contentprovider_without_aidl_query ` -- 方案3
