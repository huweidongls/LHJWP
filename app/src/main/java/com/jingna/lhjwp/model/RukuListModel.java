package com.jingna.lhjwp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/10/31.
 */

public class RukuListModel implements Serializable {

    /**
     * xmList : [{"S_SJ":"201805","S_XMMC":"","B3139":68997,"S_TAB":"S812_2018_TZ_RKSQ","S_CODE":"301233238230110001","S_UP_FLAG":"0","S_CORP_UUID":"20170401155524087","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"1","S_ADDRESS":"电塔街"},{"S_SJ":"201805","S_XMMC":"","B3139":10721,"S_TAB":"S812_2018_TZ_RKSQ","S_CODE":"002301296230110005","S_UP_FLAG":"0","S_CORP_UUID":"20170508150313973","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"1","S_ADDRESS":"远香街"},{"S_SJ":"201805","S_XMMC":"","B3139":11000,"S_TAB":"S812_2018_TZ_RKSQ","S_CODE":"301195470230162002","S_UP_FLAG":"0","S_CORP_UUID":"20170510143538255","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"1","S_ADDRESS":"规划208路以北、规划209路以东、A-01-14地块以西地块内"},{"S_SJ":"201805","S_XMMC":"","B3139":117187,"S_TAB":"S812_2018_TZ_RKSQ","S_CODE":"781348105230125005","S_UP_FLAG":"0","S_CORP_UUID":"20170411091804010","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"1","S_ADDRESS":""},{"S_SJ":"201805","S_XMMC":"哈尔滨站北广场地下枢纽交通通道\u2014兆麟街、地段街地下通道","B3139":9659,"S_TAB":"S812_2018_TZ_RKSQ","S_CODE":"002276836230102802","S_UP_FLAG":"0","S_CORP_UUID":"cc7c8fbb99654caa9017031c97c779a0","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"1","S_ADDRESS":"兆麟街地下通道南起哈站北广场地下一层停车场，北至霁虹桥与兆麟街交叉口地面道路。地段街地下通道南起哈站北广场地下一层停车场，北至地段街地面道路。"},{"S_SJ":"201809","S_XMMC":"润禾\u2014香都城","B3139":30589,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"MA19EYFA3001","S_UP_FLAG":"0","S_CORP_UUID":"a2ec34ddbdcb4f23a74059bb5b265910","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"联草街"},{"S_SJ":"201809","S_XMMC":"华润置地公馆项目","B3139":86366,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"086040643002","S_UP_FLAG":"0","S_CORP_UUID":"e09ae55e7d54460d8c1832095b46fbf6","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"发展大道"},{"S_SJ":"201809","S_XMMC":"哈尔滨市松北区汇锦华庭项目","B3139":23000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"MA19EKRMX001","S_UP_FLAG":"0","S_CORP_UUID":"a26f78424a00402fb7500ec0e21f3240","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"鑫源街"},{"S_SJ":"201809","S_XMMC":"汇宏金融港项目","B3139":120000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"558274350001","S_UP_FLAG":"0","S_CORP_UUID":"85b639212898409b89b7b8cb138b9dbb","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"科技创新城科技一"},{"S_SJ":"201809","S_XMMC":"生态园公寓住宅楼","B3139":12000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"MA18XYEX8001","S_UP_FLAG":"0","S_CORP_UUID":"79432607895a43878ace87b404997688","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"兴胜"},{"S_SJ":"201809","S_XMMC":"嘉和城（二期）","B3139":12500,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"300884848003","S_UP_FLAG":"0","S_CORP_UUID":"1ea69416481f4261b3808af3eaf1a6ce","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"尚志大街"},{"S_SJ":"201809","S_XMMC":"袁家屯改造工程四期DC30、DC23、DC22、26、27、28、DC31号楼","B3139":48390,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"749502467006","S_UP_FLAG":"0","S_CORP_UUID":"1f56cb8344894736840e26ab27795599","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"学院"},{"S_SJ":"201809","S_XMMC":"哈尔滨林达外语城建设项目","B3139":303515,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"MA18Y21F6001","S_UP_FLAG":"0","S_CORP_UUID":"4e8e27cbf55c46d1952d2ea7f6020d26","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"学院"},{"S_SJ":"201809","S_XMMC":"永泰郦郡项目","B3139":193356,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"MA19MLA08001","S_UP_FLAG":"0","S_CORP_UUID":"81c25b8e792a41ef909809bf9767d825","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"世茂大道"},{"S_SJ":"201809","S_XMMC":"君豪哈尔滨义乌小商品城项目","B3139":132000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"565421427001","S_UP_FLAG":"0","S_CORP_UUID":"5f19dd65ceed415fac3406fd6b2b1945","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"春天大道"},{"S_SJ":"201809","S_XMMC":"中国工艺文化创意园·黑龙江（C-08-01地块）","B3139":291101,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"300959459001","S_UP_FLAG":"0","S_CORP_UUID":"183f4d2d79cc420da78b971f38954f18","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"群力第一大道、龙新路"},{"S_SJ":"201809","S_XMMC":"招商贝肯山E05 E06 E07 E10 E11地块","B3139":153932,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"598457629004","S_UP_FLAG":"0","S_CORP_UUID":"265b2952f7c24340bd184a6af92e7707","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"友谊西路丽江路交汇"},{"S_SJ":"201809","S_XMMC":"兰河新城一期-1（2号、3号、4号楼）","B3139":8500,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"702905410009","S_UP_FLAG":"0","S_CORP_UUID":"bfe85bcadcf14097b1b8073bc56c9686","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"河沿"},{"S_SJ":"201809","S_XMMC":"哈尔滨毅腾商都项目（地中海阳光二期b阶段）","B3139":48572,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"731360456005","S_UP_FLAG":"0","S_CORP_UUID":"5c57bd8cd39f434ea75066d75c6133af","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"世茂大道"},{"S_SJ":"201809","S_XMMC":"尚层峰景项目","B3139":42659,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"736941989001","S_UP_FLAG":"0","S_CORP_UUID":"27230a124d534e618d2fc74389e5720a","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"哈西大街"},{"S_SJ":"201809","S_XMMC":"哈尔滨合力投资控股有限公司大数据产业园项目","B3139":96000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"127047025001","S_UP_FLAG":"0","S_CORP_UUID":"b36cd08c78e74c79919865929d0f2eb0","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"滨河大道南侧哈南五路西南侧"},{"S_SJ":"201809","S_XMMC":"鲁商。松江新城6号地块项目","B3139":163100,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"565417954004","S_UP_FLAG":"0","S_CORP_UUID":"422e40a67f7e4708be2eb8e5e8599547","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"学府路368号"},{"S_SJ":"201809","S_XMMC":"鲁商.松江新城1号地块项目","B3139":42400,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"565417954003","S_UP_FLAG":"0","S_CORP_UUID":"3df4cabd57d2442a94bb6d7472cf665c","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"学府路368号"},{"S_SJ":"201809","S_XMMC":"爱达尊御项目","B3139":81447,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"MA18Y3P67001","S_UP_FLAG":"0","S_CORP_UUID":"72d2e56cb7a94e4a93cfcf2467734c49","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2"},{"S_SJ":"201809","S_XMMC":"金鼎华庭小区项目","B3139":13000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"552616428001","S_UP_FLAG":"0","S_CORP_UUID":"89a12d66e14c4ec59370d39a64e30c7d","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"健康路97号"},{"S_SJ":"201809","S_XMMC":"哈尔滨市香坊区远创樾府二期项目","B3139":68113,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"551330444001","S_UP_FLAG":"0","S_CORP_UUID":"638390bdd6614f198b73718b423d6e47","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"公滨路"},{"S_SJ":"201809","S_XMMC":"双城区浩宁康桥郡小区一期项目","B3139":14007,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"69682826X801","S_UP_FLAG":"0","S_CORP_UUID":"8a6b6ad3531446af9fd473d0e18be61e","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"诚祥"},{"S_SJ":"201809","S_XMMC":"哈尔滨市双城区伯爵公馆项目","B3139":15000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"672134490801","S_UP_FLAG":"0","S_CORP_UUID":"e5c4393f772a4cf68ecb61022cf27fa5","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"承旭"},{"S_SJ":"201809","S_XMMC":"盛隆国际小区六期工程","B3139":13000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"300894675006","S_UP_FLAG":"0","S_CORP_UUID":"362810b47759459f9fdc14cae9a59215","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"黎明"},{"S_SJ":"201809","S_XMMC":"哈尔滨市双城区伯爵公馆项目","B3139":15000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"672134490801","S_UP_FLAG":"0","S_CORP_UUID":"c0b19db309e645c6b59bf1a2fb7cf3cd","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"承旭"},{"S_SJ":"201809","S_XMMC":"哈尔滨市双城区御景东方小区房产开发项目","B3139":28000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"MA19D2DW8801","S_UP_FLAG":"0","S_CORP_UUID":"011422d8f6564bfe802a36d40f76495a","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"承旭"},{"S_SJ":"201809","S_XMMC":"哈尔滨市呼兰区滨才新天地四期-城市主场项目","B3139":20000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"725308483022","S_UP_FLAG":"0","S_CORP_UUID":"c415afc50017406fa3a90b2d9252a495","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"学院"},{"S_SJ":"201809","S_XMMC":"观江国际购物中心项目","B3139":20000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"763195619005","S_UP_FLAG":"0","S_CORP_UUID":"c3db0b9fda6244d7b398c7e3b6080e43","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"松浦大道"},{"S_SJ":"201809","S_XMMC":"哈尔滨汇雄生活汇","B3139":213400,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"MA18WFC91001","S_UP_FLAG":"0","S_CORP_UUID":"9ddaa4d9a5ae4da5bde770de2f2baac7","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"武源街"},{"S_SJ":"201809","S_XMMC":"鸿利.天下大观","B3139":44000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"663894164001","S_UP_FLAG":"0","S_CORP_UUID":"d63d571277974cad862b552ccd278838","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"红旗大街227号"},{"S_SJ":"201809","S_XMMC":"尚层峰景项目","B3139":42659,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"736941889001","S_UP_FLAG":"0","S_CORP_UUID":"27230a124d534e618d2fc74389e5720a","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"哈西大街"},{"S_SJ":"201809","S_XMMC":"爱达尊御项目","B3139":81447,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"MA18Y3P76001","S_UP_FLAG":"0","S_CORP_UUID":"72d2e56cb7a94e4a93cfcf2467734c49","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"哈西大街"},{"S_SJ":"201809","S_XMMC":"哈尔滨市恒大时代广场一期项目","B3139":273000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"MA19EJ9H1001","S_UP_FLAG":"0","S_CORP_UUID":"a42d545f2d4c40ec914c737859564cb6","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"香福路97"},{"S_SJ":"201809","S_XMMC":"东方花园小区","B3139":10000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"301176165002","S_UP_FLAG":"0","S_CORP_UUID":"f257512a2f654ff1ae9506c485c31c9b","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"卫建"},{"S_SJ":"201809","S_XMMC":"君豪哈尔滨御园项目","B3139":93734,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"565421427002","S_UP_FLAG":"0","S_CORP_UUID":"7370ff6382354b3faf609eccb2f818b8","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"利民"},{"S_SJ":"201809","S_XMMC":"御河湾印象城棚户区改造项目","B3139":60000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"308640848001","S_UP_FLAG":"0","S_CORP_UUID":"6683e5d78e1d4f6cbe7ca5b78bf28c9b","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"通河路"},{"S_SJ":"201809","S_XMMC":"哈汽仁和家园","B3139":4500,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"128020051001","S_UP_FLAG":"0","S_CORP_UUID":"448ec487cfb14cdebd1ca7cef7c917d1","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"华府路与太华路交口"},{"S_SJ":"201809","S_XMMC":"鲁商.松江新城1号地块项目","B3139":42400,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"565417954003","S_UP_FLAG":"0","S_CORP_UUID":"3df4cabd57d2442a94bb6d7472cf665c","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"学府路368号"},{"S_SJ":"201809","S_XMMC":"中艺·璞悦湾项目","B3139":105626,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"300826728001","S_UP_FLAG":"0","S_CORP_UUID":"53d0353c074b4e51920293f313516722","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"龙柏路与群力大道交口东南角"},{"S_SJ":"201809","S_XMMC":"南开华府项目","B3139":42659,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"736941889001","S_UP_FLAG":"0","S_CORP_UUID":"27230a124d534e618d2fc74389e5720a","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2"},{"S_SJ":"201809","S_XMMC":"中山路-比乐街高层","B3139":9635,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"607151139003","S_UP_FLAG":"0","S_CORP_UUID":"bd900e53970048889c6b3f7ec3e0fde3","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2"},{"S_SJ":"201809","S_XMMC":"XF22_进乡片区D-07A地块（华润.紫云府）项目","B3139":55270,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"MA18YCYX0002","S_UP_FLAG":"0","S_CORP_UUID":"5331b2b89c5b49bcaa9e4ced68cd6001","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"通乡街"},{"S_SJ":"201809","S_XMMC":"哈尔滨兴旺房地产有限公司黑龙江省哈尔滨市宾县学府新区三期建设项目","B3139":2000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"686038766005","S_UP_FLAG":"0","S_CORP_UUID":"025fb2754e18469a9ebb15cae57706f2","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2"},{"S_SJ":"201809","S_XMMC":"盛隆国际小区六期工程","B3139":13000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"300894675006","S_UP_FLAG":"0","S_CORP_UUID":"362810b47759459f9fdc14cae9a59215","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"黎明"},{"S_SJ":"201809","S_XMMC":"北岸观江国际项目","B3139":30000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"MA19ANX04001","S_UP_FLAG":"0","S_CORP_UUID":"8b7e35783cde4545973f33fd05ed774e","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"祥安北大街"},{"S_SJ":"201809","S_XMMC":"鲁商。松江新城6号B地块项目","B3139":113100,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"565417954004","S_UP_FLAG":"0","S_CORP_UUID":"422e40a67f7e4708be2eb8e5e8599547","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"学府路368号"},{"S_SJ":"201809","S_XMMC":"锦绣花园项目","B3139":29000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"77500770X001","S_UP_FLAG":"0","S_CORP_UUID":"97e0e6be8e5f4392a7ae194ead075e2a","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"职高路"},{"S_SJ":"201809","S_XMMC":"依兰县水韵紫城二期（A区）工程项目","B3139":7945,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"787540106801","S_UP_FLAG":"0","S_CORP_UUID":"8005de36754b46e49164d688e95c2ece","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2"},{"S_SJ":"201809","S_XMMC":"金色城邦D地块项目","B3139":72640,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"260646088003","S_UP_FLAG":"0","S_CORP_UUID":"e0bcc502d5044f01b5734b9093e11693","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"拥军"},{"S_SJ":"201809","S_XMMC":"千企PARK","B3139":66188,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"702848305001","S_UP_FLAG":"0","S_CORP_UUID":"998fd5fa447940a589391a73381bf463","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"松北大道"},{"S_SJ":"201809","S_XMMC":"哈尔滨市双城区御景东方小区房产开发项目","B3139":28000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"MA19D2DW8801","S_UP_FLAG":"0","S_CORP_UUID":"74cb43db67c44d4e9b363ebc570d8cd4","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"承旭"},{"S_SJ":"201809","S_XMMC":"木兰卓领盛世小区","B3139":8500,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"556102125002","S_UP_FLAG":"0","S_CORP_UUID":"50c1a8d285ef405e8536d6f9dc56bb39","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"富民"},{"S_SJ":"201809","S_XMMC":"哈尔滨时代置业房屋开发有限公司黑龙江省哈尔滨市五常市时代新城一期项目","B3139":44300,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"799277747001","S_UP_FLAG":"0","S_CORP_UUID":"fd81cdd85c3944ee8c01f84611228292","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"葵花街"},{"S_SJ":"201809","S_XMMC":"哈尔滨市阿城区万博华庭小区项目","B3139":5500,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"690742879002","S_UP_FLAG":"0","S_CORP_UUID":"2982f7f7357b4ffd97c5dca0fc323452","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"金都街"},{"S_SJ":"201809","S_XMMC":"哈东华府二期（紫荆名都）","B3139":107000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"585122029005","S_UP_FLAG":"0","S_CORP_UUID":"c4d29d1d3def4f83aa386735c704e3fb","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"东西路20"},{"S_SJ":"201809","S_XMMC":"黑龙江省哈尔滨市宾县西城文苑商服楼建设项目","B3139":2718,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"66566299X018","S_UP_FLAG":"0","S_CORP_UUID":"a1d7a157ad4b4fed93f8fec8092ad113","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"西城"},{"S_SJ":"201809","S_XMMC":"鲁商。松江新城6号地块项目","B3139":163100,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"565417954004","S_UP_FLAG":"0","S_CORP_UUID":"422e40a67f7e4708be2eb8e5e8599547","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"学府路368号"},{"S_SJ":"201809","S_XMMC":"鲁商.松江新城1号地块项目","B3139":42400,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"565417954005","S_UP_FLAG":"0","S_CORP_UUID":"3df4cabd57d2442a94bb6d7472cf665c","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"学府路368号"},{"S_SJ":"201809","S_XMMC":"哈尔滨恒大锦城","B3139":75438,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"MA18YCBJX001","S_UP_FLAG":"0","S_CORP_UUID":"62b6e8b1548e4ce2b61bcffa16666e80","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"曙光社区"},{"S_SJ":"201809","S_XMMC":"汇智中心项目","B3139":300000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"MA1AY97B2001","S_UP_FLAG":"0","S_CORP_UUID":"260c94e83e6d48e68de495146be891e5","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"东方大街与规划路围合区域"},{"S_SJ":"201809","S_XMMC":"地铁家园二期","B3139":91966,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"680290581003","S_UP_FLAG":"0","S_CORP_UUID":"c1d5b00bd03b4f2f9bfca75a0d5e97f3","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"哈东路"},{"S_SJ":"201809","S_XMMC":"国际花都.香堤九里","B3139":135000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"578068773007","S_UP_FLAG":"0","S_CORP_UUID":"4330c13663384b5295e31987917a2a7d","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"第八大道与哈南十五路处西北侧"},{"S_SJ":"201809","S_XMMC":"亚布力林业局河畔小区项目","B3139":5135,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"56063137X001","S_UP_FLAG":"0","S_CORP_UUID":"6ca4f0f9d5ad421a91dd692a95d0c843","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"三委"},{"S_SJ":"201809","S_XMMC":"哈尔滨永丰房地产开发有限公司生态园公寓","B3139":12000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"MA18XYEX8001","S_UP_FLAG":"0","S_CORP_UUID":"7ec2cd0aa51248b48cd9e9def3a5c313","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"兴胜"},{"S_SJ":"201809","S_XMMC":"鸿雁城商住综合楼项目","B3139":6000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"552631265801","S_UP_FLAG":"0","S_CORP_UUID":"f1805e4d6e9847068580d8a7b979dcd7","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"承旭"},{"S_SJ":"201809","S_XMMC":"东化工路棚改回迁安置项目","B3139":369267,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"558290916001","S_UP_FLAG":"0","S_CORP_UUID":"95d09902603648258a369d9d94d0a74e","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"红光社区"},{"S_SJ":"201809","S_XMMC":"依兰县水韵紫城二期工程项目","B3139":7945,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"787540106801","S_UP_FLAG":"0","S_CORP_UUID":"8005de36754b46e49164d688e95c2ece","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"三姓路"},{"S_SJ":"201809","S_XMMC":"哈尔滨恒大锦城","B3139":75438,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"MA18YCBJX001","S_UP_FLAG":"0","S_CORP_UUID":"62b6e8b1548e4ce2b61bcffa16666e80","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"曙光社区"},{"S_SJ":"201809","S_XMMC":"依兰县依兰镇城西（城中村）棚户区改造追加项目","B3139":12115,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"300908301801","S_UP_FLAG":"0","S_CORP_UUID":"0d334b726ad14f0597f5797f45cb1e76","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"中央大街"},{"S_SJ":"201809","S_XMMC":"君豪天御项目","B3139":82800,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"565421419002","S_UP_FLAG":"0","S_CORP_UUID":"cfc2c178cd35412db8f280119de0d6d7","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"888号"},{"S_SJ":"201809","S_XMMC":"鸿雁城商住综合楼项目","B3139":6000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"552631265801","S_UP_FLAG":"0","S_CORP_UUID":"f1805e4d6e9847068580d8a7b979dcd7","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"承旭"},{"S_SJ":"201809","S_XMMC":"香坊区哈尔滨恒大时代广场（D-09-01)地块项目","B3139":85686,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"MA19EJAA0001","S_UP_FLAG":"0","S_CORP_UUID":"278a2481587746068d52c3d0c047a841","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"香福路"},{"S_SJ":"201809","S_XMMC":"香坊区哈尔滨恒大时代广场（D-09-02)地块项目","B3139":54505,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"MA19EJA25001","S_UP_FLAG":"0","S_CORP_UUID":"877743398aad4070a48bc9818ae5102d","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"香福路"},{"S_SJ":"201809","S_XMMC":"香港卫视东北亚总部基地项目","B3139":150000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"MA19B92L6001","S_UP_FLAG":"0","S_CORP_UUID":"1df4919904904679bb3f0cb26ecf42d2","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"松北"},{"S_SJ":"201809","S_XMMC":"三航科技大厦项目","B3139":20000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"598469128001","S_UP_FLAG":"0","S_CORP_UUID":"2c24dc0fa36049c1963601c04358921f","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"雪花路"},{"S_SJ":"201809","S_XMMC":"大正臻园项目","B3139":88000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"128025362003","S_UP_FLAG":"0","S_CORP_UUID":"91bc5f225b98493090289a17af645300","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"龙川路"},{"S_SJ":"201809","S_XMMC":"金色城邦D地块项目","B3139":72640,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"260646088003","S_UP_FLAG":"0","S_CORP_UUID":"e0bcc502d5044f01b5734b9093e11693","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"拥军"},{"S_SJ":"201809","S_XMMC":"金色城邦C地块项目","B3139":83000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"560646088004","S_UP_FLAG":"0","S_CORP_UUID":"8850fb1b536a46668f99b17ce8b9a5a6","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"军民"},{"S_SJ":"201809","S_XMMC":"哈尔滨恒大锦城","B3139":75438,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"MA18YCBJX001","S_UP_FLAG":"0","S_CORP_UUID":"62b6e8b1548e4ce2b61bcffa16666e80","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"曙光社区"},{"S_SJ":"201809","S_XMMC":"尚志市馨园三期","B3139":9400,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"769080265001","S_UP_FLAG":"0","S_CORP_UUID":"ed010c9f023043829284b421a9156270","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"新建路"},{"S_SJ":"201809","S_XMMC":"哈尔滨市双城区浩宁金府第高档小区一期项目","B3139":20000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"69682826X802","S_UP_FLAG":"0","S_CORP_UUID":"6130c7904ca44c03b1a5e1f9aaaa9659","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"承旭"},{"S_SJ":"201809","S_XMMC":"黑龙江省哈尔滨市宾县英杰温泉小镇03地块项目","B3139":6089,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"568887196003","S_UP_FLAG":"0","S_CORP_UUID":"8a2b0784a8f644d1bd234f990fa511e5","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2"},{"S_SJ":"201809","S_XMMC":"嘉和城（二期）","B3139":8000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"300884848003","S_UP_FLAG":"0","S_CORP_UUID":"1ea69416481f4261b3808af3eaf1a6ce","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"尚志大街"},{"S_SJ":"201809","S_XMMC":"哈尔滨市双城区山水国际商住楼项目","B3139":32000,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"MA1971D94801","S_UP_FLAG":"0","S_CORP_UUID":"53be5e3e39b24e628eb997710a4532cb","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"承旭"},{"S_SJ":"201809","S_XMMC":"上京广场商住小区","B3139":55833,"S_TAB":"C814_2018_FDC_RKSQ","S_CODE":"126960090002","S_UP_FLAG":"0","S_CORP_UUID":"da460f477a2c48d3af5a833bbed340c8","S_COORDINATE_CK":"","type":"1","S_PHONE":"","S_TASK_ID":"","S_ZY":"2","S_ADDRESS":"北顺社区"}]
     * result : 1
     */

