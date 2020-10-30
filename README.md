# ipc_router_demo


|方案 | 业务使用 | 跨进程媒介 |框架实现 | demo分支 | 
|:---:|:---:|:---:|:---:|:---:|
| 1 | 需要java + aidl接口双实现、有一定学习成本 | contentprovider.call + aidl| 简单 | `router_and_contentprovider_with_aidl`|
| 2 |无需aidl接口，使用简单 | contentprovider.call | 复杂 | `router_and_contentprovider_without_aidl `|
| 3 |无需aidl接口，使用简单 | contentprovider.query | 很复杂 | `router_and_contentprovider_without_aidl_query `|