    private String result;
    private List<XmListBean> xmList;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<XmListBean> getXmList() {
        return xmList;
    }

    public void setXmList(List<XmListBean> xmList) {
        this.xmList = xmList;
    }

    public static class XmListBean implements Serializable {
        /**
         * S_SJ : 201805
         * S_XMMC :
         * B3139 : 68997
         * S_TAB : S812_2018_TZ_RKSQ
         * S_CODE : 301233238230110001
         * S_UP_FLAG : 0
         * S_CORP_UUID : 20170401155524087
         * S_COORDINATE_CK :
         * type : 1
         * S_PHONE :
         * S_TASK_ID :
         * S_ZY : 1
         * S_ADDRESS : 电塔街
         */

        private String S_SJ;
        private String S_XMMC;
        private int B3139;
        private String S_TAB;
        private String S_CODE;
        private String S_UP_FLAG;
        private String S_CORP_UUID;
        private String S_COORDINATE_CK;
        private String type;
        private String S_PHONE;
        private String S_TASK_ID;
        private String S_ZY;
        private String S_ADDRESS;

        public String getS_SJ() {
            return S_SJ;
        }

        public void setS_SJ(String S_SJ) {
            this.S_SJ = S_SJ;
        }

        public String getS_XMMC() {
            return S_XMMC;
        }

        public void setS_XMMC(String S_XMMC) {
            this.S_XMMC = S_XMMC;
        }

        public int getB3139() {
            return B3139;
        }

        public void setB3139(int B3139) {
            this.B3139 = B3139;
        }

        public String getS_TAB() {
            return S_TAB;
        }

        public void setS_TAB(String S_TAB) {
            this.S_TAB = S_TAB;
        }

        public String getS_CODE() {
            return S_CODE;
        }

        public void setS_CODE(String S_CODE) {
            this.S_CODE = S_CODE;
        }

        public String getS_UP_FLAG() {
            return S_UP_FLAG;
        }

        public void setS_UP_FLAG(String S_UP_FLAG) {
            this.S_UP_FLAG = S_UP_FLAG;
        }

        public String getS_CORP_UUID() {
            return S_CORP_UUID;
        }

        public void setS_CORP_UUID(String S_CORP_UUID) {
            this.S_CORP_UUID = S_CORP_UUID;
        }

        public String getS_COORDINATE_CK() {
            return S_COORDINATE_CK;
        }

        public void setS_COORDINATE_CK(String S_COORDINATE_CK) {
            this.S_COORDINATE_CK = S_COORDINATE_CK;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getS_PHONE() {
            return S_PHONE;
        }

        public void setS_PHONE(String S_PHONE) {
            this.S_PHONE = S_PHONE;
        }

        public String getS_TASK_ID() {
            return S_TASK_ID;
        }

        public void setS_TASK_ID(String S_TASK_ID) {
            this.S_TASK_ID = S_TASK_ID;
        }

        public String getS_ZY() {
            return S_ZY;
        }

        public void setS_ZY(String S_ZY) {
            this.S_ZY = S_ZY;
        }

        public String getS_ADDRESS() {
            return S_ADDRESS;
        }

        public void setS_ADDRESS(String S_ADDRESS) {
            this.S_ADDRESS = S_ADDRESS;
        }
    }
}
