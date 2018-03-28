var COUNTRY_ID = "100000";
var districts = [
		{
			"ID" : "100000",
			"PARENT_ID" : "",
			"DISTRICT_NAME" : "全国",
			"SPELLING" : "Quanguo"
		},
		{
			"ID" : "110000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "北京",
			"SPELLING" : "Beijing"
		},
		{
			"ID" : "110100",
			"PARENT_ID" : "110000",
			"DISTRICT_NAME" : "北京市",
			"SPELLING" : "Beijing"
		},
		{
			"ID" : "110101",
			"PARENT_ID" : "110100",
			"DISTRICT_NAME" : "东城区",
			"SPELLING" : "Dongcheng"
		},
		{
			"ID" : "110102",
			"PARENT_ID" : "110100",
			"DISTRICT_NAME" : "西城区",
			"SPELLING" : "Xicheng"
		},
		{
			"ID" : "110105",
			"PARENT_ID" : "110100",
			"DISTRICT_NAME" : "朝阳区",
			"SPELLING" : "Chaoyang"
		},
		{
			"ID" : "110106",
			"PARENT_ID" : "110100",
			"DISTRICT_NAME" : "丰台区",
			"SPELLING" : "Fengtai"
		},
		{
			"ID" : "110107",
			"PARENT_ID" : "110100",
			"DISTRICT_NAME" : "石景山区",
			"SPELLING" : "Shijingshan"
		},
		{
			"ID" : "110108",
			"PARENT_ID" : "110100",
			"DISTRICT_NAME" : "海淀区",
			"SPELLING" : "Haidian"
		},
		{
			"ID" : "110109",
			"PARENT_ID" : "110100",
			"DISTRICT_NAME" : "门头沟区",
			"SPELLING" : "Mentougou"
		},
		{
			"ID" : "110111",
			"PARENT_ID" : "110100",
			"DISTRICT_NAME" : "房山区",
			"SPELLING" : "Fangshan"
		},
		{
			"ID" : "110112",
			"PARENT_ID" : "110100",
			"DISTRICT_NAME" : "通州区",
			"SPELLING" : "Tongzhou"
		},
		{
			"ID" : "110113",
			"PARENT_ID" : "110100",
			"DISTRICT_NAME" : "顺义区",
			"SPELLING" : "Shunyi"
		},
		{
			"ID" : "110114",
			"PARENT_ID" : "110100",
			"DISTRICT_NAME" : "昌平区",
			"SPELLING" : "Changping"
		},
		{
			"ID" : "110115",
			"PARENT_ID" : "110100",
			"DISTRICT_NAME" : "大兴区",
			"SPELLING" : "Daxing"
		},
		{
			"ID" : "110116",
			"PARENT_ID" : "110100",
			"DISTRICT_NAME" : "怀柔区",
			"SPELLING" : "Huairou"
		},
		{
			"ID" : "110117",
			"PARENT_ID" : "110100",
			"DISTRICT_NAME" : "平谷区",
			"SPELLING" : "Pinggu"
		},
		{
			"ID" : "110228",
			"PARENT_ID" : "110100",
			"DISTRICT_NAME" : "密云县",
			"SPELLING" : "Miyun"
		},
		{
			"ID" : "110229",
			"PARENT_ID" : "110100",
			"DISTRICT_NAME" : "延庆县",
			"SPELLING" : "Yanqing"
		},
		{
			"ID" : "120000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "天津",
			"SPELLING" : "Tianjin"
		},
		{
			"ID" : "120100",
			"PARENT_ID" : "120000",
			"DISTRICT_NAME" : "天津市",
			"SPELLING" : "Tianjin"
		},
		{
			"ID" : "120101",
			"PARENT_ID" : "120100",
			"DISTRICT_NAME" : "和平区",
			"SPELLING" : "Heping"
		},
		{
			"ID" : "120102",
			"PARENT_ID" : "120100",
			"DISTRICT_NAME" : "河东区",
			"SPELLING" : "Hedong"
		},
		{
			"ID" : "120103",
			"PARENT_ID" : "120100",
			"DISTRICT_NAME" : "河西区",
			"SPELLING" : "Hexi"
		},
		{
			"ID" : "120104",
			"PARENT_ID" : "120100",
			"DISTRICT_NAME" : "南开区",
			"SPELLING" : "Nankai"
		},
		{
			"ID" : "120105",
			"PARENT_ID" : "120100",
			"DISTRICT_NAME" : "河北区",
			"SPELLING" : "Hebei"
		},
		{
			"ID" : "120106",
			"PARENT_ID" : "120100",
			"DISTRICT_NAME" : "红桥区",
			"SPELLING" : "Hongqiao"
		},
		{
			"ID" : "120110",
			"PARENT_ID" : "120100",
			"DISTRICT_NAME" : "东丽区",
			"SPELLING" : "Dongli"
		},
		{
			"ID" : "120111",
			"PARENT_ID" : "120100",
			"DISTRICT_NAME" : "西青区",
			"SPELLING" : "Xiqing"
		},
		{
			"ID" : "120112",
			"PARENT_ID" : "120100",
			"DISTRICT_NAME" : "津南区",
			"SPELLING" : "Jinnan"
		},
		{
			"ID" : "120113",
			"PARENT_ID" : "120100",
			"DISTRICT_NAME" : "北辰区",
			"SPELLING" : "Beichen"
		},
		{
			"ID" : "120114",
			"PARENT_ID" : "120100",
			"DISTRICT_NAME" : "武清区",
			"SPELLING" : "Wuqing"
		},
		{
			"ID" : "120115",
			"PARENT_ID" : "120100",
			"DISTRICT_NAME" : "宝坻区",
			"SPELLING" : "Baodi"
		},
		{
			"ID" : "120116",
			"PARENT_ID" : "120100",
			"DISTRICT_NAME" : "滨海新区",
			"SPELLING" : "Binhaixinqu"
		},
		{
			"ID" : "120221",
			"PARENT_ID" : "120100",
			"DISTRICT_NAME" : "宁河县",
			"SPELLING" : "Ninghe"
		},
		{
			"ID" : "120223",
			"PARENT_ID" : "120100",
			"DISTRICT_NAME" : "静海县",
			"SPELLING" : "Jinghai"
		},
		{
			"ID" : "120225",
			"PARENT_ID" : "120100",
			"DISTRICT_NAME" : "蓟县",
			"SPELLING" : "Jixian"
		},
		{
			"ID" : "130000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "河北省",
			"SPELLING" : "Hebei"
		},
		{
			"ID" : "130100",
			"PARENT_ID" : "130000",
			"DISTRICT_NAME" : "石家庄市",
			"SPELLING" : "Shijiazhuang"
		},
		{
			"ID" : "130102",
			"PARENT_ID" : "130100",
			"DISTRICT_NAME" : "长安区",
			"SPELLING" : "Changan"
		},
		{
			"ID" : "130104",
			"PARENT_ID" : "130100",
			"DISTRICT_NAME" : "桥西区",
			"SPELLING" : "Qiaoxi"
		},
		{
			"ID" : "130105",
			"PARENT_ID" : "130100",
			"DISTRICT_NAME" : "新华区",
			"SPELLING" : "Xinhua"
		},
		{
			"ID" : "130107",
			"PARENT_ID" : "130100",
			"DISTRICT_NAME" : "井陉矿区",
			"SPELLING" : "Jingxingkuangqu"
		},
		{
			"ID" : "130108",
			"PARENT_ID" : "130100",
			"DISTRICT_NAME" : "裕华区",
			"SPELLING" : "Yuhua"
		},
		{
			"ID" : "130109",
			"PARENT_ID" : "130100",
			"DISTRICT_NAME" : "藁城区",
			"SPELLING" : "Gaocheng"
		},
		{
			"ID" : "130110",
			"PARENT_ID" : "130100",
			"DISTRICT_NAME" : "鹿泉区",
			"SPELLING" : "Luquan"
		},
		{
			"ID" : "130111",
			"PARENT_ID" : "130100",
			"DISTRICT_NAME" : "栾城区",
			"SPELLING" : "Luancheng"
		},
		{
			"ID" : "130121",
			"PARENT_ID" : "130100",
			"DISTRICT_NAME" : "井陉县",
			"SPELLING" : "Jingxing"
		},
		{
			"ID" : "130123",
			"PARENT_ID" : "130100",
			"DISTRICT_NAME" : "正定县",
			"SPELLING" : "Zhengding"
		},
		{
			"ID" : "130125",
			"PARENT_ID" : "130100",
			"DISTRICT_NAME" : "行唐县",
			"SPELLING" : "Xingtang"
		},
		{
			"ID" : "130126",
			"PARENT_ID" : "130100",
			"DISTRICT_NAME" : "灵寿县",
			"SPELLING" : "Lingshou"
		},
		{
			"ID" : "130127",
			"PARENT_ID" : "130100",
			"DISTRICT_NAME" : "高邑县",
			"SPELLING" : "Gaoyi"
		},
		{
			"ID" : "130128",
			"PARENT_ID" : "130100",
			"DISTRICT_NAME" : "深泽县",
			"SPELLING" : "Shenze"
		},
		{
			"ID" : "130129",
			"PARENT_ID" : "130100",
			"DISTRICT_NAME" : "赞皇县",
			"SPELLING" : "Zanhuang"
		},
		{
			"ID" : "130130",
			"PARENT_ID" : "130100",
			"DISTRICT_NAME" : "无极县",
			"SPELLING" : "Wuji"
		},
		{
			"ID" : "130131",
			"PARENT_ID" : "130100",
			"DISTRICT_NAME" : "平山县",
			"SPELLING" : "Pingshan"
		},
		{
			"ID" : "130132",
			"PARENT_ID" : "130100",
			"DISTRICT_NAME" : "元氏县",
			"SPELLING" : "Yuanshi"
		},
		{
			"ID" : "130133",
			"PARENT_ID" : "130100",
			"DISTRICT_NAME" : "赵县",
			"SPELLING" : "Zhaoxian"
		},
		{
			"ID" : "130181",
			"PARENT_ID" : "130100",
			"DISTRICT_NAME" : "辛集市",
			"SPELLING" : "Xinji"
		},
		{
			"ID" : "130183",
			"PARENT_ID" : "130100",
			"DISTRICT_NAME" : "晋州市",
			"SPELLING" : "Jinzhou"
		},
		{
			"ID" : "130184",
			"PARENT_ID" : "130100",
			"DISTRICT_NAME" : "新乐市",
			"SPELLING" : "Xinle"
		},
		{
			"ID" : "130200",
			"PARENT_ID" : "130000",
			"DISTRICT_NAME" : "唐山市",
			"SPELLING" : "Tangshan"
		},
		{
			"ID" : "130202",
			"PARENT_ID" : "130200",
			"DISTRICT_NAME" : "路南区",
			"SPELLING" : "Lunan"
		},
		{
			"ID" : "130203",
			"PARENT_ID" : "130200",
			"DISTRICT_NAME" : "路北区",
			"SPELLING" : "Lubei"
		},
		{
			"ID" : "130204",
			"PARENT_ID" : "130200",
			"DISTRICT_NAME" : "古冶区",
			"SPELLING" : "Guye"
		},
		{
			"ID" : "130205",
			"PARENT_ID" : "130200",
			"DISTRICT_NAME" : "开平区",
			"SPELLING" : "Kaiping"
		},
		{
			"ID" : "130207",
			"PARENT_ID" : "130200",
			"DISTRICT_NAME" : "丰南区",
			"SPELLING" : "Fengnan"
		},
		{
			"ID" : "130208",
			"PARENT_ID" : "130200",
			"DISTRICT_NAME" : "丰润区",
			"SPELLING" : "Fengrun"
		},
		{
			"ID" : "130209",
			"PARENT_ID" : "130200",
			"DISTRICT_NAME" : "曹妃甸区",
			"SPELLING" : "Caofeidian"
		},
		{
			"ID" : "130223",
			"PARENT_ID" : "130200",
			"DISTRICT_NAME" : "滦县",
			"SPELLING" : "Luanxian"
		},
		{
			"ID" : "130224",
			"PARENT_ID" : "130200",
			"DISTRICT_NAME" : "滦南县",
			"SPELLING" : "Luannan"
		},
		{
			"ID" : "130225",
			"PARENT_ID" : "130200",
			"DISTRICT_NAME" : "乐亭县",
			"SPELLING" : "Laoting"
		},
		{
			"ID" : "130227",
			"PARENT_ID" : "130200",
			"DISTRICT_NAME" : "迁西县",
			"SPELLING" : "Qianxi"
		},
		{
			"ID" : "130229",
			"PARENT_ID" : "130200",
			"DISTRICT_NAME" : "玉田县",
			"SPELLING" : "Yutian"
		},
		{
			"ID" : "130281",
			"PARENT_ID" : "130200",
			"DISTRICT_NAME" : "遵化市",
			"SPELLING" : "Zunhua"
		},
		{
			"ID" : "130283",
			"PARENT_ID" : "130200",
			"DISTRICT_NAME" : "迁安市",
			"SPELLING" : "Qianan"
		},
		{
			"ID" : "130300",
			"PARENT_ID" : "130000",
			"DISTRICT_NAME" : "秦皇岛市",
			"SPELLING" : "Qinhuangdao"
		},
		{
			"ID" : "130302",
			"PARENT_ID" : "130300",
			"DISTRICT_NAME" : "海港区",
			"SPELLING" : "Haigang"
		},
		{
			"ID" : "130303",
			"PARENT_ID" : "130300",
			"DISTRICT_NAME" : "山海关区",
			"SPELLING" : "Shanhaiguan"
		},
		{
			"ID" : "130304",
			"PARENT_ID" : "130300",
			"DISTRICT_NAME" : "北戴河区",
			"SPELLING" : "Beidaihe"
		},
		{
			"ID" : "130321",
			"PARENT_ID" : "130300",
			"DISTRICT_NAME" : "青龙满族自治县",
			"SPELLING" : "Qinglong"
		},
		{
			"ID" : "130322",
			"PARENT_ID" : "130300",
			"DISTRICT_NAME" : "昌黎县",
			"SPELLING" : "Changli"
		},
		{
			"ID" : "130323",
			"PARENT_ID" : "130300",
			"DISTRICT_NAME" : "抚宁县",
			"SPELLING" : "Funing"
		},
		{
			"ID" : "130324",
			"PARENT_ID" : "130300",
			"DISTRICT_NAME" : "卢龙县",
			"SPELLING" : "Lulong"
		},
		{
			"ID" : "130400",
			"PARENT_ID" : "130000",
			"DISTRICT_NAME" : "邯郸市",
			"SPELLING" : "Handan"
		},
		{
			"ID" : "130402",
			"PARENT_ID" : "130400",
			"DISTRICT_NAME" : "邯山区",
			"SPELLING" : "Hanshan"
		},
		{
			"ID" : "130403",
			"PARENT_ID" : "130400",
			"DISTRICT_NAME" : "丛台区",
			"SPELLING" : "Congtai"
		},
		{
			"ID" : "130404",
			"PARENT_ID" : "130400",
			"DISTRICT_NAME" : "复兴区",
			"SPELLING" : "Fuxing"
		},
		{
			"ID" : "130406",
			"PARENT_ID" : "130400",
			"DISTRICT_NAME" : "峰峰矿区",
			"SPELLING" : "Fengfengkuangqu"
		},
		{
			"ID" : "130421",
			"PARENT_ID" : "130400",
			"DISTRICT_NAME" : "邯郸县",
			"SPELLING" : "Handan"
		},
		{
			"ID" : "130423",
			"PARENT_ID" : "130400",
			"DISTRICT_NAME" : "临漳县",
			"SPELLING" : "Linzhang"
		},
		{
			"ID" : "130424",
			"PARENT_ID" : "130400",
			"DISTRICT_NAME" : "成安县",
			"SPELLING" : "Chengan"
		},
		{
			"ID" : "130425",
			"PARENT_ID" : "130400",
			"DISTRICT_NAME" : "大名县",
			"SPELLING" : "Daming"
		},
		{
			"ID" : "130426",
			"PARENT_ID" : "130400",
			"DISTRICT_NAME" : "涉县",
			"SPELLING" : "Shexian"
		},
		{
			"ID" : "130427",
			"PARENT_ID" : "130400",
			"DISTRICT_NAME" : "磁县",
			"SPELLING" : "Cixian"
		},
		{
			"ID" : "130428",
			"PARENT_ID" : "130400",
			"DISTRICT_NAME" : "肥乡县",
			"SPELLING" : "Feixiang"
		},
		{
			"ID" : "130429",
			"PARENT_ID" : "130400",
			"DISTRICT_NAME" : "永年县",
			"SPELLING" : "Yongnian"
		},
		{
			"ID" : "130430",
			"PARENT_ID" : "130400",
			"DISTRICT_NAME" : "邱县",
			"SPELLING" : "Qiuxian"
		},
		{
			"ID" : "130431",
			"PARENT_ID" : "130400",
			"DISTRICT_NAME" : "鸡泽县",
			"SPELLING" : "Jize"
		},
		{
			"ID" : "130432",
			"PARENT_ID" : "130400",
			"DISTRICT_NAME" : "广平县",
			"SPELLING" : "Guangping"
		},
		{
			"ID" : "130433",
			"PARENT_ID" : "130400",
			"DISTRICT_NAME" : "馆陶县",
			"SPELLING" : "Guantao"
		},
		{
			"ID" : "130434",
			"PARENT_ID" : "130400",
			"DISTRICT_NAME" : "魏县",
			"SPELLING" : "Weixian"
		},
		{
			"ID" : "130435",
			"PARENT_ID" : "130400",
			"DISTRICT_NAME" : "曲周县",
			"SPELLING" : "Quzhou"
		},
		{
			"ID" : "130481",
			"PARENT_ID" : "130400",
			"DISTRICT_NAME" : "武安市",
			"SPELLING" : "Wuan"
		},
		{
			"ID" : "130500",
			"PARENT_ID" : "130000",
			"DISTRICT_NAME" : "邢台市",
			"SPELLING" : "Xingtai"
		},
		{
			"ID" : "130502",
			"PARENT_ID" : "130500",
			"DISTRICT_NAME" : "桥东区",
			"SPELLING" : "Qiaodong"
		},
		{
			"ID" : "130503",
			"PARENT_ID" : "130500",
			"DISTRICT_NAME" : "桥西区",
			"SPELLING" : "Qiaoxi"
		},
		{
			"ID" : "130521",
			"PARENT_ID" : "130500",
			"DISTRICT_NAME" : "邢台县",
			"SPELLING" : "Xingtai"
		},
		{
			"ID" : "130522",
			"PARENT_ID" : "130500",
			"DISTRICT_NAME" : "临城县",
			"SPELLING" : "Lincheng"
		},
		{
			"ID" : "130523",
			"PARENT_ID" : "130500",
			"DISTRICT_NAME" : "内丘县",
			"SPELLING" : "Neiqiu"
		},
		{
			"ID" : "130524",
			"PARENT_ID" : "130500",
			"DISTRICT_NAME" : "柏乡县",
			"SPELLING" : "Baixiang"
		},
		{
			"ID" : "130525",
			"PARENT_ID" : "130500",
			"DISTRICT_NAME" : "隆尧县",
			"SPELLING" : "Longyao"
		},
		{
			"ID" : "130526",
			"PARENT_ID" : "130500",
			"DISTRICT_NAME" : "任县",
			"SPELLING" : "Renxian"
		},
		{
			"ID" : "130527",
			"PARENT_ID" : "130500",
			"DISTRICT_NAME" : "南和县",
			"SPELLING" : "Nanhe"
		},
		{
			"ID" : "130528",
			"PARENT_ID" : "130500",
			"DISTRICT_NAME" : "宁晋县",
			"SPELLING" : "Ningjin"
		},
		{
			"ID" : "130529",
			"PARENT_ID" : "130500",
			"DISTRICT_NAME" : "巨鹿县",
			"SPELLING" : "Julu"
		},
		{
			"ID" : "130530",
			"PARENT_ID" : "130500",
			"DISTRICT_NAME" : "新河县",
			"SPELLING" : "Xinhe"
		},
		{
			"ID" : "130531",
			"PARENT_ID" : "130500",
			"DISTRICT_NAME" : "广宗县",
			"SPELLING" : "Guangzong"
		},
		{
			"ID" : "130532",
			"PARENT_ID" : "130500",
			"DISTRICT_NAME" : "平乡县",
			"SPELLING" : "Pingxiang"
		},
		{
			"ID" : "130533",
			"PARENT_ID" : "130500",
			"DISTRICT_NAME" : "威县",
			"SPELLING" : "Weixian"
		},
		{
			"ID" : "130534",
			"PARENT_ID" : "130500",
			"DISTRICT_NAME" : "清河县",
			"SPELLING" : "Qinghe"
		},
		{
			"ID" : "130535",
			"PARENT_ID" : "130500",
			"DISTRICT_NAME" : "临西县",
			"SPELLING" : "Linxi"
		},
		{
			"ID" : "130581",
			"PARENT_ID" : "130500",
			"DISTRICT_NAME" : "南宫市",
			"SPELLING" : "Nangong"
		},
		{
			"ID" : "130582",
			"PARENT_ID" : "130500",
			"DISTRICT_NAME" : "沙河市",
			"SPELLING" : "Shahe"
		},
		{
			"ID" : "130600",
			"PARENT_ID" : "130000",
			"DISTRICT_NAME" : "保定市",
			"SPELLING" : "Baoding"
		},
		{
			"ID" : "130602",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "新市区",
			"SPELLING" : "Xinshi"
		},
		{
			"ID" : "130603",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "北市区",
			"SPELLING" : "Beishi"
		},
		{
			"ID" : "130604",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "南市区",
			"SPELLING" : "Nanshi"
		},
		{
			"ID" : "130621",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "满城县",
			"SPELLING" : "Mancheng"
		},
		{
			"ID" : "130622",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "清苑县",
			"SPELLING" : "Qingyuan"
		},
		{
			"ID" : "130623",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "涞水县",
			"SPELLING" : "Laishui"
		},
		{
			"ID" : "130624",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "阜平县",
			"SPELLING" : "Fuping"
		},
		{
			"ID" : "130625",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "徐水县",
			"SPELLING" : "Xushui"
		},
		{
			"ID" : "130626",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "定兴县",
			"SPELLING" : "Dingxing"
		},
		{
			"ID" : "130627",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "唐县",
			"SPELLING" : "Tangxian"
		},
		{
			"ID" : "130628",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "高阳县",
			"SPELLING" : "Gaoyang"
		},
		{
			"ID" : "130629",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "容城县",
			"SPELLING" : "Rongcheng"
		},
		{
			"ID" : "130630",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "涞源县",
			"SPELLING" : "Laiyuan"
		},
		{
			"ID" : "130631",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "望都县",
			"SPELLING" : "Wangdu"
		},
		{
			"ID" : "130632",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "安新县",
			"SPELLING" : "Anxin"
		},
		{
			"ID" : "130633",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "易县",
			"SPELLING" : "Yixian"
		},
		{
			"ID" : "130634",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "曲阳县",
			"SPELLING" : "Quyang"
		},
		{
			"ID" : "130635",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "蠡县",
			"SPELLING" : "Lixian"
		},
		{
			"ID" : "130636",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "顺平县",
			"SPELLING" : "Shunping"
		},
		{
			"ID" : "130637",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "博野县",
			"SPELLING" : "Boye"
		},
		{
			"ID" : "130638",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "雄县",
			"SPELLING" : "Xiongxian"
		},
		{
			"ID" : "130681",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "涿州市",
			"SPELLING" : "Zhuozhou"
		},
		{
			"ID" : "130682",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "定州市",
			"SPELLING" : "Dingzhou"
		},
		{
			"ID" : "130683",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "安国市",
			"SPELLING" : "Anguo"
		},
		{
			"ID" : "130684",
			"PARENT_ID" : "130600",
			"DISTRICT_NAME" : "高碑店市",
			"SPELLING" : "Gaobeidian"
		},
		{
			"ID" : "130700",
			"PARENT_ID" : "130000",
			"DISTRICT_NAME" : "张家口市",
			"SPELLING" : "Zhangjiakou"
		},
		{
			"ID" : "130702",
			"PARENT_ID" : "130700",
			"DISTRICT_NAME" : "桥东区",
			"SPELLING" : "Qiaodong"
		},
		{
			"ID" : "130703",
			"PARENT_ID" : "130700",
			"DISTRICT_NAME" : "桥西区",
			"SPELLING" : "Qiaoxi"
		},
		{
			"ID" : "130705",
			"PARENT_ID" : "130700",
			"DISTRICT_NAME" : "宣化区",
			"SPELLING" : "Xuanhua"
		},
		{
			"ID" : "130706",
			"PARENT_ID" : "130700",
			"DISTRICT_NAME" : "下花园区",
			"SPELLING" : "Xiahuayuan"
		},
		{
			"ID" : "130721",
			"PARENT_ID" : "130700",
			"DISTRICT_NAME" : "宣化县",
			"SPELLING" : "Xuanhua"
		},
		{
			"ID" : "130722",
			"PARENT_ID" : "130700",
			"DISTRICT_NAME" : "张北县",
			"SPELLING" : "Zhangbei"
		},
		{
			"ID" : "130723",
			"PARENT_ID" : "130700",
			"DISTRICT_NAME" : "康保县",
			"SPELLING" : "Kangbao"
		},
		{
			"ID" : "130724",
			"PARENT_ID" : "130700",
			"DISTRICT_NAME" : "沽源县",
			"SPELLING" : "Guyuan"
		},
		{
			"ID" : "130725",
			"PARENT_ID" : "130700",
			"DISTRICT_NAME" : "尚义县",
			"SPELLING" : "Shangyi"
		},
		{
			"ID" : "130726",
			"PARENT_ID" : "130700",
			"DISTRICT_NAME" : "蔚县",
			"SPELLING" : "Yuxian"
		},
		{
			"ID" : "130727",
			"PARENT_ID" : "130700",
			"DISTRICT_NAME" : "阳原县",
			"SPELLING" : "Yangyuan"
		},
		{
			"ID" : "130728",
			"PARENT_ID" : "130700",
			"DISTRICT_NAME" : "怀安县",
			"SPELLING" : "Huaian"
		},
		{
			"ID" : "130729",
			"PARENT_ID" : "130700",
			"DISTRICT_NAME" : "万全县",
			"SPELLING" : "Wanquan"
		},
		{
			"ID" : "130730",
			"PARENT_ID" : "130700",
			"DISTRICT_NAME" : "怀来县",
			"SPELLING" : "Huailai"
		},
		{
			"ID" : "130731",
			"PARENT_ID" : "130700",
			"DISTRICT_NAME" : "涿鹿县",
			"SPELLING" : "Zhuolu"
		},
		{
			"ID" : "130732",
			"PARENT_ID" : "130700",
			"DISTRICT_NAME" : "赤城县",
			"SPELLING" : "Chicheng"
		},
		{
			"ID" : "130733",
			"PARENT_ID" : "130700",
			"DISTRICT_NAME" : "崇礼县",
			"SPELLING" : "Chongli"
		},
		{
			"ID" : "130800",
			"PARENT_ID" : "130000",
			"DISTRICT_NAME" : "承德市",
			"SPELLING" : "Chengde"
		},
		{
			"ID" : "130802",
			"PARENT_ID" : "130800",
			"DISTRICT_NAME" : "双桥区",
			"SPELLING" : "Shuangqiao"
		},
		{
			"ID" : "130803",
			"PARENT_ID" : "130800",
			"DISTRICT_NAME" : "双滦区",
			"SPELLING" : "Shuangluan"
		},
		{
			"ID" : "130804",
			"PARENT_ID" : "130800",
			"DISTRICT_NAME" : "鹰手营子矿区",
			"SPELLING" : "Yingshouyingzikuangqu"
		},
		{
			"ID" : "130821",
			"PARENT_ID" : "130800",
			"DISTRICT_NAME" : "承德县",
			"SPELLING" : "Chengde"
		},
		{
			"ID" : "130822",
			"PARENT_ID" : "130800",
			"DISTRICT_NAME" : "兴隆县",
			"SPELLING" : "Xinglong"
		},
		{
			"ID" : "130823",
			"PARENT_ID" : "130800",
			"DISTRICT_NAME" : "平泉县",
			"SPELLING" : "Pingquan"
		},
		{
			"ID" : "130824",
			"PARENT_ID" : "130800",
			"DISTRICT_NAME" : "滦平县",
			"SPELLING" : "Luanping"
		},
		{
			"ID" : "130825",
			"PARENT_ID" : "130800",
			"DISTRICT_NAME" : "隆化县",
			"SPELLING" : "Longhua"
		},
		{
			"ID" : "130826",
			"PARENT_ID" : "130800",
			"DISTRICT_NAME" : "丰宁满族自治县",
			"SPELLING" : "Fengning"
		},
		{
			"ID" : "130827",
			"PARENT_ID" : "130800",
			"DISTRICT_NAME" : "宽城满族自治县",
			"SPELLING" : "Kuancheng"
		},
		{
			"ID" : "130828",
			"PARENT_ID" : "130800",
			"DISTRICT_NAME" : "围场满族蒙古族自治县",
			"SPELLING" : "Weichang"
		},
		{
			"ID" : "130900",
			"PARENT_ID" : "130000",
			"DISTRICT_NAME" : "沧州市",
			"SPELLING" : "Cangzhou"
		},
		{
			"ID" : "130902",
			"PARENT_ID" : "130900",
			"DISTRICT_NAME" : "新华区",
			"SPELLING" : "Xinhua"
		},
		{
			"ID" : "130903",
			"PARENT_ID" : "130900",
			"DISTRICT_NAME" : "运河区",
			"SPELLING" : "Yunhe"
		},
		{
			"ID" : "130921",
			"PARENT_ID" : "130900",
			"DISTRICT_NAME" : "沧县",
			"SPELLING" : "Cangxian"
		},
		{
			"ID" : "130922",
			"PARENT_ID" : "130900",
			"DISTRICT_NAME" : "青县",
			"SPELLING" : "Qingxian"
		},
		{
			"ID" : "130923",
			"PARENT_ID" : "130900",
			"DISTRICT_NAME" : "东光县",
			"SPELLING" : "Dongguang"
		},
		{
			"ID" : "130924",
			"PARENT_ID" : "130900",
			"DISTRICT_NAME" : "海兴县",
			"SPELLING" : "Haixing"
		},
		{
			"ID" : "130925",
			"PARENT_ID" : "130900",
			"DISTRICT_NAME" : "盐山县",
			"SPELLING" : "Yanshan"
		},
		{
			"ID" : "130926",
			"PARENT_ID" : "130900",
			"DISTRICT_NAME" : "肃宁县",
			"SPELLING" : "Suning"
		},
		{
			"ID" : "130927",
			"PARENT_ID" : "130900",
			"DISTRICT_NAME" : "南皮县",
			"SPELLING" : "Nanpi"
		},
		{
			"ID" : "130928",
			"PARENT_ID" : "130900",
			"DISTRICT_NAME" : "吴桥县",
			"SPELLING" : "Wuqiao"
		},
		{
			"ID" : "130929",
			"PARENT_ID" : "130900",
			"DISTRICT_NAME" : "献县",
			"SPELLING" : "Xianxian"
		},
		{
			"ID" : "130930",
			"PARENT_ID" : "130900",
			"DISTRICT_NAME" : "孟村回族自治县",
			"SPELLING" : "Mengcun"
		},
		{
			"ID" : "130981",
			"PARENT_ID" : "130900",
			"DISTRICT_NAME" : "泊头市",
			"SPELLING" : "Botou"
		},
		{
			"ID" : "130982",
			"PARENT_ID" : "130900",
			"DISTRICT_NAME" : "任丘市",
			"SPELLING" : "Renqiu"
		},
		{
			"ID" : "130983",
			"PARENT_ID" : "130900",
			"DISTRICT_NAME" : "黄骅市",
			"SPELLING" : "Huanghua"
		},
		{
			"ID" : "130984",
			"PARENT_ID" : "130900",
			"DISTRICT_NAME" : "河间市",
			"SPELLING" : "Hejian"
		},
		{
			"ID" : "131000",
			"PARENT_ID" : "130000",
			"DISTRICT_NAME" : "廊坊市",
			"SPELLING" : "Langfang"
		},
		{
			"ID" : "131002",
			"PARENT_ID" : "131000",
			"DISTRICT_NAME" : "安次区",
			"SPELLING" : "Anci"
		},
		{
			"ID" : "131003",
			"PARENT_ID" : "131000",
			"DISTRICT_NAME" : "广阳区",
			"SPELLING" : "Guangyang"
		},
		{
			"ID" : "131022",
			"PARENT_ID" : "131000",
			"DISTRICT_NAME" : "固安县",
			"SPELLING" : "Guan"
		},
		{
			"ID" : "131023",
			"PARENT_ID" : "131000",
			"DISTRICT_NAME" : "永清县",
			"SPELLING" : "Yongqing"
		},
		{
			"ID" : "131024",
			"PARENT_ID" : "131000",
			"DISTRICT_NAME" : "香河县",
			"SPELLING" : "Xianghe"
		},
		{
			"ID" : "131025",
			"PARENT_ID" : "131000",
			"DISTRICT_NAME" : "大城县",
			"SPELLING" : "Daicheng"
		},
		{
			"ID" : "131026",
			"PARENT_ID" : "131000",
			"DISTRICT_NAME" : "文安县",
			"SPELLING" : "Wenan"
		},
		{
			"ID" : "131028",
			"PARENT_ID" : "131000",
			"DISTRICT_NAME" : "大厂回族自治县",
			"SPELLING" : "Dachang"
		},
		{
			"ID" : "131081",
			"PARENT_ID" : "131000",
			"DISTRICT_NAME" : "霸州市",
			"SPELLING" : "Bazhou"
		},
		{
			"ID" : "131082",
			"PARENT_ID" : "131000",
			"DISTRICT_NAME" : "三河市",
			"SPELLING" : "Sanhe"
		},
		{
			"ID" : "131100",
			"PARENT_ID" : "130000",
			"DISTRICT_NAME" : "衡水市",
			"SPELLING" : "Hengshui"
		},
		{
			"ID" : "131102",
			"PARENT_ID" : "131100",
			"DISTRICT_NAME" : "桃城区",
			"SPELLING" : "Taocheng"
		},
		{
			"ID" : "131121",
			"PARENT_ID" : "131100",
			"DISTRICT_NAME" : "枣强县",
			"SPELLING" : "Zaoqiang"
		},
		{
			"ID" : "131122",
			"PARENT_ID" : "131100",
			"DISTRICT_NAME" : "武邑县",
			"SPELLING" : "Wuyi"
		},
		{
			"ID" : "131123",
			"PARENT_ID" : "131100",
			"DISTRICT_NAME" : "武强县",
			"SPELLING" : "Wuqiang"
		},
		{
			"ID" : "131124",
			"PARENT_ID" : "131100",
			"DISTRICT_NAME" : "饶阳县",
			"SPELLING" : "Raoyang"
		},
		{
			"ID" : "131125",
			"PARENT_ID" : "131100",
			"DISTRICT_NAME" : "安平县",
			"SPELLING" : "Anping"
		},
		{
			"ID" : "131126",
			"PARENT_ID" : "131100",
			"DISTRICT_NAME" : "故城县",
			"SPELLING" : "Gucheng"
		},
		{
			"ID" : "131127",
			"PARENT_ID" : "131100",
			"DISTRICT_NAME" : "景县",
			"SPELLING" : "Jingxian"
		},
		{
			"ID" : "131128",
			"PARENT_ID" : "131100",
			"DISTRICT_NAME" : "阜城县",
			"SPELLING" : "Fucheng"
		},
		{
			"ID" : "131181",
			"PARENT_ID" : "131100",
			"DISTRICT_NAME" : "冀州市",
			"SPELLING" : "Jizhou"
		},
		{
			"ID" : "131182",
			"PARENT_ID" : "131100",
			"DISTRICT_NAME" : "深州市",
			"SPELLING" : "Shenzhou"
		},
		{
			"ID" : "140000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "山西省",
			"SPELLING" : "Shanxi"
		},
		{
			"ID" : "140100",
			"PARENT_ID" : "140000",
			"DISTRICT_NAME" : "太原市",
			"SPELLING" : "Taiyuan"
		},
		{
			"ID" : "140105",
			"PARENT_ID" : "140100",
			"DISTRICT_NAME" : "小店区",
			"SPELLING" : "Xiaodian"
		},
		{
			"ID" : "140106",
			"PARENT_ID" : "140100",
			"DISTRICT_NAME" : "迎泽区",
			"SPELLING" : "Yingze"
		},
		{
			"ID" : "140107",
			"PARENT_ID" : "140100",
			"DISTRICT_NAME" : "杏花岭区",
			"SPELLING" : "Xinghualing"
		},
		{
			"ID" : "140108",
			"PARENT_ID" : "140100",
			"DISTRICT_NAME" : "尖草坪区",
			"SPELLING" : "Jiancaoping"
		},
		{
			"ID" : "140109",
			"PARENT_ID" : "140100",
			"DISTRICT_NAME" : "万柏林区",
			"SPELLING" : "Wanbailin"
		},
		{
			"ID" : "140110",
			"PARENT_ID" : "140100",
			"DISTRICT_NAME" : "晋源区",
			"SPELLING" : "Jinyuan"
		},
		{
			"ID" : "140121",
			"PARENT_ID" : "140100",
			"DISTRICT_NAME" : "清徐县",
			"SPELLING" : "Qingxu"
		},
		{
			"ID" : "140122",
			"PARENT_ID" : "140100",
			"DISTRICT_NAME" : "阳曲县",
			"SPELLING" : "Yangqu"
		},
		{
			"ID" : "140123",
			"PARENT_ID" : "140100",
			"DISTRICT_NAME" : "娄烦县",
			"SPELLING" : "Loufan"
		},
		{
			"ID" : "140181",
			"PARENT_ID" : "140100",
			"DISTRICT_NAME" : "古交市",
			"SPELLING" : "Gujiao"
		},
		{
			"ID" : "140200",
			"PARENT_ID" : "140000",
			"DISTRICT_NAME" : "大同市",
			"SPELLING" : "Datong"
		},
		{
			"ID" : "140202",
			"PARENT_ID" : "140200",
			"DISTRICT_NAME" : "城区",
			"SPELLING" : "Chengqu"
		},
		{
			"ID" : "140203",
			"PARENT_ID" : "140200",
			"DISTRICT_NAME" : "矿区",
			"SPELLING" : "Kuangqu"
		},
		{
			"ID" : "140211",
			"PARENT_ID" : "140200",
			"DISTRICT_NAME" : "南郊区",
			"SPELLING" : "Nanjiao"
		},
		{
			"ID" : "140212",
			"PARENT_ID" : "140200",
			"DISTRICT_NAME" : "新荣区",
			"SPELLING" : "Xinrong"
		},
		{
			"ID" : "140221",
			"PARENT_ID" : "140200",
			"DISTRICT_NAME" : "阳高县",
			"SPELLING" : "Yanggao"
		},
		{
			"ID" : "140222",
			"PARENT_ID" : "140200",
			"DISTRICT_NAME" : "天镇县",
			"SPELLING" : "Tianzhen"
		},
		{
			"ID" : "140223",
			"PARENT_ID" : "140200",
			"DISTRICT_NAME" : "广灵县",
			"SPELLING" : "Guangling"
		},
		{
			"ID" : "140224",
			"PARENT_ID" : "140200",
			"DISTRICT_NAME" : "灵丘县",
			"SPELLING" : "Lingqiu"
		},
		{
			"ID" : "140225",
			"PARENT_ID" : "140200",
			"DISTRICT_NAME" : "浑源县",
			"SPELLING" : "Hunyuan"
		},
		{
			"ID" : "140226",
			"PARENT_ID" : "140200",
			"DISTRICT_NAME" : "左云县",
			"SPELLING" : "Zuoyun"
		},
		{
			"ID" : "140227",
			"PARENT_ID" : "140200",
			"DISTRICT_NAME" : "大同县",
			"SPELLING" : "Datong"
		},
		{
			"ID" : "140300",
			"PARENT_ID" : "140000",
			"DISTRICT_NAME" : "阳泉市",
			"SPELLING" : "Yangquan"
		},
		{
			"ID" : "140302",
			"PARENT_ID" : "140300",
			"DISTRICT_NAME" : "城区",
			"SPELLING" : "Chengqu"
		},
		{
			"ID" : "140303",
			"PARENT_ID" : "140300",
			"DISTRICT_NAME" : "矿区",
			"SPELLING" : "Kuangqu"
		},
		{
			"ID" : "140311",
			"PARENT_ID" : "140300",
			"DISTRICT_NAME" : "郊区",
			"SPELLING" : "Jiaoqu"
		},
		{
			"ID" : "140321",
			"PARENT_ID" : "140300",
			"DISTRICT_NAME" : "平定县",
			"SPELLING" : "Pingding"
		},
		{
			"ID" : "140322",
			"PARENT_ID" : "140300",
			"DISTRICT_NAME" : "盂县",
			"SPELLING" : "Yuxian"
		},
		{
			"ID" : "140400",
			"PARENT_ID" : "140000",
			"DISTRICT_NAME" : "长治市",
			"SPELLING" : "Changzhi"
		},
		{
			"ID" : "140402",
			"PARENT_ID" : "140400",
			"DISTRICT_NAME" : "城区",
			"SPELLING" : "Chengqu"
		},
		{
			"ID" : "140411",
			"PARENT_ID" : "140400",
			"DISTRICT_NAME" : "郊区",
			"SPELLING" : "Jiaoqu"
		},
		{
			"ID" : "140421",
			"PARENT_ID" : "140400",
			"DISTRICT_NAME" : "长治县",
			"SPELLING" : "Changzhi"
		},
		{
			"ID" : "140423",
			"PARENT_ID" : "140400",
			"DISTRICT_NAME" : "襄垣县",
			"SPELLING" : "Xiangyuan"
		},
		{
			"ID" : "140424",
			"PARENT_ID" : "140400",
			"DISTRICT_NAME" : "屯留县",
			"SPELLING" : "Tunliu"
		},
		{
			"ID" : "140425",
			"PARENT_ID" : "140400",
			"DISTRICT_NAME" : "平顺县",
			"SPELLING" : "Pingshun"
		},
		{
			"ID" : "140426",
			"PARENT_ID" : "140400",
			"DISTRICT_NAME" : "黎城县",
			"SPELLING" : "Licheng"
		},
		{
			"ID" : "140427",
			"PARENT_ID" : "140400",
			"DISTRICT_NAME" : "壶关县",
			"SPELLING" : "Huguan"
		},
		{
			"ID" : "140428",
			"PARENT_ID" : "140400",
			"DISTRICT_NAME" : "长子县",
			"SPELLING" : "Zhangzi"
		},
		{
			"ID" : "140429",
			"PARENT_ID" : "140400",
			"DISTRICT_NAME" : "武乡县",
			"SPELLING" : "Wuxiang"
		},
		{
			"ID" : "140430",
			"PARENT_ID" : "140400",
			"DISTRICT_NAME" : "沁县",
			"SPELLING" : "Qinxian"
		},
		{
			"ID" : "140431",
			"PARENT_ID" : "140400",
			"DISTRICT_NAME" : "沁源县",
			"SPELLING" : "Qinyuan"
		},
		{
			"ID" : "140481",
			"PARENT_ID" : "140400",
			"DISTRICT_NAME" : "潞城市",
			"SPELLING" : "Lucheng"
		},
		{
			"ID" : "140500",
			"PARENT_ID" : "140000",
			"DISTRICT_NAME" : "晋城市",
			"SPELLING" : "Jincheng"
		},
		{
			"ID" : "140502",
			"PARENT_ID" : "140500",
			"DISTRICT_NAME" : "城区",
			"SPELLING" : "Chengqu"
		},
		{
			"ID" : "140521",
			"PARENT_ID" : "140500",
			"DISTRICT_NAME" : "沁水县",
			"SPELLING" : "Qinshui"
		},
		{
			"ID" : "140522",
			"PARENT_ID" : "140500",
			"DISTRICT_NAME" : "阳城县",
			"SPELLING" : "Yangcheng"
		},
		{
			"ID" : "140524",
			"PARENT_ID" : "140500",
			"DISTRICT_NAME" : "陵川县",
			"SPELLING" : "Lingchuan"
		},
		{
			"ID" : "140525",
			"PARENT_ID" : "140500",
			"DISTRICT_NAME" : "泽州县",
			"SPELLING" : "Zezhou"
		},
		{
			"ID" : "140581",
			"PARENT_ID" : "140500",
			"DISTRICT_NAME" : "高平市",
			"SPELLING" : "Gaoping"
		},
		{
			"ID" : "140600",
			"PARENT_ID" : "140000",
			"DISTRICT_NAME" : "朔州市",
			"SPELLING" : "Shuozhou"
		},
		{
			"ID" : "140602",
			"PARENT_ID" : "140600",
			"DISTRICT_NAME" : "朔城区",
			"SPELLING" : "Shuocheng"
		},
		{
			"ID" : "140603",
			"PARENT_ID" : "140600",
			"DISTRICT_NAME" : "平鲁区",
			"SPELLING" : "Pinglu"
		},
		{
			"ID" : "140621",
			"PARENT_ID" : "140600",
			"DISTRICT_NAME" : "山阴县",
			"SPELLING" : "Shanyin"
		},
		{
			"ID" : "140622",
			"PARENT_ID" : "140600",
			"DISTRICT_NAME" : "应县",
			"SPELLING" : "Yingxian"
		},
		{
			"ID" : "140623",
			"PARENT_ID" : "140600",
			"DISTRICT_NAME" : "右玉县",
			"SPELLING" : "Youyu"
		},
		{
			"ID" : "140624",
			"PARENT_ID" : "140600",
			"DISTRICT_NAME" : "怀仁县",
			"SPELLING" : "Huairen"
		},
		{
			"ID" : "140700",
			"PARENT_ID" : "140000",
			"DISTRICT_NAME" : "晋中市",
			"SPELLING" : "Jinzhong"
		},
		{
			"ID" : "140702",
			"PARENT_ID" : "140700",
			"DISTRICT_NAME" : "榆次区",
			"SPELLING" : "Yuci"
		},
		{
			"ID" : "140721",
			"PARENT_ID" : "140700",
			"DISTRICT_NAME" : "榆社县",
			"SPELLING" : "Yushe"
		},
		{
			"ID" : "140722",
			"PARENT_ID" : "140700",
			"DISTRICT_NAME" : "左权县",
			"SPELLING" : "Zuoquan"
		},
		{
			"ID" : "140723",
			"PARENT_ID" : "140700",
			"DISTRICT_NAME" : "和顺县",
			"SPELLING" : "Heshun"
		},
		{
			"ID" : "140724",
			"PARENT_ID" : "140700",
			"DISTRICT_NAME" : "昔阳县",
			"SPELLING" : "Xiyang"
		},
		{
			"ID" : "140725",
			"PARENT_ID" : "140700",
			"DISTRICT_NAME" : "寿阳县",
			"SPELLING" : "Shouyang"
		},
		{
			"ID" : "140726",
			"PARENT_ID" : "140700",
			"DISTRICT_NAME" : "太谷县",
			"SPELLING" : "Taigu"
		},
		{
			"ID" : "140727",
			"PARENT_ID" : "140700",
			"DISTRICT_NAME" : "祁县",
			"SPELLING" : "Qixian"
		},
		{
			"ID" : "140728",
			"PARENT_ID" : "140700",
			"DISTRICT_NAME" : "平遥县",
			"SPELLING" : "Pingyao"
		},
		{
			"ID" : "140729",
			"PARENT_ID" : "140700",
			"DISTRICT_NAME" : "灵石县",
			"SPELLING" : "Lingshi"
		},
		{
			"ID" : "140781",
			"PARENT_ID" : "140700",
			"DISTRICT_NAME" : "介休市",
			"SPELLING" : "Jiexiu"
		},
		{
			"ID" : "140800",
			"PARENT_ID" : "140000",
			"DISTRICT_NAME" : "运城市",
			"SPELLING" : "Yuncheng"
		},
		{
			"ID" : "140802",
			"PARENT_ID" : "140800",
			"DISTRICT_NAME" : "盐湖区",
			"SPELLING" : "Yanhu"
		},
		{
			"ID" : "140821",
			"PARENT_ID" : "140800",
			"DISTRICT_NAME" : "临猗县",
			"SPELLING" : "Linyi"
		},
		{
			"ID" : "140822",
			"PARENT_ID" : "140800",
			"DISTRICT_NAME" : "万荣县",
			"SPELLING" : "Wanrong"
		},
		{
			"ID" : "140823",
			"PARENT_ID" : "140800",
			"DISTRICT_NAME" : "闻喜县",
			"SPELLING" : "Wenxi"
		},
		{
			"ID" : "140824",
			"PARENT_ID" : "140800",
			"DISTRICT_NAME" : "稷山县",
			"SPELLING" : "Jishan"
		},
		{
			"ID" : "140825",
			"PARENT_ID" : "140800",
			"DISTRICT_NAME" : "新绛县",
			"SPELLING" : "Xinjiang"
		},
		{
			"ID" : "140826",
			"PARENT_ID" : "140800",
			"DISTRICT_NAME" : "绛县",
			"SPELLING" : "Jiangxian"
		},
		{
			"ID" : "140827",
			"PARENT_ID" : "140800",
			"DISTRICT_NAME" : "垣曲县",
			"SPELLING" : "Yuanqu"
		},
		{
			"ID" : "140828",
			"PARENT_ID" : "140800",
			"DISTRICT_NAME" : "夏县",
			"SPELLING" : "Xiaxian"
		},
		{
			"ID" : "140829",
			"PARENT_ID" : "140800",
			"DISTRICT_NAME" : "平陆县",
			"SPELLING" : "Pinglu"
		},
		{
			"ID" : "140830",
			"PARENT_ID" : "140800",
			"DISTRICT_NAME" : "芮城县",
			"SPELLING" : "Ruicheng"
		},
		{
			"ID" : "140881",
			"PARENT_ID" : "140800",
			"DISTRICT_NAME" : "永济市",
			"SPELLING" : "Yongji"
		},
		{
			"ID" : "140882",
			"PARENT_ID" : "140800",
			"DISTRICT_NAME" : "河津市",
			"SPELLING" : "Hejin"
		},
		{
			"ID" : "140900",
			"PARENT_ID" : "140000",
			"DISTRICT_NAME" : "忻州市",
			"SPELLING" : "Xinzhou"
		},
		{
			"ID" : "140902",
			"PARENT_ID" : "140900",
			"DISTRICT_NAME" : "忻府区",
			"SPELLING" : "Xinfu"
		},
		{
			"ID" : "140921",
			"PARENT_ID" : "140900",
			"DISTRICT_NAME" : "定襄县",
			"SPELLING" : "Dingxiang"
		},
		{
			"ID" : "140922",
			"PARENT_ID" : "140900",
			"DISTRICT_NAME" : "五台县",
			"SPELLING" : "Wutai"
		},
		{
			"ID" : "140923",
			"PARENT_ID" : "140900",
			"DISTRICT_NAME" : "代县",
			"SPELLING" : "Daixian"
		},
		{
			"ID" : "140924",
			"PARENT_ID" : "140900",
			"DISTRICT_NAME" : "繁峙县",
			"SPELLING" : "Fanshi"
		},
		{
			"ID" : "140925",
			"PARENT_ID" : "140900",
			"DISTRICT_NAME" : "宁武县",
			"SPELLING" : "Ningwu"
		},
		{
			"ID" : "140926",
			"PARENT_ID" : "140900",
			"DISTRICT_NAME" : "静乐县",
			"SPELLING" : "Jingle"
		},
		{
			"ID" : "140927",
			"PARENT_ID" : "140900",
			"DISTRICT_NAME" : "神池县",
			"SPELLING" : "Shenchi"
		},
		{
			"ID" : "140928",
			"PARENT_ID" : "140900",
			"DISTRICT_NAME" : "五寨县",
			"SPELLING" : "Wuzhai"
		},
		{
			"ID" : "140929",
			"PARENT_ID" : "140900",
			"DISTRICT_NAME" : "岢岚县",
			"SPELLING" : "Kelan"
		},
		{
			"ID" : "140930",
			"PARENT_ID" : "140900",
			"DISTRICT_NAME" : "河曲县",
			"SPELLING" : "Hequ"
		},
		{
			"ID" : "140931",
			"PARENT_ID" : "140900",
			"DISTRICT_NAME" : "保德县",
			"SPELLING" : "Baode"
		},
		{
			"ID" : "140932",
			"PARENT_ID" : "140900",
			"DISTRICT_NAME" : "偏关县",
			"SPELLING" : "Pianguan"
		},
		{
			"ID" : "140981",
			"PARENT_ID" : "140900",
			"DISTRICT_NAME" : "原平市",
			"SPELLING" : "Yuanping"
		},
		{
			"ID" : "141000",
			"PARENT_ID" : "140000",
			"DISTRICT_NAME" : "临汾市",
			"SPELLING" : "Linfen"
		},
		{
			"ID" : "141002",
			"PARENT_ID" : "141000",
			"DISTRICT_NAME" : "尧都区",
			"SPELLING" : "Yaodu"
		},
		{
			"ID" : "141021",
			"PARENT_ID" : "141000",
			"DISTRICT_NAME" : "曲沃县",
			"SPELLING" : "Quwo"
		},
		{
			"ID" : "141022",
			"PARENT_ID" : "141000",
			"DISTRICT_NAME" : "翼城县",
			"SPELLING" : "Yicheng"
		},
		{
			"ID" : "141023",
			"PARENT_ID" : "141000",
			"DISTRICT_NAME" : "襄汾县",
			"SPELLING" : "Xiangfen"
		},
		{
			"ID" : "141024",
			"PARENT_ID" : "141000",
			"DISTRICT_NAME" : "洪洞县",
			"SPELLING" : "Hongtong"
		},
		{
			"ID" : "141025",
			"PARENT_ID" : "141000",
			"DISTRICT_NAME" : "古县",
			"SPELLING" : "Guxian"
		},
		{
			"ID" : "141026",
			"PARENT_ID" : "141000",
			"DISTRICT_NAME" : "安泽县",
			"SPELLING" : "Anze"
		},
		{
			"ID" : "141027",
			"PARENT_ID" : "141000",
			"DISTRICT_NAME" : "浮山县",
			"SPELLING" : "Fushan"
		},
		{
			"ID" : "141028",
			"PARENT_ID" : "141000",
			"DISTRICT_NAME" : "吉县",
			"SPELLING" : "Jixian"
		},
		{
			"ID" : "141029",
			"PARENT_ID" : "141000",
			"DISTRICT_NAME" : "乡宁县",
			"SPELLING" : "Xiangning"
		},
		{
			"ID" : "141030",
			"PARENT_ID" : "141000",
			"DISTRICT_NAME" : "大宁县",
			"SPELLING" : "Daning"
		},
		{
			"ID" : "141031",
			"PARENT_ID" : "141000",
			"DISTRICT_NAME" : "隰县",
			"SPELLING" : "Xixian"
		},
		{
			"ID" : "141032",
			"PARENT_ID" : "141000",
			"DISTRICT_NAME" : "永和县",
			"SPELLING" : "Yonghe"
		},
		{
			"ID" : "141033",
			"PARENT_ID" : "141000",
			"DISTRICT_NAME" : "蒲县",
			"SPELLING" : "Puxian"
		},
		{
			"ID" : "141034",
			"PARENT_ID" : "141000",
			"DISTRICT_NAME" : "汾西县",
			"SPELLING" : "Fenxi"
		},
		{
			"ID" : "141081",
			"PARENT_ID" : "141000",
			"DISTRICT_NAME" : "侯马市",
			"SPELLING" : "Houma"
		},
		{
			"ID" : "141082",
			"PARENT_ID" : "141000",
			"DISTRICT_NAME" : "霍州市",
			"SPELLING" : "Huozhou"
		},
		{
			"ID" : "141100",
			"PARENT_ID" : "140000",
			"DISTRICT_NAME" : "吕梁市",
			"SPELLING" : "Lvliang"
		},
		{
			"ID" : "141102",
			"PARENT_ID" : "141100",
			"DISTRICT_NAME" : "离石区",
			"SPELLING" : "Lishi"
		},
		{
			"ID" : "141121",
			"PARENT_ID" : "141100",
			"DISTRICT_NAME" : "文水县",
			"SPELLING" : "Wenshui"
		},
		{
			"ID" : "141122",
			"PARENT_ID" : "141100",
			"DISTRICT_NAME" : "交城县",
			"SPELLING" : "Jiaocheng"
		},
		{
			"ID" : "141123",
			"PARENT_ID" : "141100",
			"DISTRICT_NAME" : "兴县",
			"SPELLING" : "Xingxian"
		},
		{
			"ID" : "141124",
			"PARENT_ID" : "141100",
			"DISTRICT_NAME" : "临县",
			"SPELLING" : "Linxian"
		},
		{
			"ID" : "141125",
			"PARENT_ID" : "141100",
			"DISTRICT_NAME" : "柳林县",
			"SPELLING" : "Liulin"
		},
		{
			"ID" : "141126",
			"PARENT_ID" : "141100",
			"DISTRICT_NAME" : "石楼县",
			"SPELLING" : "Shilou"
		},
		{
			"ID" : "141127",
			"PARENT_ID" : "141100",
			"DISTRICT_NAME" : "岚县",
			"SPELLING" : "Lanxian"
		},
		{
			"ID" : "141128",
			"PARENT_ID" : "141100",
			"DISTRICT_NAME" : "方山县",
			"SPELLING" : "Fangshan"
		},
		{
			"ID" : "141129",
			"PARENT_ID" : "141100",
			"DISTRICT_NAME" : "中阳县",
			"SPELLING" : "Zhongyang"
		},
		{
			"ID" : "141130",
			"PARENT_ID" : "141100",
			"DISTRICT_NAME" : "交口县",
			"SPELLING" : "Jiaokou"
		},
		{
			"ID" : "141181",
			"PARENT_ID" : "141100",
			"DISTRICT_NAME" : "孝义市",
			"SPELLING" : "Xiaoyi"
		},
		{
			"ID" : "141182",
			"PARENT_ID" : "141100",
			"DISTRICT_NAME" : "汾阳市",
			"SPELLING" : "Fenyang"
		},
		{
			"ID" : "150000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "内蒙古自治区",
			"SPELLING" : "Inner Mongolia"
		},
		{
			"ID" : "150100",
			"PARENT_ID" : "150000",
			"DISTRICT_NAME" : "呼和浩特市",
			"SPELLING" : "Hohhot"
		},
		{
			"ID" : "150102",
			"PARENT_ID" : "150100",
			"DISTRICT_NAME" : "新城区",
			"SPELLING" : "Xincheng"
		},
		{
			"ID" : "150103",
			"PARENT_ID" : "150100",
			"DISTRICT_NAME" : "回民区",
			"SPELLING" : "Huimin"
		},
		{
			"ID" : "150104",
			"PARENT_ID" : "150100",
			"DISTRICT_NAME" : "玉泉区",
			"SPELLING" : "Yuquan"
		},
		{
			"ID" : "150105",
			"PARENT_ID" : "150100",
			"DISTRICT_NAME" : "赛罕区",
			"SPELLING" : "Saihan"
		},
		{
			"ID" : "150121",
			"PARENT_ID" : "150100",
			"DISTRICT_NAME" : "土默特左旗",
			"SPELLING" : "Tumotezuoqi"
		},
		{
			"ID" : "150122",
			"PARENT_ID" : "150100",
			"DISTRICT_NAME" : "托克托县",
			"SPELLING" : "Tuoketuo"
		},
		{
			"ID" : "150123",
			"PARENT_ID" : "150100",
			"DISTRICT_NAME" : "和林格尔县",
			"SPELLING" : "Helingeer"
		},
		{
			"ID" : "150124",
			"PARENT_ID" : "150100",
			"DISTRICT_NAME" : "清水河县",
			"SPELLING" : "Qingshuihe"
		},
		{
			"ID" : "150125",
			"PARENT_ID" : "150100",
			"DISTRICT_NAME" : "武川县",
			"SPELLING" : "Wuchuan"
		},
		{
			"ID" : "150200",
			"PARENT_ID" : "150000",
			"DISTRICT_NAME" : "包头市",
			"SPELLING" : "Baotou"
		},
		{
			"ID" : "150202",
			"PARENT_ID" : "150200",
			"DISTRICT_NAME" : "东河区",
			"SPELLING" : "Donghe"
		},
		{
			"ID" : "150203",
			"PARENT_ID" : "150200",
			"DISTRICT_NAME" : "昆都仑区",
			"SPELLING" : "Kundulun"
		},
		{
			"ID" : "150204",
			"PARENT_ID" : "150200",
			"DISTRICT_NAME" : "青山区",
			"SPELLING" : "Qingshan"
		},
		{
			"ID" : "150205",
			"PARENT_ID" : "150200",
			"DISTRICT_NAME" : "石拐区",
			"SPELLING" : "Shiguai"
		},
		{
			"ID" : "150206",
			"PARENT_ID" : "150200",
			"DISTRICT_NAME" : "白云鄂博矿区",
			"SPELLING" : "Baiyunebokuangqu"
		},
		{
			"ID" : "150207",
			"PARENT_ID" : "150200",
			"DISTRICT_NAME" : "九原区",
			"SPELLING" : "Jiuyuan"
		},
		{
			"ID" : "150221",
			"PARENT_ID" : "150200",
			"DISTRICT_NAME" : "土默特右旗",
			"SPELLING" : "Tumoteyouqi"
		},
		{
			"ID" : "150222",
			"PARENT_ID" : "150200",
			"DISTRICT_NAME" : "固阳县",
			"SPELLING" : "Guyang"
		},
		{
			"ID" : "150223",
			"PARENT_ID" : "150200",
			"DISTRICT_NAME" : "达尔罕茂明安联合旗",
			"SPELLING" : "Damaoqi"
		},
		{
			"ID" : "150300",
			"PARENT_ID" : "150000",
			"DISTRICT_NAME" : "乌海市",
			"SPELLING" : "Wuhai"
		},
		{
			"ID" : "150302",
			"PARENT_ID" : "150300",
			"DISTRICT_NAME" : "海勃湾区",
			"SPELLING" : "Haibowan"
		},
		{
			"ID" : "150303",
			"PARENT_ID" : "150300",
			"DISTRICT_NAME" : "海南区",
			"SPELLING" : "Hainan"
		},
		{
			"ID" : "150304",
			"PARENT_ID" : "150300",
			"DISTRICT_NAME" : "乌达区",
			"SPELLING" : "Wuda"
		},
		{
			"ID" : "150400",
			"PARENT_ID" : "150000",
			"DISTRICT_NAME" : "赤峰市",
			"SPELLING" : "Chifeng"
		},
		{
			"ID" : "150402",
			"PARENT_ID" : "150400",
			"DISTRICT_NAME" : "红山区",
			"SPELLING" : "Hongshan"
		},
		{
			"ID" : "150403",
			"PARENT_ID" : "150400",
			"DISTRICT_NAME" : "元宝山区",
			"SPELLING" : "Yuanbaoshan"
		},
		{
			"ID" : "150404",
			"PARENT_ID" : "150400",
			"DISTRICT_NAME" : "松山区",
			"SPELLING" : "Songshan"
		},
		{
			"ID" : "150421",
			"PARENT_ID" : "150400",
			"DISTRICT_NAME" : "阿鲁科尔沁旗",
			"SPELLING" : "Alukeerqinqi"
		},
		{
			"ID" : "150422",
			"PARENT_ID" : "150400",
			"DISTRICT_NAME" : "巴林左旗",
			"SPELLING" : "Balinzuoqi"
		},
		{
			"ID" : "150423",
			"PARENT_ID" : "150400",
			"DISTRICT_NAME" : "巴林右旗",
			"SPELLING" : "Balinyouqi"
		},
		{
			"ID" : "150424",
			"PARENT_ID" : "150400",
			"DISTRICT_NAME" : "林西县",
			"SPELLING" : "Linxi"
		},
		{
			"ID" : "150425",
			"PARENT_ID" : "150400",
			"DISTRICT_NAME" : "克什克腾旗",
			"SPELLING" : "Keshiketengqi"
		},
		{
			"ID" : "150426",
			"PARENT_ID" : "150400",
			"DISTRICT_NAME" : "翁牛特旗",
			"SPELLING" : "Wengniuteqi"
		},
		{
			"ID" : "150428",
			"PARENT_ID" : "150400",
			"DISTRICT_NAME" : "喀喇沁旗",
			"SPELLING" : "Kalaqinqi"
		},
		{
			"ID" : "150429",
			"PARENT_ID" : "150400",
			"DISTRICT_NAME" : "宁城县",
			"SPELLING" : "Ningcheng"
		},
		{
			"ID" : "150430",
			"PARENT_ID" : "150400",
			"DISTRICT_NAME" : "敖汉旗",
			"SPELLING" : "Aohanqi"
		},
		{
			"ID" : "150500",
			"PARENT_ID" : "150000",
			"DISTRICT_NAME" : "通辽市",
			"SPELLING" : "Tongliao"
		},
		{
			"ID" : "150502",
			"PARENT_ID" : "150500",
			"DISTRICT_NAME" : "科尔沁区",
			"SPELLING" : "Keerqin"
		},
		{
			"ID" : "150521",
			"PARENT_ID" : "150500",
			"DISTRICT_NAME" : "科尔沁左翼中旗",
			"SPELLING" : "Keerqinzuoyizhongqi"
		},
		{
			"ID" : "150522",
			"PARENT_ID" : "150500",
			"DISTRICT_NAME" : "科尔沁左翼后旗",
			"SPELLING" : "Keerqinzuoyihouqi"
		},
		{
			"ID" : "150523",
			"PARENT_ID" : "150500",
			"DISTRICT_NAME" : "开鲁县",
			"SPELLING" : "Kailu"
		},
		{
			"ID" : "150524",
			"PARENT_ID" : "150500",
			"DISTRICT_NAME" : "库伦旗",
			"SPELLING" : "Kulunqi"
		},
		{
			"ID" : "150525",
			"PARENT_ID" : "150500",
			"DISTRICT_NAME" : "奈曼旗",
			"SPELLING" : "Naimanqi"
		},
		{
			"ID" : "150526",
			"PARENT_ID" : "150500",
			"DISTRICT_NAME" : "扎鲁特旗",
			"SPELLING" : "Zhaluteqi"
		},
		{
			"ID" : "150581",
			"PARENT_ID" : "150500",
			"DISTRICT_NAME" : "霍林郭勒市",
			"SPELLING" : "Huolinguole"
		},
		{
			"ID" : "150600",
			"PARENT_ID" : "150000",
			"DISTRICT_NAME" : "鄂尔多斯市",
			"SPELLING" : "Ordos"
		},
		{
			"ID" : "150602",
			"PARENT_ID" : "150600",
			"DISTRICT_NAME" : "东胜区",
			"SPELLING" : "Dongsheng"
		},
		{
			"ID" : "150621",
			"PARENT_ID" : "150600",
			"DISTRICT_NAME" : "达拉特旗",
			"SPELLING" : "Dalateqi"
		},
		{
			"ID" : "150622",
			"PARENT_ID" : "150600",
			"DISTRICT_NAME" : "准格尔旗",
			"SPELLING" : "Zhungeerqi"
		},
		{
			"ID" : "150623",
			"PARENT_ID" : "150600",
			"DISTRICT_NAME" : "鄂托克前旗",
			"SPELLING" : "Etuokeqianqi"
		},
		{
			"ID" : "150624",
			"PARENT_ID" : "150600",
			"DISTRICT_NAME" : "鄂托克旗",
			"SPELLING" : "Etuokeqi"
		},
		{
			"ID" : "150625",
			"PARENT_ID" : "150600",
			"DISTRICT_NAME" : "杭锦旗",
			"SPELLING" : "Hangjinqi"
		},
		{
			"ID" : "150626",
			"PARENT_ID" : "150600",
			"DISTRICT_NAME" : "乌审旗",
			"SPELLING" : "Wushenqi"
		},
		{
			"ID" : "150627",
			"PARENT_ID" : "150600",
			"DISTRICT_NAME" : "伊金霍洛旗",
			"SPELLING" : "Yijinhuoluoqi"
		},
		{
			"ID" : "150700",
			"PARENT_ID" : "150000",
			"DISTRICT_NAME" : "呼伦贝尔市",
			"SPELLING" : "Hulunber"
		},
		{
			"ID" : "150702",
			"PARENT_ID" : "150700",
			"DISTRICT_NAME" : "海拉尔区",
			"SPELLING" : "Hailaer"
		},
		{
			"ID" : "150703",
			"PARENT_ID" : "150700",
			"DISTRICT_NAME" : "扎赉诺尔区",
			"SPELLING" : "Zhalainuoer"
		},
		{
			"ID" : "150721",
			"PARENT_ID" : "150700",
			"DISTRICT_NAME" : "阿荣旗",
			"SPELLING" : "Arongqi"
		},
		{
			"ID" : "150722",
			"PARENT_ID" : "150700",
			"DISTRICT_NAME" : "莫力达瓦达斡尔族自治旗",
			"SPELLING" : "Moqi"
		},
		{
			"ID" : "150723",
			"PARENT_ID" : "150700",
			"DISTRICT_NAME" : "鄂伦春自治旗",
			"SPELLING" : "Elunchun"
		},
		{
			"ID" : "150724",
			"PARENT_ID" : "150700",
			"DISTRICT_NAME" : "鄂温克族自治旗",
			"SPELLING" : "Ewen"
		},
		{
			"ID" : "150725",
			"PARENT_ID" : "150700",
			"DISTRICT_NAME" : "陈巴尔虎旗",
			"SPELLING" : "Chenbaerhuqi"
		},
		{
			"ID" : "150726",
			"PARENT_ID" : "150700",
			"DISTRICT_NAME" : "新巴尔虎左旗",
			"SPELLING" : "Xinbaerhuzuoqi"
		},
		{
			"ID" : "150727",
			"PARENT_ID" : "150700",
			"DISTRICT_NAME" : "新巴尔虎右旗",
			"SPELLING" : "Xinbaerhuyouqi"
		},
		{
			"ID" : "150781",
			"PARENT_ID" : "150700",
			"DISTRICT_NAME" : "满洲里市",
			"SPELLING" : "Manzhouli"
		},
		{
			"ID" : "150782",
			"PARENT_ID" : "150700",
			"DISTRICT_NAME" : "牙克石市",
			"SPELLING" : "Yakeshi"
		},
		{
			"ID" : "150783",
			"PARENT_ID" : "150700",
			"DISTRICT_NAME" : "扎兰屯市",
			"SPELLING" : "Zhalantun"
		},
		{
			"ID" : "150784",
			"PARENT_ID" : "150700",
			"DISTRICT_NAME" : "额尔古纳市",
			"SPELLING" : "Eerguna"
		},
		{
			"ID" : "150785",
			"PARENT_ID" : "150700",
			"DISTRICT_NAME" : "根河市",
			"SPELLING" : "Genhe"
		},
		{
			"ID" : "150800",
			"PARENT_ID" : "150000",
			"DISTRICT_NAME" : "巴彦淖尔市",
			"SPELLING" : "Bayan Nur"
		},
		{
			"ID" : "150802",
			"PARENT_ID" : "150800",
			"DISTRICT_NAME" : "临河区",
			"SPELLING" : "Linhe"
		},
		{
			"ID" : "150821",
			"PARENT_ID" : "150800",
			"DISTRICT_NAME" : "五原县",
			"SPELLING" : "Wuyuan"
		},
		{
			"ID" : "150822",
			"PARENT_ID" : "150800",
			"DISTRICT_NAME" : "磴口县",
			"SPELLING" : "Dengkou"
		},
		{
			"ID" : "150823",
			"PARENT_ID" : "150800",
			"DISTRICT_NAME" : "乌拉特前旗",
			"SPELLING" : "Wulateqianqi"
		},
		{
			"ID" : "150824",
			"PARENT_ID" : "150800",
			"DISTRICT_NAME" : "乌拉特中旗",
			"SPELLING" : "Wulatezhongqi"
		},
		{
			"ID" : "150825",
			"PARENT_ID" : "150800",
			"DISTRICT_NAME" : "乌拉特后旗",
			"SPELLING" : "Wulatehouqi"
		},
		{
			"ID" : "150826",
			"PARENT_ID" : "150800",
			"DISTRICT_NAME" : "杭锦后旗",
			"SPELLING" : "Hangjinhouqi"
		},
		{
			"ID" : "150900",
			"PARENT_ID" : "150000",
			"DISTRICT_NAME" : "乌兰察布市",
			"SPELLING" : "Ulanqab"
		},
		{
			"ID" : "150902",
			"PARENT_ID" : "150900",
			"DISTRICT_NAME" : "集宁区",
			"SPELLING" : "Jining"
		},
		{
			"ID" : "150921",
			"PARENT_ID" : "150900",
			"DISTRICT_NAME" : "卓资县",
			"SPELLING" : "Zhuozi"
		},
		{
			"ID" : "150922",
			"PARENT_ID" : "150900",
			"DISTRICT_NAME" : "化德县",
			"SPELLING" : "Huade"
		},
		{
			"ID" : "150923",
			"PARENT_ID" : "150900",
			"DISTRICT_NAME" : "商都县",
			"SPELLING" : "Shangdu"
		},
		{
			"ID" : "150924",
			"PARENT_ID" : "150900",
			"DISTRICT_NAME" : "兴和县",
			"SPELLING" : "Xinghe"
		},
		{
			"ID" : "150925",
			"PARENT_ID" : "150900",
			"DISTRICT_NAME" : "凉城县",
			"SPELLING" : "Liangcheng"
		},
		{
			"ID" : "150926",
			"PARENT_ID" : "150900",
			"DISTRICT_NAME" : "察哈尔右翼前旗",
			"SPELLING" : "Chayouqianqi"
		},
		{
			"ID" : "150927",
			"PARENT_ID" : "150900",
			"DISTRICT_NAME" : "察哈尔右翼中旗",
			"SPELLING" : "Chayouzhongqi"
		},
		{
			"ID" : "150928",
			"PARENT_ID" : "150900",
			"DISTRICT_NAME" : "察哈尔右翼后旗",
			"SPELLING" : "Chayouhouqi"
		},
		{
			"ID" : "150929",
			"PARENT_ID" : "150900",
			"DISTRICT_NAME" : "四子王旗",
			"SPELLING" : "Siziwangqi"
		},
		{
			"ID" : "150981",
			"PARENT_ID" : "150900",
			"DISTRICT_NAME" : "丰镇市",
			"SPELLING" : "Fengzhen"
		},
		{
			"ID" : "152200",
			"PARENT_ID" : "150000",
			"DISTRICT_NAME" : "兴安盟",
			"SPELLING" : "Hinggan"
		},
		{
			"ID" : "152201",
			"PARENT_ID" : "152200",
			"DISTRICT_NAME" : "乌兰浩特市",
			"SPELLING" : "Wulanhaote"
		},
		{
			"ID" : "152202",
			"PARENT_ID" : "152200",
			"DISTRICT_NAME" : "阿尔山市",
			"SPELLING" : "Aershan"
		},
		{
			"ID" : "152221",
			"PARENT_ID" : "152200",
			"DISTRICT_NAME" : "科尔沁右翼前旗",
			"SPELLING" : "Keyouqianqi"
		},
		{
			"ID" : "152222",
			"PARENT_ID" : "152200",
			"DISTRICT_NAME" : "科尔沁右翼中旗",
			"SPELLING" : "Keyouzhongqi"
		},
		{
			"ID" : "152223",
			"PARENT_ID" : "152200",
			"DISTRICT_NAME" : "扎赉特旗",
			"SPELLING" : "Zhalaiteqi"
		},
		{
			"ID" : "152224",
			"PARENT_ID" : "152200",
			"DISTRICT_NAME" : "突泉县",
			"SPELLING" : "Tuquan"
		},
		{
			"ID" : "152500",
			"PARENT_ID" : "150000",
			"DISTRICT_NAME" : "锡林郭勒盟",
			"SPELLING" : "Xilin Gol"
		},
		{
			"ID" : "152501",
			"PARENT_ID" : "152500",
			"DISTRICT_NAME" : "二连浩特市",
			"SPELLING" : "Erlianhaote"
		},
		{
			"ID" : "152502",
			"PARENT_ID" : "152500",
			"DISTRICT_NAME" : "锡林浩特市",
			"SPELLING" : "Xilinhaote"
		},
		{
			"ID" : "152522",
			"PARENT_ID" : "152500",
			"DISTRICT_NAME" : "阿巴嘎旗",
			"SPELLING" : "Abagaqi"
		},
		{
			"ID" : "152523",
			"PARENT_ID" : "152500",
			"DISTRICT_NAME" : "苏尼特左旗",
			"SPELLING" : "Sunitezuoqi"
		},
		{
			"ID" : "152524",
			"PARENT_ID" : "152500",
			"DISTRICT_NAME" : "苏尼特右旗",
			"SPELLING" : "Suniteyouqi"
		},
		{
			"ID" : "152525",
			"PARENT_ID" : "152500",
			"DISTRICT_NAME" : "东乌珠穆沁旗",
			"SPELLING" : "Dongwuqi"
		},
		{
			"ID" : "152526",
			"PARENT_ID" : "152500",
			"DISTRICT_NAME" : "西乌珠穆沁旗",
			"SPELLING" : "Xiwuqi"
		},
		{
			"ID" : "152527",
			"PARENT_ID" : "152500",
			"DISTRICT_NAME" : "太仆寺旗",
			"SPELLING" : "Taipusiqi"
		},
		{
			"ID" : "152528",
			"PARENT_ID" : "152500",
			"DISTRICT_NAME" : "镶黄旗",
			"SPELLING" : "Xianghuangqi"
		},
		{
			"ID" : "152529",
			"PARENT_ID" : "152500",
			"DISTRICT_NAME" : "正镶白旗",
			"SPELLING" : "Zhengxiangbaiqi"
		},
		{
			"ID" : "152530",
			"PARENT_ID" : "152500",
			"DISTRICT_NAME" : "正蓝旗",
			"SPELLING" : "Zhenglanqi"
		},
		{
			"ID" : "152531",
			"PARENT_ID" : "152500",
			"DISTRICT_NAME" : "多伦县",
			"SPELLING" : "Duolun"
		},
		{
			"ID" : "152900",
			"PARENT_ID" : "150000",
			"DISTRICT_NAME" : "阿拉善盟",
			"SPELLING" : "Alxa"
		},
		{
			"ID" : "152921",
			"PARENT_ID" : "152900",
			"DISTRICT_NAME" : "阿拉善左旗",
			"SPELLING" : "Alashanzuoqi"
		},
		{
			"ID" : "152922",
			"PARENT_ID" : "152900",
			"DISTRICT_NAME" : "阿拉善右旗",
			"SPELLING" : "Alashanyouqi"
		},
		{
			"ID" : "152923",
			"PARENT_ID" : "152900",
			"DISTRICT_NAME" : "额济纳旗",
			"SPELLING" : "Ejinaqi"
		},
		{
			"ID" : "210000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "辽宁省",
			"SPELLING" : "Liaoning"
		},
		{
			"ID" : "210100",
			"PARENT_ID" : "210000",
			"DISTRICT_NAME" : "沈阳市",
			"SPELLING" : "Shenyang"
		},
		{
			"ID" : "210102",
			"PARENT_ID" : "210100",
			"DISTRICT_NAME" : "和平区",
			"SPELLING" : "Heping"
		},
		{
			"ID" : "210103",
			"PARENT_ID" : "210100",
			"DISTRICT_NAME" : "沈河区",
			"SPELLING" : "Shenhe"
		},
		{
			"ID" : "210104",
			"PARENT_ID" : "210100",
			"DISTRICT_NAME" : "大东区",
			"SPELLING" : "Dadong"
		},
		{
			"ID" : "210105",
			"PARENT_ID" : "210100",
			"DISTRICT_NAME" : "皇姑区",
			"SPELLING" : "Huanggu"
		},
		{
			"ID" : "210106",
			"PARENT_ID" : "210100",
			"DISTRICT_NAME" : "铁西区",
			"SPELLING" : "Tiexi"
		},
		{
			"ID" : "210111",
			"PARENT_ID" : "210100",
			"DISTRICT_NAME" : "苏家屯区",
			"SPELLING" : "Sujiatun"
		},
		{
			"ID" : "210112",
			"PARENT_ID" : "210100",
			"DISTRICT_NAME" : "浑南区",
			"SPELLING" : "Hunnan"
		},
		{
			"ID" : "210113",
			"PARENT_ID" : "210100",
			"DISTRICT_NAME" : "沈北新区",
			"SPELLING" : "Shenbeixinqu"
		},
		{
			"ID" : "210114",
			"PARENT_ID" : "210100",
			"DISTRICT_NAME" : "于洪区",
			"SPELLING" : "Yuhong"
		},
		{
			"ID" : "210122",
			"PARENT_ID" : "210100",
			"DISTRICT_NAME" : "辽中县",
			"SPELLING" : "Liaozhong"
		},
		{
			"ID" : "210123",
			"PARENT_ID" : "210100",
			"DISTRICT_NAME" : "康平县",
			"SPELLING" : "Kangping"
		},
		{
			"ID" : "210124",
			"PARENT_ID" : "210100",
			"DISTRICT_NAME" : "法库县",
			"SPELLING" : "Faku"
		},
		{
			"ID" : "210181",
			"PARENT_ID" : "210100",
			"DISTRICT_NAME" : "新民市",
			"SPELLING" : "Xinmin"
		},
		{
			"ID" : "210200",
			"PARENT_ID" : "210000",
			"DISTRICT_NAME" : "大连市",
			"SPELLING" : "Dalian"
		},
		{
			"ID" : "210202",
			"PARENT_ID" : "210200",
			"DISTRICT_NAME" : "中山区",
			"SPELLING" : "Zhongshan"
		},
		{
			"ID" : "210203",
			"PARENT_ID" : "210200",
			"DISTRICT_NAME" : "西岗区",
			"SPELLING" : "Xigang"
		},
		{
			"ID" : "210204",
			"PARENT_ID" : "210200",
			"DISTRICT_NAME" : "沙河口区",
			"SPELLING" : "Shahekou"
		},
		{
			"ID" : "210211",
			"PARENT_ID" : "210200",
			"DISTRICT_NAME" : "甘井子区",
			"SPELLING" : "Ganjingzi"
		},
		{
			"ID" : "210212",
			"PARENT_ID" : "210200",
			"DISTRICT_NAME" : "旅顺口区",
			"SPELLING" : "Lvshunkou"
		},
		{
			"ID" : "210213",
			"PARENT_ID" : "210200",
			"DISTRICT_NAME" : "金州区",
			"SPELLING" : "Jinzhou"
		},
		{
			"ID" : "210224",
			"PARENT_ID" : "210200",
			"DISTRICT_NAME" : "长海县",
			"SPELLING" : "Changhai"
		},
		{
			"ID" : "210281",
			"PARENT_ID" : "210200",
			"DISTRICT_NAME" : "瓦房店市",
			"SPELLING" : "Wafangdian"
		},
		{
			"ID" : "210282",
			"PARENT_ID" : "210200",
			"DISTRICT_NAME" : "普兰店市",
			"SPELLING" : "Pulandian"
		},
		{
			"ID" : "210283",
			"PARENT_ID" : "210200",
			"DISTRICT_NAME" : "庄河市",
			"SPELLING" : "Zhuanghe"
		},
		{
			"ID" : "210300",
			"PARENT_ID" : "210000",
			"DISTRICT_NAME" : "鞍山市",
			"SPELLING" : "Anshan"
		},
		{
			"ID" : "210302",
			"PARENT_ID" : "210300",
			"DISTRICT_NAME" : "铁东区",
			"SPELLING" : "Tiedong"
		},
		{
			"ID" : "210303",
			"PARENT_ID" : "210300",
			"DISTRICT_NAME" : "铁西区",
			"SPELLING" : "Tiexi"
		},
		{
			"ID" : "210304",
			"PARENT_ID" : "210300",
			"DISTRICT_NAME" : "立山区",
			"SPELLING" : "Lishan"
		},
		{
			"ID" : "210311",
			"PARENT_ID" : "210300",
			"DISTRICT_NAME" : "千山区",
			"SPELLING" : "Qianshan"
		},
		{
			"ID" : "210321",
			"PARENT_ID" : "210300",
			"DISTRICT_NAME" : "台安县",
			"SPELLING" : "Taian"
		},
		{
			"ID" : "210323",
			"PARENT_ID" : "210300",
			"DISTRICT_NAME" : "岫岩满族自治县",
			"SPELLING" : "Xiuyan"
		},
		{
			"ID" : "210381",
			"PARENT_ID" : "210300",
			"DISTRICT_NAME" : "海城市",
			"SPELLING" : "Haicheng"
		},
		{
			"ID" : "210400",
			"PARENT_ID" : "210000",
			"DISTRICT_NAME" : "抚顺市",
			"SPELLING" : "Fushun"
		},
		{
			"ID" : "210402",
			"PARENT_ID" : "210400",
			"DISTRICT_NAME" : "新抚区",
			"SPELLING" : "Xinfu"
		},
		{
			"ID" : "210403",
			"PARENT_ID" : "210400",
			"DISTRICT_NAME" : "东洲区",
			"SPELLING" : "Dongzhou"
		},
		{
			"ID" : "210404",
			"PARENT_ID" : "210400",
			"DISTRICT_NAME" : "望花区",
			"SPELLING" : "Wanghua"
		},
		{
			"ID" : "210411",
			"PARENT_ID" : "210400",
			"DISTRICT_NAME" : "顺城区",
			"SPELLING" : "Shuncheng"
		},
		{
			"ID" : "210421",
			"PARENT_ID" : "210400",
			"DISTRICT_NAME" : "抚顺县",
			"SPELLING" : "Fushun"
		},
		{
			"ID" : "210422",
			"PARENT_ID" : "210400",
			"DISTRICT_NAME" : "新宾满族自治县",
			"SPELLING" : "Xinbin"
		},
		{
			"ID" : "210423",
			"PARENT_ID" : "210400",
			"DISTRICT_NAME" : "清原满族自治县",
			"SPELLING" : "Qingyuan"
		},
		{
			"ID" : "210500",
			"PARENT_ID" : "210000",
			"DISTRICT_NAME" : "本溪市",
			"SPELLING" : "Benxi"
		},
		{
			"ID" : "210502",
			"PARENT_ID" : "210500",
			"DISTRICT_NAME" : "平山区",
			"SPELLING" : "Pingshan"
		},
		{
			"ID" : "210503",
			"PARENT_ID" : "210500",
			"DISTRICT_NAME" : "溪湖区",
			"SPELLING" : "Xihu"
		},
		{
			"ID" : "210504",
			"PARENT_ID" : "210500",
			"DISTRICT_NAME" : "明山区",
			"SPELLING" : "Mingshan"
		},
		{
			"ID" : "210505",
			"PARENT_ID" : "210500",
			"DISTRICT_NAME" : "南芬区",
			"SPELLING" : "Nanfen"
		},
		{
			"ID" : "210521",
			"PARENT_ID" : "210500",
			"DISTRICT_NAME" : "本溪满族自治县",
			"SPELLING" : "Benxi"
		},
		{
			"ID" : "210522",
			"PARENT_ID" : "210500",
			"DISTRICT_NAME" : "桓仁满族自治县",
			"SPELLING" : "Huanren"
		},
		{
			"ID" : "210600",
			"PARENT_ID" : "210000",
			"DISTRICT_NAME" : "丹东市",
			"SPELLING" : "Dandong"
		},
		{
			"ID" : "210602",
			"PARENT_ID" : "210600",
			"DISTRICT_NAME" : "元宝区",
			"SPELLING" : "Yuanbao"
		},
		{
			"ID" : "210603",
			"PARENT_ID" : "210600",
			"DISTRICT_NAME" : "振兴区",
			"SPELLING" : "Zhenxing"
		},
		{
			"ID" : "210604",
			"PARENT_ID" : "210600",
			"DISTRICT_NAME" : "振安区",
			"SPELLING" : "Zhenan"
		},
		{
			"ID" : "210624",
			"PARENT_ID" : "210600",
			"DISTRICT_NAME" : "宽甸满族自治县",
			"SPELLING" : "Kuandian"
		},
		{
			"ID" : "210681",
			"PARENT_ID" : "210600",
			"DISTRICT_NAME" : "东港市",
			"SPELLING" : "Donggang"
		},
		{
			"ID" : "210682",
			"PARENT_ID" : "210600",
			"DISTRICT_NAME" : "凤城市",
			"SPELLING" : "Fengcheng"
		},
		{
			"ID" : "210700",
			"PARENT_ID" : "210000",
			"DISTRICT_NAME" : "锦州市",
			"SPELLING" : "Jinzhou"
		},
		{
			"ID" : "210702",
			"PARENT_ID" : "210700",
			"DISTRICT_NAME" : "古塔区",
			"SPELLING" : "Guta"
		},
		{
			"ID" : "210703",
			"PARENT_ID" : "210700",
			"DISTRICT_NAME" : "凌河区",
			"SPELLING" : "Linghe"
		},
		{
			"ID" : "210711",
			"PARENT_ID" : "210700",
			"DISTRICT_NAME" : "太和区",
			"SPELLING" : "Taihe"
		},
		{
			"ID" : "210726",
			"PARENT_ID" : "210700",
			"DISTRICT_NAME" : "黑山县",
			"SPELLING" : "Heishan"
		},
		{
			"ID" : "210727",
			"PARENT_ID" : "210700",
			"DISTRICT_NAME" : "义县",
			"SPELLING" : "Yixian"
		},
		{
			"ID" : "210781",
			"PARENT_ID" : "210700",
			"DISTRICT_NAME" : "凌海市",
			"SPELLING" : "Linghai"
		},
		{
			"ID" : "210782",
			"PARENT_ID" : "210700",
			"DISTRICT_NAME" : "北镇市",
			"SPELLING" : "Beizhen"
		},
		{
			"ID" : "210800",
			"PARENT_ID" : "210000",
			"DISTRICT_NAME" : "营口市",
			"SPELLING" : "Yingkou"
		},
		{
			"ID" : "210802",
			"PARENT_ID" : "210800",
			"DISTRICT_NAME" : "站前区",
			"SPELLING" : "Zhanqian"
		},
		{
			"ID" : "210803",
			"PARENT_ID" : "210800",
			"DISTRICT_NAME" : "西市区",
			"SPELLING" : "Xishi"
		},
		{
			"ID" : "210804",
			"PARENT_ID" : "210800",
			"DISTRICT_NAME" : "鲅鱼圈区",
			"SPELLING" : "Bayuquan"
		},
		{
			"ID" : "210811",
			"PARENT_ID" : "210800",
			"DISTRICT_NAME" : "老边区",
			"SPELLING" : "Laobian"
		},
		{
			"ID" : "210881",
			"PARENT_ID" : "210800",
			"DISTRICT_NAME" : "盖州市",
			"SPELLING" : "Gaizhou"
		},
		{
			"ID" : "210882",
			"PARENT_ID" : "210800",
			"DISTRICT_NAME" : "大石桥市",
			"SPELLING" : "Dashiqiao"
		},
		{
			"ID" : "210900",
			"PARENT_ID" : "210000",
			"DISTRICT_NAME" : "阜新市",
			"SPELLING" : "Fuxin"
		},
		{
			"ID" : "210902",
			"PARENT_ID" : "210900",
			"DISTRICT_NAME" : "海州区",
			"SPELLING" : "Haizhou"
		},
		{
			"ID" : "210903",
			"PARENT_ID" : "210900",
			"DISTRICT_NAME" : "新邱区",
			"SPELLING" : "Xinqiu"
		},
		{
			"ID" : "210904",
			"PARENT_ID" : "210900",
			"DISTRICT_NAME" : "太平区",
			"SPELLING" : "Taiping"
		},
		{
			"ID" : "210905",
			"PARENT_ID" : "210900",
			"DISTRICT_NAME" : "清河门区",
			"SPELLING" : "Qinghemen"
		},
		{
			"ID" : "210911",
			"PARENT_ID" : "210900",
			"DISTRICT_NAME" : "细河区",
			"SPELLING" : "Xihe"
		},
		{
			"ID" : "210921",
			"PARENT_ID" : "210900",
			"DISTRICT_NAME" : "阜新蒙古族自治县",
			"SPELLING" : "Fuxin"
		},
		{
			"ID" : "210922",
			"PARENT_ID" : "210900",
			"DISTRICT_NAME" : "彰武县",
			"SPELLING" : "Zhangwu"
		},
		{
			"ID" : "211000",
			"PARENT_ID" : "210000",
			"DISTRICT_NAME" : "辽阳市",
			"SPELLING" : "Liaoyang"
		},
		{
			"ID" : "211002",
			"PARENT_ID" : "211000",
			"DISTRICT_NAME" : "白塔区",
			"SPELLING" : "Baita"
		},
		{
			"ID" : "211003",
			"PARENT_ID" : "211000",
			"DISTRICT_NAME" : "文圣区",
			"SPELLING" : "Wensheng"
		},
		{
			"ID" : "211004",
			"PARENT_ID" : "211000",
			"DISTRICT_NAME" : "宏伟区",
			"SPELLING" : "Hongwei"
		},
		{
			"ID" : "211005",
			"PARENT_ID" : "211000",
			"DISTRICT_NAME" : "弓长岭区",
			"SPELLING" : "Gongchangling"
		},
		{
			"ID" : "211011",
			"PARENT_ID" : "211000",
			"DISTRICT_NAME" : "太子河区",
			"SPELLING" : "Taizihe"
		},
		{
			"ID" : "211021",
			"PARENT_ID" : "211000",
			"DISTRICT_NAME" : "辽阳县",
			"SPELLING" : "Liaoyang"
		},
		{
			"ID" : "211081",
			"PARENT_ID" : "211000",
			"DISTRICT_NAME" : "灯塔市",
			"SPELLING" : "Dengta"
		},
		{
			"ID" : "211100",
			"PARENT_ID" : "210000",
			"DISTRICT_NAME" : "盘锦市",
			"SPELLING" : "Panjin"
		},
		{
			"ID" : "211102",
			"PARENT_ID" : "211100",
			"DISTRICT_NAME" : "双台子区",
			"SPELLING" : "Shuangtaizi"
		},
		{
			"ID" : "211103",
			"PARENT_ID" : "211100",
			"DISTRICT_NAME" : "兴隆台区",
			"SPELLING" : "Xinglongtai"
		},
		{
			"ID" : "211121",
			"PARENT_ID" : "211100",
			"DISTRICT_NAME" : "大洼县",
			"SPELLING" : "Dawa"
		},
		{
			"ID" : "211122",
			"PARENT_ID" : "211100",
			"DISTRICT_NAME" : "盘山县",
			"SPELLING" : "Panshan"
		},
		{
			"ID" : "211200",
			"PARENT_ID" : "210000",
			"DISTRICT_NAME" : "铁岭市",
			"SPELLING" : "Tieling"
		},
		{
			"ID" : "211202",
			"PARENT_ID" : "211200",
			"DISTRICT_NAME" : "银州区",
			"SPELLING" : "Yinzhou"
		},
		{
			"ID" : "211204",
			"PARENT_ID" : "211200",
			"DISTRICT_NAME" : "清河区",
			"SPELLING" : "Qinghe"
		},
		{
			"ID" : "211221",
			"PARENT_ID" : "211200",
			"DISTRICT_NAME" : "铁岭县",
			"SPELLING" : "Tieling"
		},
		{
			"ID" : "211223",
			"PARENT_ID" : "211200",
			"DISTRICT_NAME" : "西丰县",
			"SPELLING" : "Xifeng"
		},
		{
			"ID" : "211224",
			"PARENT_ID" : "211200",
			"DISTRICT_NAME" : "昌图县",
			"SPELLING" : "Changtu"
		},
		{
			"ID" : "211281",
			"PARENT_ID" : "211200",
			"DISTRICT_NAME" : "调兵山市",
			"SPELLING" : "Diaobingshan"
		},
		{
			"ID" : "211282",
			"PARENT_ID" : "211200",
			"DISTRICT_NAME" : "开原市",
			"SPELLING" : "Kaiyuan"
		},
		{
			"ID" : "211300",
			"PARENT_ID" : "210000",
			"DISTRICT_NAME" : "朝阳市",
			"SPELLING" : "Chaoyang"
		},
		{
			"ID" : "211302",
			"PARENT_ID" : "211300",
			"DISTRICT_NAME" : "双塔区",
			"SPELLING" : "Shuangta"
		},
		{
			"ID" : "211303",
			"PARENT_ID" : "211300",
			"DISTRICT_NAME" : "龙城区",
			"SPELLING" : "Longcheng"
		},
		{
			"ID" : "211321",
			"PARENT_ID" : "211300",
			"DISTRICT_NAME" : "朝阳县",
			"SPELLING" : "Chaoyang"
		},
		{
			"ID" : "211322",
			"PARENT_ID" : "211300",
			"DISTRICT_NAME" : "建平县",
			"SPELLING" : "Jianping"
		},
		{
			"ID" : "211324",
			"PARENT_ID" : "211300",
			"DISTRICT_NAME" : "喀喇沁左翼蒙古族自治县",
			"SPELLING" : "Kalaqinzuoyi"
		},
		{
			"ID" : "211381",
			"PARENT_ID" : "211300",
			"DISTRICT_NAME" : "北票市",
			"SPELLING" : "Beipiao"
		},
		{
			"ID" : "211382",
			"PARENT_ID" : "211300",
			"DISTRICT_NAME" : "凌源市",
			"SPELLING" : "Lingyuan"
		},
		{
			"ID" : "211400",
			"PARENT_ID" : "210000",
			"DISTRICT_NAME" : "葫芦岛市",
			"SPELLING" : "Huludao"
		},
		{
			"ID" : "211402",
			"PARENT_ID" : "211400",
			"DISTRICT_NAME" : "连山区",
			"SPELLING" : "Lianshan"
		},
		{
			"ID" : "211403",
			"PARENT_ID" : "211400",
			"DISTRICT_NAME" : "龙港区",
			"SPELLING" : "Longgang"
		},
		{
			"ID" : "211404",
			"PARENT_ID" : "211400",
			"DISTRICT_NAME" : "南票区",
			"SPELLING" : "Nanpiao"
		},
		{
			"ID" : "211421",
			"PARENT_ID" : "211400",
			"DISTRICT_NAME" : "绥中县",
			"SPELLING" : "Suizhong"
		},
		{
			"ID" : "211422",
			"PARENT_ID" : "211400",
			"DISTRICT_NAME" : "建昌县",
			"SPELLING" : "Jianchang"
		},
		{
			"ID" : "211481",
			"PARENT_ID" : "211400",
			"DISTRICT_NAME" : "兴城市",
			"SPELLING" : "Xingcheng"
		},
		{
			"ID" : "211500",
			"PARENT_ID" : "210000",
			"DISTRICT_NAME" : "金普新区",
			"SPELLING" : "Jinpuxinqu"
		},
		{
			"ID" : "211501",
			"PARENT_ID" : "211500",
			"DISTRICT_NAME" : "金州新区",
			"SPELLING" : "Jinzhouxinqu"
		},
		{
			"ID" : "211502",
			"PARENT_ID" : "211500",
			"DISTRICT_NAME" : "普湾新区",
			"SPELLING" : "Puwanxinqu"
		},
		{
			"ID" : "211503",
			"PARENT_ID" : "211500",
			"DISTRICT_NAME" : "保税区",
			"SPELLING" : "Baoshuiqu"
		},
		{
			"ID" : "220000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "吉林省",
			"SPELLING" : "Jilin"
		},
		{
			"ID" : "220100",
			"PARENT_ID" : "220000",
			"DISTRICT_NAME" : "长春市",
			"SPELLING" : "Changchun"
		},
		{
			"ID" : "220102",
			"PARENT_ID" : "220100",
			"DISTRICT_NAME" : "南关区",
			"SPELLING" : "Nanguan"
		},
		{
			"ID" : "220103",
			"PARENT_ID" : "220100",
			"DISTRICT_NAME" : "宽城区",
			"SPELLING" : "Kuancheng"
		},
		{
			"ID" : "220104",
			"PARENT_ID" : "220100",
			"DISTRICT_NAME" : "朝阳区",
			"SPELLING" : "Chaoyang"
		},
		{
			"ID" : "220105",
			"PARENT_ID" : "220100",
			"DISTRICT_NAME" : "二道区",
			"SPELLING" : "Erdao"
		},
		{
			"ID" : "220106",
			"PARENT_ID" : "220100",
			"DISTRICT_NAME" : "绿园区",
			"SPELLING" : "Lvyuan"
		},
		{
			"ID" : "220112",
			"PARENT_ID" : "220100",
			"DISTRICT_NAME" : "双阳区",
			"SPELLING" : "Shuangyang"
		},
		{
			"ID" : "220113",
			"PARENT_ID" : "220100",
			"DISTRICT_NAME" : "九台区",
			"SPELLING" : "Jiutai"
		},
		{
			"ID" : "220122",
			"PARENT_ID" : "220100",
			"DISTRICT_NAME" : "农安县",
			"SPELLING" : "Nongan"
		},
		{
			"ID" : "220182",
			"PARENT_ID" : "220100",
			"DISTRICT_NAME" : "榆树市",
			"SPELLING" : "Yushu"
		},
		{
			"ID" : "220183",
			"PARENT_ID" : "220100",
			"DISTRICT_NAME" : "德惠市",
			"SPELLING" : "Dehui"
		},
		{
			"ID" : "220200",
			"PARENT_ID" : "220000",
			"DISTRICT_NAME" : "吉林市",
			"SPELLING" : "Jilin"
		},
		{
			"ID" : "220202",
			"PARENT_ID" : "220200",
			"DISTRICT_NAME" : "昌邑区",
			"SPELLING" : "Changyi"
		},
		{
			"ID" : "220203",
			"PARENT_ID" : "220200",
			"DISTRICT_NAME" : "龙潭区",
			"SPELLING" : "Longtan"
		},
		{
			"ID" : "220204",
			"PARENT_ID" : "220200",
			"DISTRICT_NAME" : "船营区",
			"SPELLING" : "Chuanying"
		},
		{
			"ID" : "220211",
			"PARENT_ID" : "220200",
			"DISTRICT_NAME" : "丰满区",
			"SPELLING" : "Fengman"
		},
		{
			"ID" : "220221",
			"PARENT_ID" : "220200",
			"DISTRICT_NAME" : "永吉县",
			"SPELLING" : "Yongji"
		},
		{
			"ID" : "220281",
			"PARENT_ID" : "220200",
			"DISTRICT_NAME" : "蛟河市",
			"SPELLING" : "Jiaohe"
		},
		{
			"ID" : "220282",
			"PARENT_ID" : "220200",
			"DISTRICT_NAME" : "桦甸市",
			"SPELLING" : "Huadian"
		},
		{
			"ID" : "220283",
			"PARENT_ID" : "220200",
			"DISTRICT_NAME" : "舒兰市",
			"SPELLING" : "Shulan"
		},
		{
			"ID" : "220284",
			"PARENT_ID" : "220200",
			"DISTRICT_NAME" : "磐石市",
			"SPELLING" : "Panshi"
		},
		{
			"ID" : "220300",
			"PARENT_ID" : "220000",
			"DISTRICT_NAME" : "四平市",
			"SPELLING" : "Siping"
		},
		{
			"ID" : "220302",
			"PARENT_ID" : "220300",
			"DISTRICT_NAME" : "铁西区",
			"SPELLING" : "Tiexi"
		},
		{
			"ID" : "220303",
			"PARENT_ID" : "220300",
			"DISTRICT_NAME" : "铁东区",
			"SPELLING" : "Tiedong"
		},
		{
			"ID" : "220322",
			"PARENT_ID" : "220300",
			"DISTRICT_NAME" : "梨树县",
			"SPELLING" : "Lishu"
		},
		{
			"ID" : "220323",
			"PARENT_ID" : "220300",
			"DISTRICT_NAME" : "伊通满族自治县",
			"SPELLING" : "Yitong"
		},
		{
			"ID" : "220381",
			"PARENT_ID" : "220300",
			"DISTRICT_NAME" : "公主岭市",
			"SPELLING" : "Gongzhuling"
		},
		{
			"ID" : "220382",
			"PARENT_ID" : "220300",
			"DISTRICT_NAME" : "双辽市",
			"SPELLING" : "Shuangliao"
		},
		{
			"ID" : "220400",
			"PARENT_ID" : "220000",
			"DISTRICT_NAME" : "辽源市",
			"SPELLING" : "Liaoyuan"
		},
		{
			"ID" : "220402",
			"PARENT_ID" : "220400",
			"DISTRICT_NAME" : "龙山区",
			"SPELLING" : "Longshan"
		},
		{
			"ID" : "220403",
			"PARENT_ID" : "220400",
			"DISTRICT_NAME" : "西安区",
			"SPELLING" : "Xian"
		},
		{
			"ID" : "220421",
			"PARENT_ID" : "220400",
			"DISTRICT_NAME" : "东丰县",
			"SPELLING" : "Dongfeng"
		},
		{
			"ID" : "220422",
			"PARENT_ID" : "220400",
			"DISTRICT_NAME" : "东辽县",
			"SPELLING" : "Dongliao"
		},
		{
			"ID" : "220500",
			"PARENT_ID" : "220000",
			"DISTRICT_NAME" : "通化市",
			"SPELLING" : "Tonghua"
		},
		{
			"ID" : "220502",
			"PARENT_ID" : "220500",
			"DISTRICT_NAME" : "东昌区",
			"SPELLING" : "Dongchang"
		},
		{
			"ID" : "220503",
			"PARENT_ID" : "220500",
			"DISTRICT_NAME" : "二道江区",
			"SPELLING" : "Erdaojiang"
		},
		{
			"ID" : "220521",
			"PARENT_ID" : "220500",
			"DISTRICT_NAME" : "通化县",
			"SPELLING" : "Tonghua"
		},
		{
			"ID" : "220523",
			"PARENT_ID" : "220500",
			"DISTRICT_NAME" : "辉南县",
			"SPELLING" : "Huinan"
		},
		{
			"ID" : "220524",
			"PARENT_ID" : "220500",
			"DISTRICT_NAME" : "柳河县",
			"SPELLING" : "Liuhe"
		},
		{
			"ID" : "220581",
			"PARENT_ID" : "220500",
			"DISTRICT_NAME" : "梅河口市",
			"SPELLING" : "Meihekou"
		},
		{
			"ID" : "220582",
			"PARENT_ID" : "220500",
			"DISTRICT_NAME" : "集安市",
			"SPELLING" : "Jian"
		},
		{
			"ID" : "220600",
			"PARENT_ID" : "220000",
			"DISTRICT_NAME" : "白山市",
			"SPELLING" : "Baishan"
		},
		{
			"ID" : "220602",
			"PARENT_ID" : "220600",
			"DISTRICT_NAME" : "浑江区",
			"SPELLING" : "Hunjiang"
		},
		{
			"ID" : "220605",
			"PARENT_ID" : "220600",
			"DISTRICT_NAME" : "江源区",
			"SPELLING" : "Jiangyuan"
		},
		{
			"ID" : "220621",
			"PARENT_ID" : "220600",
			"DISTRICT_NAME" : "抚松县",
			"SPELLING" : "Fusong"
		},
		{
			"ID" : "220622",
			"PARENT_ID" : "220600",
			"DISTRICT_NAME" : "靖宇县",
			"SPELLING" : "Jingyu"
		},
		{
			"ID" : "220623",
			"PARENT_ID" : "220600",
			"DISTRICT_NAME" : "长白朝鲜族自治县",
			"SPELLING" : "Changbai"
		},
		{
			"ID" : "220681",
			"PARENT_ID" : "220600",
			"DISTRICT_NAME" : "临江市",
			"SPELLING" : "Linjiang"
		},
		{
			"ID" : "220700",
			"PARENT_ID" : "220000",
			"DISTRICT_NAME" : "松原市",
			"SPELLING" : "Songyuan"
		},
		{
			"ID" : "220702",
			"PARENT_ID" : "220700",
			"DISTRICT_NAME" : "宁江区",
			"SPELLING" : "Ningjiang"
		},
		{
			"ID" : "220721",
			"PARENT_ID" : "220700",
			"DISTRICT_NAME" : "前郭尔罗斯蒙古族自治县",
			"SPELLING" : "Qianguoerluosi"
		},
		{
			"ID" : "220722",
			"PARENT_ID" : "220700",
			"DISTRICT_NAME" : "长岭县",
			"SPELLING" : "Changling"
		},
		{
			"ID" : "220723",
			"PARENT_ID" : "220700",
			"DISTRICT_NAME" : "乾安县",
			"SPELLING" : "Qianan"
		},
		{
			"ID" : "220781",
			"PARENT_ID" : "220700",
			"DISTRICT_NAME" : "扶余市",
			"SPELLING" : "Fuyu"
		},
		{
			"ID" : "220800",
			"PARENT_ID" : "220000",
			"DISTRICT_NAME" : "白城市",
			"SPELLING" : "Baicheng"
		},
		{
			"ID" : "220802",
			"PARENT_ID" : "220800",
			"DISTRICT_NAME" : "洮北区",
			"SPELLING" : "Taobei"
		},
		{
			"ID" : "220821",
			"PARENT_ID" : "220800",
			"DISTRICT_NAME" : "镇赉县",
			"SPELLING" : "Zhenlai"
		},
		{
			"ID" : "220822",
			"PARENT_ID" : "220800",
			"DISTRICT_NAME" : "通榆县",
			"SPELLING" : "Tongyu"
		},
		{
			"ID" : "220881",
			"PARENT_ID" : "220800",
			"DISTRICT_NAME" : "洮南市",
			"SPELLING" : "Taonan"
		},
		{
			"ID" : "220882",
			"PARENT_ID" : "220800",
			"DISTRICT_NAME" : "大安市",
			"SPELLING" : "Daan"
		},
		{
			"ID" : "222400",
			"PARENT_ID" : "220000",
			"DISTRICT_NAME" : "延边朝鲜族自治州",
			"SPELLING" : "Yanbian"
		},
		{
			"ID" : "222401",
			"PARENT_ID" : "222400",
			"DISTRICT_NAME" : "延吉市",
			"SPELLING" : "Yanji"
		},
		{
			"ID" : "222402",
			"PARENT_ID" : "222400",
			"DISTRICT_NAME" : "图们市",
			"SPELLING" : "Tumen"
		},
		{
			"ID" : "222403",
			"PARENT_ID" : "222400",
			"DISTRICT_NAME" : "敦化市",
			"SPELLING" : "Dunhua"
		},
		{
			"ID" : "222404",
			"PARENT_ID" : "222400",
			"DISTRICT_NAME" : "珲春市",
			"SPELLING" : "Hunchun"
		},
		{
			"ID" : "222405",
			"PARENT_ID" : "222400",
			"DISTRICT_NAME" : "龙井市",
			"SPELLING" : "Longjing"
		},
		{
			"ID" : "222406",
			"PARENT_ID" : "222400",
			"DISTRICT_NAME" : "和龙市",
			"SPELLING" : "Helong"
		},
		{
			"ID" : "222424",
			"PARENT_ID" : "222400",
			"DISTRICT_NAME" : "汪清县",
			"SPELLING" : "Wangqing"
		},
		{
			"ID" : "222426",
			"PARENT_ID" : "222400",
			"DISTRICT_NAME" : "安图县",
			"SPELLING" : "Antu"
		},
		{
			"ID" : "230000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "黑龙江省",
			"SPELLING" : "Heilongjiang"
		},
		{
			"ID" : "230100",
			"PARENT_ID" : "230000",
			"DISTRICT_NAME" : "哈尔滨市",
			"SPELLING" : "Harbin"
		},
		{
			"ID" : "230102",
			"PARENT_ID" : "230100",
			"DISTRICT_NAME" : "道里区",
			"SPELLING" : "Daoli"
		},
		{
			"ID" : "230103",
			"PARENT_ID" : "230100",
			"DISTRICT_NAME" : "南岗区",
			"SPELLING" : "Nangang"
		},
		{
			"ID" : "230104",
			"PARENT_ID" : "230100",
			"DISTRICT_NAME" : "道外区",
			"SPELLING" : "Daowai"
		},
		{
			"ID" : "230108",
			"PARENT_ID" : "230100",
			"DISTRICT_NAME" : "平房区",
			"SPELLING" : "Pingfang"
		},
		{
			"ID" : "230109",
			"PARENT_ID" : "230100",
			"DISTRICT_NAME" : "松北区",
			"SPELLING" : "Songbei"
		},
		{
			"ID" : "230110",
			"PARENT_ID" : "230100",
			"DISTRICT_NAME" : "香坊区",
			"SPELLING" : "Xiangfang"
		},
		{
			"ID" : "230111",
			"PARENT_ID" : "230100",
			"DISTRICT_NAME" : "呼兰区",
			"SPELLING" : "Hulan"
		},
		{
			"ID" : "230112",
			"PARENT_ID" : "230100",
			"DISTRICT_NAME" : "阿城区",
			"SPELLING" : "Acheng"
		},
		{
			"ID" : "230113",
			"PARENT_ID" : "230100",
			"DISTRICT_NAME" : "双城区",
			"SPELLING" : "Shuangcheng"
		},
		{
			"ID" : "230123",
			"PARENT_ID" : "230100",
			"DISTRICT_NAME" : "依兰县",
			"SPELLING" : "Yilan"
		},
		{
			"ID" : "230124",
			"PARENT_ID" : "230100",
			"DISTRICT_NAME" : "方正县",
			"SPELLING" : "Fangzheng"
		},
		{
			"ID" : "230125",
			"PARENT_ID" : "230100",
			"DISTRICT_NAME" : "宾县",
			"SPELLING" : "Binxian"
		},
		{
			"ID" : "230126",
			"PARENT_ID" : "230100",
			"DISTRICT_NAME" : "巴彦县",
			"SPELLING" : "Bayan"
		},
		{
			"ID" : "230127",
			"PARENT_ID" : "230100",
			"DISTRICT_NAME" : "木兰县",
			"SPELLING" : "Mulan"
		},
		{
			"ID" : "230128",
			"PARENT_ID" : "230100",
			"DISTRICT_NAME" : "通河县",
			"SPELLING" : "Tonghe"
		},
		{
			"ID" : "230129",
			"PARENT_ID" : "230100",
			"DISTRICT_NAME" : "延寿县",
			"SPELLING" : "Yanshou"
		},
		{
			"ID" : "230183",
			"PARENT_ID" : "230100",
			"DISTRICT_NAME" : "尚志市",
			"SPELLING" : "Shangzhi"
		},
		{
			"ID" : "230184",
			"PARENT_ID" : "230100",
			"DISTRICT_NAME" : "五常市",
			"SPELLING" : "Wuchang"
		},
		{
			"ID" : "230200",
			"PARENT_ID" : "230000",
			"DISTRICT_NAME" : "齐齐哈尔市",
			"SPELLING" : "Qiqihar"
		},
		{
			"ID" : "230202",
			"PARENT_ID" : "230200",
			"DISTRICT_NAME" : "龙沙区",
			"SPELLING" : "Longsha"
		},
		{
			"ID" : "230203",
			"PARENT_ID" : "230200",
			"DISTRICT_NAME" : "建华区",
			"SPELLING" : "Jianhua"
		},
		{
			"ID" : "230204",
			"PARENT_ID" : "230200",
			"DISTRICT_NAME" : "铁锋区",
			"SPELLING" : "Tiefeng"
		},
		{
			"ID" : "230205",
			"PARENT_ID" : "230200",
			"DISTRICT_NAME" : "昂昂溪区",
			"SPELLING" : "Angangxi"
		},
		{
			"ID" : "230206",
			"PARENT_ID" : "230200",
			"DISTRICT_NAME" : "富拉尔基区",
			"SPELLING" : "Fulaerji"
		},
		{
			"ID" : "230207",
			"PARENT_ID" : "230200",
			"DISTRICT_NAME" : "碾子山区",
			"SPELLING" : "Nianzishan"
		},
		{
			"ID" : "230208",
			"PARENT_ID" : "230200",
			"DISTRICT_NAME" : "梅里斯达斡尔族区",
			"SPELLING" : "Meilisi"
		},
		{
			"ID" : "230221",
			"PARENT_ID" : "230200",
			"DISTRICT_NAME" : "龙江县",
			"SPELLING" : "Longjiang"
		},
		{
			"ID" : "230223",
			"PARENT_ID" : "230200",
			"DISTRICT_NAME" : "依安县",
			"SPELLING" : "Yian"
		},
		{
			"ID" : "230224",
			"PARENT_ID" : "230200",
			"DISTRICT_NAME" : "泰来县",
			"SPELLING" : "Tailai"
		},
		{
			"ID" : "230225",
			"PARENT_ID" : "230200",
			"DISTRICT_NAME" : "甘南县",
			"SPELLING" : "Gannan"
		},
		{
			"ID" : "230227",
			"PARENT_ID" : "230200",
			"DISTRICT_NAME" : "富裕县",
			"SPELLING" : "Fuyu"
		},
		{
			"ID" : "230229",
			"PARENT_ID" : "230200",
			"DISTRICT_NAME" : "克山县",
			"SPELLING" : "Keshan"
		},
		{
			"ID" : "230230",
			"PARENT_ID" : "230200",
			"DISTRICT_NAME" : "克东县",
			"SPELLING" : "Kedong"
		},
		{
			"ID" : "230231",
			"PARENT_ID" : "230200",
			"DISTRICT_NAME" : "拜泉县",
			"SPELLING" : "Baiquan"
		},
		{
			"ID" : "230281",
			"PARENT_ID" : "230200",
			"DISTRICT_NAME" : "讷河市",
			"SPELLING" : "Nehe"
		},
		{
			"ID" : "230300",
			"PARENT_ID" : "230000",
			"DISTRICT_NAME" : "鸡西市",
			"SPELLING" : "Jixi"
		},
		{
			"ID" : "230302",
			"PARENT_ID" : "230300",
			"DISTRICT_NAME" : "鸡冠区",
			"SPELLING" : "Jiguan"
		},
		{
			"ID" : "230303",
			"PARENT_ID" : "230300",
			"DISTRICT_NAME" : "恒山区",
			"SPELLING" : "Hengshan"
		},
		{
			"ID" : "230304",
			"PARENT_ID" : "230300",
			"DISTRICT_NAME" : "滴道区",
			"SPELLING" : "Didao"
		},
		{
			"ID" : "230305",
			"PARENT_ID" : "230300",
			"DISTRICT_NAME" : "梨树区",
			"SPELLING" : "Lishu"
		},
		{
			"ID" : "230306",
			"PARENT_ID" : "230300",
			"DISTRICT_NAME" : "城子河区",
			"SPELLING" : "Chengzihe"
		},
		{
			"ID" : "230307",
			"PARENT_ID" : "230300",
			"DISTRICT_NAME" : "麻山区",
			"SPELLING" : "Mashan"
		},
		{
			"ID" : "230321",
			"PARENT_ID" : "230300",
			"DISTRICT_NAME" : "鸡东县",
			"SPELLING" : "Jidong"
		},
		{
			"ID" : "230381",
			"PARENT_ID" : "230300",
			"DISTRICT_NAME" : "虎林市",
			"SPELLING" : "Hulin"
		},
		{
			"ID" : "230382",
			"PARENT_ID" : "230300",
			"DISTRICT_NAME" : "密山市",
			"SPELLING" : "Mishan"
		},
		{
			"ID" : "230400",
			"PARENT_ID" : "230000",
			"DISTRICT_NAME" : "鹤岗市",
			"SPELLING" : "Hegang"
		},
		{
			"ID" : "230402",
			"PARENT_ID" : "230400",
			"DISTRICT_NAME" : "向阳区",
			"SPELLING" : "Xiangyang"
		},
		{
			"ID" : "230403",
			"PARENT_ID" : "230400",
			"DISTRICT_NAME" : "工农区",
			"SPELLING" : "Gongnong"
		},
		{
			"ID" : "230404",
			"PARENT_ID" : "230400",
			"DISTRICT_NAME" : "南山区",
			"SPELLING" : "Nanshan"
		},
		{
			"ID" : "230405",
			"PARENT_ID" : "230400",
			"DISTRICT_NAME" : "兴安区",
			"SPELLING" : "Xingan"
		},
		{
			"ID" : "230406",
			"PARENT_ID" : "230400",
			"DISTRICT_NAME" : "东山区",
			"SPELLING" : "Dongshan"
		},
		{
			"ID" : "230407",
			"PARENT_ID" : "230400",
			"DISTRICT_NAME" : "兴山区",
			"SPELLING" : "Xingshan"
		},
		{
			"ID" : "230421",
			"PARENT_ID" : "230400",
			"DISTRICT_NAME" : "萝北县",
			"SPELLING" : "Luobei"
		},
		{
			"ID" : "230422",
			"PARENT_ID" : "230400",
			"DISTRICT_NAME" : "绥滨县",
			"SPELLING" : "Suibin"
		},
		{
			"ID" : "230500",
			"PARENT_ID" : "230000",
			"DISTRICT_NAME" : "双鸭山市",
			"SPELLING" : "Shuangyashan"
		},
		{
			"ID" : "230502",
			"PARENT_ID" : "230500",
			"DISTRICT_NAME" : "尖山区",
			"SPELLING" : "Jianshan"
		},
		{
			"ID" : "230503",
			"PARENT_ID" : "230500",
			"DISTRICT_NAME" : "岭东区",
			"SPELLING" : "Lingdong"
		},
		{
			"ID" : "230505",
			"PARENT_ID" : "230500",
			"DISTRICT_NAME" : "四方台区",
			"SPELLING" : "Sifangtai"
		},
		{
			"ID" : "230506",
			"PARENT_ID" : "230500",
			"DISTRICT_NAME" : "宝山区",
			"SPELLING" : "Baoshan"
		},
		{
			"ID" : "230521",
			"PARENT_ID" : "230500",
			"DISTRICT_NAME" : "集贤县",
			"SPELLING" : "Jixian"
		},
		{
			"ID" : "230522",
			"PARENT_ID" : "230500",
			"DISTRICT_NAME" : "友谊县",
			"SPELLING" : "Youyi"
		},
		{
			"ID" : "230523",
			"PARENT_ID" : "230500",
			"DISTRICT_NAME" : "宝清县",
			"SPELLING" : "Baoqing"
		},
		{
			"ID" : "230524",
			"PARENT_ID" : "230500",
			"DISTRICT_NAME" : "饶河县",
			"SPELLING" : "Raohe"
		},
		{
			"ID" : "230600",
			"PARENT_ID" : "230000",
			"DISTRICT_NAME" : "大庆市",
			"SPELLING" : "Daqing"
		},
		{
			"ID" : "230602",
			"PARENT_ID" : "230600",
			"DISTRICT_NAME" : "萨尔图区",
			"SPELLING" : "Saertu"
		},
		{
			"ID" : "230603",
			"PARENT_ID" : "230600",
			"DISTRICT_NAME" : "龙凤区",
			"SPELLING" : "Longfeng"
		},
		{
			"ID" : "230604",
			"PARENT_ID" : "230600",
			"DISTRICT_NAME" : "让胡路区",
			"SPELLING" : "Ranghulu"
		},
		{
			"ID" : "230605",
			"PARENT_ID" : "230600",
			"DISTRICT_NAME" : "红岗区",
			"SPELLING" : "Honggang"
		},
		{
			"ID" : "230606",
			"PARENT_ID" : "230600",
			"DISTRICT_NAME" : "大同区",
			"SPELLING" : "Datong"
		},
		{
			"ID" : "230621",
			"PARENT_ID" : "230600",
			"DISTRICT_NAME" : "肇州县",
			"SPELLING" : "Zhaozhou"
		},
		{
			"ID" : "230622",
			"PARENT_ID" : "230600",
			"DISTRICT_NAME" : "肇源县",
			"SPELLING" : "Zhaoyuan"
		},
		{
			"ID" : "230623",
			"PARENT_ID" : "230600",
			"DISTRICT_NAME" : "林甸县",
			"SPELLING" : "Lindian"
		},
		{
			"ID" : "230624",
			"PARENT_ID" : "230600",
			"DISTRICT_NAME" : "杜尔伯特蒙古族自治县",
			"SPELLING" : "Duerbote"
		},
		{
			"ID" : "230700",
			"PARENT_ID" : "230000",
			"DISTRICT_NAME" : "伊春市",
			"SPELLING" : "Yichun"
		},
		{
			"ID" : "230702",
			"PARENT_ID" : "230700",
			"DISTRICT_NAME" : "伊春区",
			"SPELLING" : "Yichun"
		},
		{
			"ID" : "230703",
			"PARENT_ID" : "230700",
			"DISTRICT_NAME" : "南岔区",
			"SPELLING" : "Nancha"
		},
		{
			"ID" : "230704",
			"PARENT_ID" : "230700",
			"DISTRICT_NAME" : "友好区",
			"SPELLING" : "Youhao"
		},
		{
			"ID" : "230705",
			"PARENT_ID" : "230700",
			"DISTRICT_NAME" : "西林区",
			"SPELLING" : "Xilin"
		},
		{
			"ID" : "230706",
			"PARENT_ID" : "230700",
			"DISTRICT_NAME" : "翠峦区",
			"SPELLING" : "Cuiluan"
		},
		{
			"ID" : "230707",
			"PARENT_ID" : "230700",
			"DISTRICT_NAME" : "新青区",
			"SPELLING" : "Xinqing"
		},
		{
			"ID" : "230708",
			"PARENT_ID" : "230700",
			"DISTRICT_NAME" : "美溪区",
			"SPELLING" : "Meixi"
		},
		{
			"ID" : "230709",
			"PARENT_ID" : "230700",
			"DISTRICT_NAME" : "金山屯区",
			"SPELLING" : "Jinshantun"
		},
		{
			"ID" : "230710",
			"PARENT_ID" : "230700",
			"DISTRICT_NAME" : "五营区",
			"SPELLING" : "Wuying"
		},
		{
			"ID" : "230711",
			"PARENT_ID" : "230700",
			"DISTRICT_NAME" : "乌马河区",
			"SPELLING" : "Wumahe"
		},
		{
			"ID" : "230712",
			"PARENT_ID" : "230700",
			"DISTRICT_NAME" : "汤旺河区",
			"SPELLING" : "Tangwanghe"
		},
		{
			"ID" : "230713",
			"PARENT_ID" : "230700",
			"DISTRICT_NAME" : "带岭区",
			"SPELLING" : "Dailing"
		},
		{
			"ID" : "230714",
			"PARENT_ID" : "230700",
			"DISTRICT_NAME" : "乌伊岭区",
			"SPELLING" : "Wuyiling"
		},
		{
			"ID" : "230715",
			"PARENT_ID" : "230700",
			"DISTRICT_NAME" : "红星区",
			"SPELLING" : "Hongxing"
		},
		{
			"ID" : "230716",
			"PARENT_ID" : "230700",
			"DISTRICT_NAME" : "上甘岭区",
			"SPELLING" : "Shangganling"
		},
		{
			"ID" : "230722",
			"PARENT_ID" : "230700",
			"DISTRICT_NAME" : "嘉荫县",
			"SPELLING" : "Jiayin"
		},
		{
			"ID" : "230781",
			"PARENT_ID" : "230700",
			"DISTRICT_NAME" : "铁力市",
			"SPELLING" : "Tieli"
		},
		{
			"ID" : "230800",
			"PARENT_ID" : "230000",
			"DISTRICT_NAME" : "佳木斯市",
			"SPELLING" : "Jiamusi"
		},
		{
			"ID" : "230803",
			"PARENT_ID" : "230800",
			"DISTRICT_NAME" : "向阳区",
			"SPELLING" : "Xiangyang"
		},
		{
			"ID" : "230804",
			"PARENT_ID" : "230800",
			"DISTRICT_NAME" : "前进区",
			"SPELLING" : "Qianjin"
		},
		{
			"ID" : "230805",
			"PARENT_ID" : "230800",
			"DISTRICT_NAME" : "东风区",
			"SPELLING" : "Dongfeng"
		},
		{
			"ID" : "230811",
			"PARENT_ID" : "230800",
			"DISTRICT_NAME" : "郊区",
			"SPELLING" : "Jiaoqu"
		},
		{
			"ID" : "230822",
			"PARENT_ID" : "230800",
			"DISTRICT_NAME" : "桦南县",
			"SPELLING" : "Huanan"
		},
		{
			"ID" : "230826",
			"PARENT_ID" : "230800",
			"DISTRICT_NAME" : "桦川县",
			"SPELLING" : "Huachuan"
		},
		{
			"ID" : "230828",
			"PARENT_ID" : "230800",
			"DISTRICT_NAME" : "汤原县",
			"SPELLING" : "Tangyuan"
		},
		{
			"ID" : "230833",
			"PARENT_ID" : "230800",
			"DISTRICT_NAME" : "抚远县",
			"SPELLING" : "Fuyuan"
		},
		{
			"ID" : "230881",
			"PARENT_ID" : "230800",
			"DISTRICT_NAME" : "同江市",
			"SPELLING" : "Tongjiang"
		},
		{
			"ID" : "230882",
			"PARENT_ID" : "230800",
			"DISTRICT_NAME" : "富锦市",
			"SPELLING" : "Fujin"
		},
		{
			"ID" : "230900",
			"PARENT_ID" : "230000",
			"DISTRICT_NAME" : "七台河市",
			"SPELLING" : "Qitaihe"
		},
		{
			"ID" : "230902",
			"PARENT_ID" : "230900",
			"DISTRICT_NAME" : "新兴区",
			"SPELLING" : "Xinxing"
		},
		{
			"ID" : "230903",
			"PARENT_ID" : "230900",
			"DISTRICT_NAME" : "桃山区",
			"SPELLING" : "Taoshan"
		},
		{
			"ID" : "230904",
			"PARENT_ID" : "230900",
			"DISTRICT_NAME" : "茄子河区",
			"SPELLING" : "Qiezihe"
		},
		{
			"ID" : "230921",
			"PARENT_ID" : "230900",
			"DISTRICT_NAME" : "勃利县",
			"SPELLING" : "Boli"
		},
		{
			"ID" : "231000",
			"PARENT_ID" : "230000",
			"DISTRICT_NAME" : "牡丹江市",
			"SPELLING" : "Mudanjiang"
		},
		{
			"ID" : "231002",
			"PARENT_ID" : "231000",
			"DISTRICT_NAME" : "东安区",
			"SPELLING" : "Dongan"
		},
		{
			"ID" : "231003",
			"PARENT_ID" : "231000",
			"DISTRICT_NAME" : "阳明区",
			"SPELLING" : "Yangming"
		},
		{
			"ID" : "231004",
			"PARENT_ID" : "231000",
			"DISTRICT_NAME" : "爱民区",
			"SPELLING" : "Aimin"
		},
		{
			"ID" : "231005",
			"PARENT_ID" : "231000",
			"DISTRICT_NAME" : "西安区",
			"SPELLING" : "Xian"
		},
		{
			"ID" : "231024",
			"PARENT_ID" : "231000",
			"DISTRICT_NAME" : "东宁县",
			"SPELLING" : "Dongning"
		},
		{
			"ID" : "231025",
			"PARENT_ID" : "231000",
			"DISTRICT_NAME" : "林口县",
			"SPELLING" : "Linkou"
		},
		{
			"ID" : "231081",
			"PARENT_ID" : "231000",
			"DISTRICT_NAME" : "绥芬河市",
			"SPELLING" : "Suifenhe"
		},
		{
			"ID" : "231083",
			"PARENT_ID" : "231000",
			"DISTRICT_NAME" : "海林市",
			"SPELLING" : "Hailin"
		},
		{
			"ID" : "231084",
			"PARENT_ID" : "231000",
			"DISTRICT_NAME" : "宁安市",
			"SPELLING" : "Ningan"
		},
		{
			"ID" : "231085",
			"PARENT_ID" : "231000",
			"DISTRICT_NAME" : "穆棱市",
			"SPELLING" : "Muling"
		},
		{
			"ID" : "231100",
			"PARENT_ID" : "230000",
			"DISTRICT_NAME" : "黑河市",
			"SPELLING" : "Heihe"
		},
		{
			"ID" : "231102",
			"PARENT_ID" : "231100",
			"DISTRICT_NAME" : "爱辉区",
			"SPELLING" : "Aihui"
		},
		{
			"ID" : "231121",
			"PARENT_ID" : "231100",
			"DISTRICT_NAME" : "嫩江县",
			"SPELLING" : "Nenjiang"
		},
		{
			"ID" : "231123",
			"PARENT_ID" : "231100",
			"DISTRICT_NAME" : "逊克县",
			"SPELLING" : "Xunke"
		},
		{
			"ID" : "231124",
			"PARENT_ID" : "231100",
			"DISTRICT_NAME" : "孙吴县",
			"SPELLING" : "Sunwu"
		},
		{
			"ID" : "231181",
			"PARENT_ID" : "231100",
			"DISTRICT_NAME" : "北安市",
			"SPELLING" : "Beian"
		},
		{
			"ID" : "231182",
			"PARENT_ID" : "231100",
			"DISTRICT_NAME" : "五大连池市",
			"SPELLING" : "Wudalianchi"
		},
		{
			"ID" : "231200",
			"PARENT_ID" : "230000",
			"DISTRICT_NAME" : "绥化市",
			"SPELLING" : "Suihua"
		},
		{
			"ID" : "231202",
			"PARENT_ID" : "231200",
			"DISTRICT_NAME" : "北林区",
			"SPELLING" : "Beilin"
		},
		{
			"ID" : "231221",
			"PARENT_ID" : "231200",
			"DISTRICT_NAME" : "望奎县",
			"SPELLING" : "Wangkui"
		},
		{
			"ID" : "231222",
			"PARENT_ID" : "231200",
			"DISTRICT_NAME" : "兰西县",
			"SPELLING" : "Lanxi"
		},
		{
			"ID" : "231223",
			"PARENT_ID" : "231200",
			"DISTRICT_NAME" : "青冈县",
			"SPELLING" : "Qinggang"
		},
		{
			"ID" : "231224",
			"PARENT_ID" : "231200",
			"DISTRICT_NAME" : "庆安县",
			"SPELLING" : "Qingan"
		},
		{
			"ID" : "231225",
			"PARENT_ID" : "231200",
			"DISTRICT_NAME" : "明水县",
			"SPELLING" : "Mingshui"
		},
		{
			"ID" : "231226",
			"PARENT_ID" : "231200",
			"DISTRICT_NAME" : "绥棱县",
			"SPELLING" : "Suileng"
		},
		{
			"ID" : "231281",
			"PARENT_ID" : "231200",
			"DISTRICT_NAME" : "安达市",
			"SPELLING" : "Anda"
		},
		{
			"ID" : "231282",
			"PARENT_ID" : "231200",
			"DISTRICT_NAME" : "肇东市",
			"SPELLING" : "Zhaodong"
		},
		{
			"ID" : "231283",
			"PARENT_ID" : "231200",
			"DISTRICT_NAME" : "海伦市",
			"SPELLING" : "Hailun"
		},
		{
			"ID" : "232700",
			"PARENT_ID" : "230000",
			"DISTRICT_NAME" : "大兴安岭地区",
			"SPELLING" : "DaXingAnLing"
		},
		{
			"ID" : "232701",
			"PARENT_ID" : "232700",
			"DISTRICT_NAME" : "加格达奇区",
			"SPELLING" : "Jiagedaqi"
		},
		{
			"ID" : "232702",
			"PARENT_ID" : "232700",
			"DISTRICT_NAME" : "新林区",
			"SPELLING" : "Xinlin"
		},
		{
			"ID" : "232703",
			"PARENT_ID" : "232700",
			"DISTRICT_NAME" : "松岭区",
			"SPELLING" : "Songling"
		},
		{
			"ID" : "232704",
			"PARENT_ID" : "232700",
			"DISTRICT_NAME" : "呼中区",
			"SPELLING" : "Huzhong"
		},
		{
			"ID" : "232721",
			"PARENT_ID" : "232700",
			"DISTRICT_NAME" : "呼玛县",
			"SPELLING" : "Huma"
		},
		{
			"ID" : "232722",
			"PARENT_ID" : "232700",
			"DISTRICT_NAME" : "塔河县",
			"SPELLING" : "Tahe"
		},
		{
			"ID" : "232723",
			"PARENT_ID" : "232700",
			"DISTRICT_NAME" : "漠河县",
			"SPELLING" : "Mohe"
		},
		{
			"ID" : "310000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "上海",
			"SPELLING" : "Shanghai"
		},
		{
			"ID" : "310100",
			"PARENT_ID" : "310000",
			"DISTRICT_NAME" : "上海市",
			"SPELLING" : "Shanghai"
		},
		{
			"ID" : "310101",
			"PARENT_ID" : "310100",
			"DISTRICT_NAME" : "黄浦区",
			"SPELLING" : "Huangpu"
		},
		{
			"ID" : "310104",
			"PARENT_ID" : "310100",
			"DISTRICT_NAME" : "徐汇区",
			"SPELLING" : "Xuhui"
		},
		{
			"ID" : "310105",
			"PARENT_ID" : "310100",
			"DISTRICT_NAME" : "长宁区",
			"SPELLING" : "Changning"
		},
		{
			"ID" : "310106",
			"PARENT_ID" : "310100",
			"DISTRICT_NAME" : "静安区",
			"SPELLING" : "Jingan"
		},
		{
			"ID" : "310107",
			"PARENT_ID" : "310100",
			"DISTRICT_NAME" : "普陀区",
			"SPELLING" : "Putuo"
		},
		{
			"ID" : "310108",
			"PARENT_ID" : "310100",
			"DISTRICT_NAME" : "闸北区",
			"SPELLING" : "Zhabei"
		},
		{
			"ID" : "310109",
			"PARENT_ID" : "310100",
			"DISTRICT_NAME" : "虹口区",
			"SPELLING" : "Hongkou"
		},
		{
			"ID" : "310110",
			"PARENT_ID" : "310100",
			"DISTRICT_NAME" : "杨浦区",
			"SPELLING" : "Yangpu"
		},
		{
			"ID" : "310112",
			"PARENT_ID" : "310100",
			"DISTRICT_NAME" : "闵行区",
			"SPELLING" : "Minhang"
		},
		{
			"ID" : "310113",
			"PARENT_ID" : "310100",
			"DISTRICT_NAME" : "宝山区",
			"SPELLING" : "Baoshan"
		},
		{
			"ID" : "310114",
			"PARENT_ID" : "310100",
			"DISTRICT_NAME" : "嘉定区",
			"SPELLING" : "Jiading"
		},
		{
			"ID" : "310115",
			"PARENT_ID" : "310100",
			"DISTRICT_NAME" : "浦东新区",
			"SPELLING" : "Pudong"
		},
		{
			"ID" : "310116",
			"PARENT_ID" : "310100",
			"DISTRICT_NAME" : "金山区",
			"SPELLING" : "Jinshan"
		},
		{
			"ID" : "310117",
			"PARENT_ID" : "310100",
			"DISTRICT_NAME" : "松江区",
			"SPELLING" : "Songjiang"
		},
		{
			"ID" : "310118",
			"PARENT_ID" : "310100",
			"DISTRICT_NAME" : "青浦区",
			"SPELLING" : "Qingpu"
		},
		{
			"ID" : "310120",
			"PARENT_ID" : "310100",
			"DISTRICT_NAME" : "奉贤区",
			"SPELLING" : "Fengxian"
		},
		{
			"ID" : "310230",
			"PARENT_ID" : "310100",
			"DISTRICT_NAME" : "崇明县",
			"SPELLING" : "Chongming"
		},
		{
			"ID" : "320000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "江苏省",
			"SPELLING" : "Jiangsu"
		},
		{
			"ID" : "320100",
			"PARENT_ID" : "320000",
			"DISTRICT_NAME" : "南京市",
			"SPELLING" : "Nanjing"
		},
		{
			"ID" : "320102",
			"PARENT_ID" : "320100",
			"DISTRICT_NAME" : "玄武区",
			"SPELLING" : "Xuanwu"
		},
		{
			"ID" : "320104",
			"PARENT_ID" : "320100",
			"DISTRICT_NAME" : "秦淮区",
			"SPELLING" : "Qinhuai"
		},
		{
			"ID" : "320105",
			"PARENT_ID" : "320100",
			"DISTRICT_NAME" : "建邺区",
			"SPELLING" : "Jianye"
		},
		{
			"ID" : "320106",
			"PARENT_ID" : "320100",
			"DISTRICT_NAME" : "鼓楼区",
			"SPELLING" : "Gulou"
		},
		{
			"ID" : "320111",
			"PARENT_ID" : "320100",
			"DISTRICT_NAME" : "浦口区",
			"SPELLING" : "Pukou"
		},
		{
			"ID" : "320113",
			"PARENT_ID" : "320100",
			"DISTRICT_NAME" : "栖霞区",
			"SPELLING" : "Qixia"
		},
		{
			"ID" : "320114",
			"PARENT_ID" : "320100",
			"DISTRICT_NAME" : "雨花台区",
			"SPELLING" : "Yuhuatai"
		},
		{
			"ID" : "320115",
			"PARENT_ID" : "320100",
			"DISTRICT_NAME" : "江宁区",
			"SPELLING" : "Jiangning"
		},
		{
			"ID" : "320116",
			"PARENT_ID" : "320100",
			"DISTRICT_NAME" : "六合区",
			"SPELLING" : "Luhe"
		},
		{
			"ID" : "320117",
			"PARENT_ID" : "320100",
			"DISTRICT_NAME" : "溧水区",
			"SPELLING" : "Lishui"
		},
		{
			"ID" : "320118",
			"PARENT_ID" : "320100",
			"DISTRICT_NAME" : "高淳区",
			"SPELLING" : "Gaochun"
		},
		{
			"ID" : "320200",
			"PARENT_ID" : "320000",
			"DISTRICT_NAME" : "无锡市",
			"SPELLING" : "Wuxi"
		},
		{
			"ID" : "320202",
			"PARENT_ID" : "320200",
			"DISTRICT_NAME" : "崇安区",
			"SPELLING" : "Chongan"
		},
		{
			"ID" : "320203",
			"PARENT_ID" : "320200",
			"DISTRICT_NAME" : "南长区",
			"SPELLING" : "Nanchang"
		},
		{
			"ID" : "320204",
			"PARENT_ID" : "320200",
			"DISTRICT_NAME" : "北塘区",
			"SPELLING" : "Beitang"
		},
		{
			"ID" : "320205",
			"PARENT_ID" : "320200",
			"DISTRICT_NAME" : "锡山区",
			"SPELLING" : "Xishan"
		},
		{
			"ID" : "320206",
			"PARENT_ID" : "320200",
			"DISTRICT_NAME" : "惠山区",
			"SPELLING" : "Huishan"
		},
		{
			"ID" : "320211",
			"PARENT_ID" : "320200",
			"DISTRICT_NAME" : "滨湖区",
			"SPELLING" : "Binhu"
		},
		{
			"ID" : "320281",
			"PARENT_ID" : "320200",
			"DISTRICT_NAME" : "江阴市",
			"SPELLING" : "Jiangyin"
		},
		{
			"ID" : "320282",
			"PARENT_ID" : "320200",
			"DISTRICT_NAME" : "宜兴市",
			"SPELLING" : "Yixing"
		},
		{
			"ID" : "320300",
			"PARENT_ID" : "320000",
			"DISTRICT_NAME" : "徐州市",
			"SPELLING" : "Xuzhou"
		},
		{
			"ID" : "320302",
			"PARENT_ID" : "320300",
			"DISTRICT_NAME" : "鼓楼区",
			"SPELLING" : "Gulou"
		},
		{
			"ID" : "320303",
			"PARENT_ID" : "320300",
			"DISTRICT_NAME" : "云龙区",
			"SPELLING" : "Yunlong"
		},
		{
			"ID" : "320305",
			"PARENT_ID" : "320300",
			"DISTRICT_NAME" : "贾汪区",
			"SPELLING" : "Jiawang"
		},
		{
			"ID" : "320311",
			"PARENT_ID" : "320300",
			"DISTRICT_NAME" : "泉山区",
			"SPELLING" : "Quanshan"
		},
		{
			"ID" : "320312",
			"PARENT_ID" : "320300",
			"DISTRICT_NAME" : "铜山区",
			"SPELLING" : "Tongshan"
		},
		{
			"ID" : "320321",
			"PARENT_ID" : "320300",
			"DISTRICT_NAME" : "丰县",
			"SPELLING" : "Fengxian"
		},
		{
			"ID" : "320322",
			"PARENT_ID" : "320300",
			"DISTRICT_NAME" : "沛县",
			"SPELLING" : "Peixian"
		},
		{
			"ID" : "320324",
			"PARENT_ID" : "320300",
			"DISTRICT_NAME" : "睢宁县",
			"SPELLING" : "Suining"
		},
		{
			"ID" : "320381",
			"PARENT_ID" : "320300",
			"DISTRICT_NAME" : "新沂市",
			"SPELLING" : "Xinyi"
		},
		{
			"ID" : "320382",
			"PARENT_ID" : "320300",
			"DISTRICT_NAME" : "邳州市",
			"SPELLING" : "Pizhou"
		},
		{
			"ID" : "320400",
			"PARENT_ID" : "320000",
			"DISTRICT_NAME" : "常州市",
			"SPELLING" : "Changzhou"
		},
		{
			"ID" : "320402",
			"PARENT_ID" : "320400",
			"DISTRICT_NAME" : "天宁区",
			"SPELLING" : "Tianning"
		},
		{
			"ID" : "320404",
			"PARENT_ID" : "320400",
			"DISTRICT_NAME" : "钟楼区",
			"SPELLING" : "Zhonglou"
		},
		{
			"ID" : "320405",
			"PARENT_ID" : "320400",
			"DISTRICT_NAME" : "戚墅堰区",
			"SPELLING" : "Qishuyan"
		},
		{
			"ID" : "320411",
			"PARENT_ID" : "320400",
			"DISTRICT_NAME" : "新北区",
			"SPELLING" : "Xinbei"
		},
		{
			"ID" : "320412",
			"PARENT_ID" : "320400",
			"DISTRICT_NAME" : "武进区",
			"SPELLING" : "Wujin"
		},
		{
			"ID" : "320481",
			"PARENT_ID" : "320400",
			"DISTRICT_NAME" : "溧阳市",
			"SPELLING" : "Liyang"
		},
		{
			"ID" : "320482",
			"PARENT_ID" : "320400",
			"DISTRICT_NAME" : "金坛市",
			"SPELLING" : "Jintan"
		},
		{
			"ID" : "320500",
			"PARENT_ID" : "320000",
			"DISTRICT_NAME" : "苏州市",
			"SPELLING" : "Suzhou"
		},
		{
			"ID" : "320505",
			"PARENT_ID" : "320500",
			"DISTRICT_NAME" : "虎丘区",
			"SPELLING" : "Huqiu"
		},
		{
			"ID" : "320506",
			"PARENT_ID" : "320500",
			"DISTRICT_NAME" : "吴中区",
			"SPELLING" : "Wuzhong"
		},
		{
			"ID" : "320507",
			"PARENT_ID" : "320500",
			"DISTRICT_NAME" : "相城区",
			"SPELLING" : "Xiangcheng"
		},
		{
			"ID" : "320508",
			"PARENT_ID" : "320500",
			"DISTRICT_NAME" : "姑苏区",
			"SPELLING" : "Gusu"
		},
		{
			"ID" : "320509",
			"PARENT_ID" : "320500",
			"DISTRICT_NAME" : "吴江区",
			"SPELLING" : "Wujiang"
		},
		{
			"ID" : "320581",
			"PARENT_ID" : "320500",
			"DISTRICT_NAME" : "常熟市",
			"SPELLING" : "Changshu"
		},
		{
			"ID" : "320582",
			"PARENT_ID" : "320500",
			"DISTRICT_NAME" : "张家港市",
			"SPELLING" : "Zhangjiagang"
		},
		{
			"ID" : "320583",
			"PARENT_ID" : "320500",
			"DISTRICT_NAME" : "昆山市",
			"SPELLING" : "Kunshan"
		},
		{
			"ID" : "320585",
			"PARENT_ID" : "320500",
			"DISTRICT_NAME" : "太仓市",
			"SPELLING" : "Taicang"
		},
		{
			"ID" : "320600",
			"PARENT_ID" : "320000",
			"DISTRICT_NAME" : "南通市",
			"SPELLING" : "Nantong"
		},
		{
			"ID" : "320602",
			"PARENT_ID" : "320600",
			"DISTRICT_NAME" : "崇川区",
			"SPELLING" : "Chongchuan"
		},
		{
			"ID" : "320611",
			"PARENT_ID" : "320600",
			"DISTRICT_NAME" : "港闸区",
			"SPELLING" : "Gangzha"
		},
		{
			"ID" : "320612",
			"PARENT_ID" : "320600",
			"DISTRICT_NAME" : "通州区",
			"SPELLING" : "Tongzhou"
		},
		{
			"ID" : "320621",
			"PARENT_ID" : "320600",
			"DISTRICT_NAME" : "海安县",
			"SPELLING" : "Haian"
		},
		{
			"ID" : "320623",
			"PARENT_ID" : "320600",
			"DISTRICT_NAME" : "如东县",
			"SPELLING" : "Rudong"
		},
		{
			"ID" : "320681",
			"PARENT_ID" : "320600",
			"DISTRICT_NAME" : "启东市",
			"SPELLING" : "Qidong"
		},
		{
			"ID" : "320682",
			"PARENT_ID" : "320600",
			"DISTRICT_NAME" : "如皋市",
			"SPELLING" : "Rugao"
		},
		{
			"ID" : "320684",
			"PARENT_ID" : "320600",
			"DISTRICT_NAME" : "海门市",
			"SPELLING" : "Haimen"
		},
		{
			"ID" : "320700",
			"PARENT_ID" : "320000",
			"DISTRICT_NAME" : "连云港市",
			"SPELLING" : "Lianyungang"
		},
		{
			"ID" : "320703",
			"PARENT_ID" : "320700",
			"DISTRICT_NAME" : "连云区",
			"SPELLING" : "Lianyun"
		},
		{
			"ID" : "320706",
			"PARENT_ID" : "320700",
			"DISTRICT_NAME" : "海州区",
			"SPELLING" : "Haizhou"
		},
		{
			"ID" : "320707",
			"PARENT_ID" : "320700",
			"DISTRICT_NAME" : "赣榆区",
			"SPELLING" : "Ganyu"
		},
		{
			"ID" : "320722",
			"PARENT_ID" : "320700",
			"DISTRICT_NAME" : "东海县",
			"SPELLING" : "Donghai"
		},
		{
			"ID" : "320723",
			"PARENT_ID" : "320700",
			"DISTRICT_NAME" : "灌云县",
			"SPELLING" : "Guanyun"
		},
		{
			"ID" : "320724",
			"PARENT_ID" : "320700",
			"DISTRICT_NAME" : "灌南县",
			"SPELLING" : "Guannan"
		},
		{
			"ID" : "320800",
			"PARENT_ID" : "320000",
			"DISTRICT_NAME" : "淮安市",
			"SPELLING" : "Huaian"
		},
		{
			"ID" : "320802",
			"PARENT_ID" : "320800",
			"DISTRICT_NAME" : "清河区",
			"SPELLING" : "Qinghe"
		},
		{
			"ID" : "320803",
			"PARENT_ID" : "320800",
			"DISTRICT_NAME" : "淮安区",
			"SPELLING" : "Huaian"
		},
		{
			"ID" : "320804",
			"PARENT_ID" : "320800",
			"DISTRICT_NAME" : "淮阴区",
			"SPELLING" : "Huaiyin"
		},
		{
			"ID" : "320811",
			"PARENT_ID" : "320800",
			"DISTRICT_NAME" : "清浦区",
			"SPELLING" : "Qingpu"
		},
		{
			"ID" : "320826",
			"PARENT_ID" : "320800",
			"DISTRICT_NAME" : "涟水县",
			"SPELLING" : "Lianshui"
		},
		{
			"ID" : "320829",
			"PARENT_ID" : "320800",
			"DISTRICT_NAME" : "洪泽县",
			"SPELLING" : "Hongze"
		},
		{
			"ID" : "320830",
			"PARENT_ID" : "320800",
			"DISTRICT_NAME" : "盱眙县",
			"SPELLING" : "Xuyi"
		},
		{
			"ID" : "320831",
			"PARENT_ID" : "320800",
			"DISTRICT_NAME" : "金湖县",
			"SPELLING" : "Jinhu"
		},
		{
			"ID" : "320900",
			"PARENT_ID" : "320000",
			"DISTRICT_NAME" : "盐城市",
			"SPELLING" : "Yancheng"
		},
		{
			"ID" : "320902",
			"PARENT_ID" : "320900",
			"DISTRICT_NAME" : "亭湖区",
			"SPELLING" : "Tinghu"
		},
		{
			"ID" : "320903",
			"PARENT_ID" : "320900",
			"DISTRICT_NAME" : "盐都区",
			"SPELLING" : "Yandu"
		},
		{
			"ID" : "320921",
			"PARENT_ID" : "320900",
			"DISTRICT_NAME" : "响水县",
			"SPELLING" : "Xiangshui"
		},
		{
			"ID" : "320922",
			"PARENT_ID" : "320900",
			"DISTRICT_NAME" : "滨海县",
			"SPELLING" : "Binhai"
		},
		{
			"ID" : "320923",
			"PARENT_ID" : "320900",
			"DISTRICT_NAME" : "阜宁县",
			"SPELLING" : "Funing"
		},
		{
			"ID" : "320924",
			"PARENT_ID" : "320900",
			"DISTRICT_NAME" : "射阳县",
			"SPELLING" : "Sheyang"
		},
		{
			"ID" : "320925",
			"PARENT_ID" : "320900",
			"DISTRICT_NAME" : "建湖县",
			"SPELLING" : "Jianhu"
		},
		{
			"ID" : "320981",
			"PARENT_ID" : "320900",
			"DISTRICT_NAME" : "东台市",
			"SPELLING" : "Dongtai"
		},
		{
			"ID" : "320982",
			"PARENT_ID" : "320900",
			"DISTRICT_NAME" : "大丰市",
			"SPELLING" : "Dafeng"
		},
		{
			"ID" : "321000",
			"PARENT_ID" : "320000",
			"DISTRICT_NAME" : "扬州市",
			"SPELLING" : "Yangzhou"
		},
		{
			"ID" : "321002",
			"PARENT_ID" : "321000",
			"DISTRICT_NAME" : "广陵区",
			"SPELLING" : "Guangling"
		},
		{
			"ID" : "321003",
			"PARENT_ID" : "321000",
			"DISTRICT_NAME" : "邗江区",
			"SPELLING" : "Hanjiang"
		},
		{
			"ID" : "321012",
			"PARENT_ID" : "321000",
			"DISTRICT_NAME" : "江都区",
			"SPELLING" : "Jiangdu"
		},
		{
			"ID" : "321023",
			"PARENT_ID" : "321000",
			"DISTRICT_NAME" : "宝应县",
			"SPELLING" : "Baoying"
		},
		{
			"ID" : "321081",
			"PARENT_ID" : "321000",
			"DISTRICT_NAME" : "仪征市",
			"SPELLING" : "Yizheng"
		},
		{
			"ID" : "321084",
			"PARENT_ID" : "321000",
			"DISTRICT_NAME" : "高邮市",
			"SPELLING" : "Gaoyou"
		},
		{
			"ID" : "321100",
			"PARENT_ID" : "320000",
			"DISTRICT_NAME" : "镇江市",
			"SPELLING" : "Zhenjiang"
		},
		{
			"ID" : "321102",
			"PARENT_ID" : "321100",
			"DISTRICT_NAME" : "京口区",
			"SPELLING" : "Jingkou"
		},
		{
			"ID" : "321111",
			"PARENT_ID" : "321100",
			"DISTRICT_NAME" : "润州区",
			"SPELLING" : "Runzhou"
		},
		{
			"ID" : "321112",
			"PARENT_ID" : "321100",
			"DISTRICT_NAME" : "丹徒区",
			"SPELLING" : "Dantu"
		},
		{
			"ID" : "321181",
			"PARENT_ID" : "321100",
			"DISTRICT_NAME" : "丹阳市",
			"SPELLING" : "Danyang"
		},
		{
			"ID" : "321182",
			"PARENT_ID" : "321100",
			"DISTRICT_NAME" : "扬中市",
			"SPELLING" : "Yangzhong"
		},
		{
			"ID" : "321183",
			"PARENT_ID" : "321100",
			"DISTRICT_NAME" : "句容市",
			"SPELLING" : "Jurong"
		},
		{
			"ID" : "321200",
			"PARENT_ID" : "320000",
			"DISTRICT_NAME" : "泰州市",
			"SPELLING" : "Taizhou"
		},
		{
			"ID" : "321202",
			"PARENT_ID" : "321200",
			"DISTRICT_NAME" : "海陵区",
			"SPELLING" : "Hailing"
		},
		{
			"ID" : "321203",
			"PARENT_ID" : "321200",
			"DISTRICT_NAME" : "高港区",
			"SPELLING" : "Gaogang"
		},
		{
			"ID" : "321204",
			"PARENT_ID" : "321200",
			"DISTRICT_NAME" : "姜堰区",
			"SPELLING" : "Jiangyan"
		},
		{
			"ID" : "321281",
			"PARENT_ID" : "321200",
			"DISTRICT_NAME" : "兴化市",
			"SPELLING" : "Xinghua"
		},
		{
			"ID" : "321282",
			"PARENT_ID" : "321200",
			"DISTRICT_NAME" : "靖江市",
			"SPELLING" : "Jingjiang"
		},
		{
			"ID" : "321283",
			"PARENT_ID" : "321200",
			"DISTRICT_NAME" : "泰兴市",
			"SPELLING" : "Taixing"
		},
		{
			"ID" : "321300",
			"PARENT_ID" : "320000",
			"DISTRICT_NAME" : "宿迁市",
			"SPELLING" : "Suqian"
		},
		{
			"ID" : "321302",
			"PARENT_ID" : "321300",
			"DISTRICT_NAME" : "宿城区",
			"SPELLING" : "Sucheng"
		},
		{
			"ID" : "321311",
			"PARENT_ID" : "321300",
			"DISTRICT_NAME" : "宿豫区",
			"SPELLING" : "Suyu"
		},
		{
			"ID" : "321322",
			"PARENT_ID" : "321300",
			"DISTRICT_NAME" : "沭阳县",
			"SPELLING" : "Shuyang"
		},
		{
			"ID" : "321323",
			"PARENT_ID" : "321300",
			"DISTRICT_NAME" : "泗阳县",
			"SPELLING" : "Siyang"
		},
		{
			"ID" : "321324",
			"PARENT_ID" : "321300",
			"DISTRICT_NAME" : "泗洪县",
			"SPELLING" : "Sihong"
		},
		{
			"ID" : "330000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "浙江省",
			"SPELLING" : "Zhejiang"
		},
		{
			"ID" : "330100",
			"PARENT_ID" : "330000",
			"DISTRICT_NAME" : "杭州市",
			"SPELLING" : "Hangzhou"
		},
		{
			"ID" : "330102",
			"PARENT_ID" : "330100",
			"DISTRICT_NAME" : "上城区",
			"SPELLING" : "Shangcheng"
		},
		{
			"ID" : "330103",
			"PARENT_ID" : "330100",
			"DISTRICT_NAME" : "下城区",
			"SPELLING" : "Xiacheng"
		},
		{
			"ID" : "330104",
			"PARENT_ID" : "330100",
			"DISTRICT_NAME" : "江干区",
			"SPELLING" : "Jianggan"
		},
		{
			"ID" : "330105",
			"PARENT_ID" : "330100",
			"DISTRICT_NAME" : "拱墅区",
			"SPELLING" : "Gongshu"
		},
		{
			"ID" : "330106",
			"PARENT_ID" : "330100",
			"DISTRICT_NAME" : "西湖区",
			"SPELLING" : "Xihu"
		},
		{
			"ID" : "330108",
			"PARENT_ID" : "330100",
			"DISTRICT_NAME" : "滨江区",
			"SPELLING" : "Binjiang"
		},
		{
			"ID" : "330109",
			"PARENT_ID" : "330100",
			"DISTRICT_NAME" : "萧山区",
			"SPELLING" : "Xiaoshan"
		},
		{
			"ID" : "330110",
			"PARENT_ID" : "330100",
			"DISTRICT_NAME" : "余杭区",
			"SPELLING" : "Yuhang"
		},
		{
			"ID" : "330122",
			"PARENT_ID" : "330100",
			"DISTRICT_NAME" : "桐庐县",
			"SPELLING" : "Tonglu"
		},
		{
			"ID" : "330127",
			"PARENT_ID" : "330100",
			"DISTRICT_NAME" : "淳安县",
			"SPELLING" : "Chunan"
		},
		{
			"ID" : "330182",
			"PARENT_ID" : "330100",
			"DISTRICT_NAME" : "建德市",
			"SPELLING" : "Jiande"
		},
		{
			"ID" : "330183",
			"PARENT_ID" : "330100",
			"DISTRICT_NAME" : "富阳区",
			"SPELLING" : "Fuyang"
		},
		{
			"ID" : "330185",
			"PARENT_ID" : "330100",
			"DISTRICT_NAME" : "临安市",
			"SPELLING" : "Linan"
		},
		{
			"ID" : "330200",
			"PARENT_ID" : "330000",
			"DISTRICT_NAME" : "宁波市",
			"SPELLING" : "Ningbo"
		},
		{
			"ID" : "330203",
			"PARENT_ID" : "330200",
			"DISTRICT_NAME" : "海曙区",
			"SPELLING" : "Haishu"
		},
		{
			"ID" : "330204",
			"PARENT_ID" : "330200",
			"DISTRICT_NAME" : "江东区",
			"SPELLING" : "Jiangdong"
		},
		{
			"ID" : "330205",
			"PARENT_ID" : "330200",
			"DISTRICT_NAME" : "江北区",
			"SPELLING" : "Jiangbei"
		},
		{
			"ID" : "330206",
			"PARENT_ID" : "330200",
			"DISTRICT_NAME" : "北仑区",
			"SPELLING" : "Beilun"
		},
		{
			"ID" : "330211",
			"PARENT_ID" : "330200",
			"DISTRICT_NAME" : "镇海区",
			"SPELLING" : "Zhenhai"
		},
		{
			"ID" : "330212",
			"PARENT_ID" : "330200",
			"DISTRICT_NAME" : "鄞州区",
			"SPELLING" : "Yinzhou"
		},
		{
			"ID" : "330225",
			"PARENT_ID" : "330200",
			"DISTRICT_NAME" : "象山县",
			"SPELLING" : "Xiangshan"
		},
		{
			"ID" : "330226",
			"PARENT_ID" : "330200",
			"DISTRICT_NAME" : "宁海县",
			"SPELLING" : "Ninghai"
		},
		{
			"ID" : "330281",
			"PARENT_ID" : "330200",
			"DISTRICT_NAME" : "余姚市",
			"SPELLING" : "Yuyao"
		},
		{
			"ID" : "330282",
			"PARENT_ID" : "330200",
			"DISTRICT_NAME" : "慈溪市",
			"SPELLING" : "Cixi"
		},
		{
			"ID" : "330283",
			"PARENT_ID" : "330200",
			"DISTRICT_NAME" : "奉化市",
			"SPELLING" : "Fenghua"
		},
		{
			"ID" : "330300",
			"PARENT_ID" : "330000",
			"DISTRICT_NAME" : "温州市",
			"SPELLING" : "Wenzhou"
		},
		{
			"ID" : "330302",
			"PARENT_ID" : "330300",
			"DISTRICT_NAME" : "鹿城区",
			"SPELLING" : "Lucheng"
		},
		{
			"ID" : "330303",
			"PARENT_ID" : "330300",
			"DISTRICT_NAME" : "龙湾区",
			"SPELLING" : "Longwan"
		},
		{
			"ID" : "330304",
			"PARENT_ID" : "330300",
			"DISTRICT_NAME" : "瓯海区",
			"SPELLING" : "Ouhai"
		},
		{
			"ID" : "330322",
			"PARENT_ID" : "330300",
			"DISTRICT_NAME" : "洞头县",
			"SPELLING" : "Dongtou"
		},
		{
			"ID" : "330324",
			"PARENT_ID" : "330300",
			"DISTRICT_NAME" : "永嘉县",
			"SPELLING" : "Yongjia"
		},
		{
			"ID" : "330326",
			"PARENT_ID" : "330300",
			"DISTRICT_NAME" : "平阳县",
			"SPELLING" : "Pingyang"
		},
		{
			"ID" : "330327",
			"PARENT_ID" : "330300",
			"DISTRICT_NAME" : "苍南县",
			"SPELLING" : "Cangnan"
		},
		{
			"ID" : "330328",
			"PARENT_ID" : "330300",
			"DISTRICT_NAME" : "文成县",
			"SPELLING" : "Wencheng"
		},
		{
			"ID" : "330329",
			"PARENT_ID" : "330300",
			"DISTRICT_NAME" : "泰顺县",
			"SPELLING" : "Taishun"
		},
		{
			"ID" : "330381",
			"PARENT_ID" : "330300",
			"DISTRICT_NAME" : "瑞安市",
			"SPELLING" : "Ruian"
		},
		{
			"ID" : "330382",
			"PARENT_ID" : "330300",
			"DISTRICT_NAME" : "乐清市",
			"SPELLING" : "Yueqing"
		},
		{
			"ID" : "330400",
			"PARENT_ID" : "330000",
			"DISTRICT_NAME" : "嘉兴市",
			"SPELLING" : "Jiaxing"
		},
		{
			"ID" : "330402",
			"PARENT_ID" : "330400",
			"DISTRICT_NAME" : "南湖区",
			"SPELLING" : "Nanhu"
		},
		{
			"ID" : "330411",
			"PARENT_ID" : "330400",
			"DISTRICT_NAME" : "秀洲区",
			"SPELLING" : "Xiuzhou"
		},
		{
			"ID" : "330421",
			"PARENT_ID" : "330400",
			"DISTRICT_NAME" : "嘉善县",
			"SPELLING" : "Jiashan"
		},
		{
			"ID" : "330424",
			"PARENT_ID" : "330400",
			"DISTRICT_NAME" : "海盐县",
			"SPELLING" : "Haiyan"
		},
		{
			"ID" : "330481",
			"PARENT_ID" : "330400",
			"DISTRICT_NAME" : "海宁市",
			"SPELLING" : "Haining"
		},
		{
			"ID" : "330482",
			"PARENT_ID" : "330400",
			"DISTRICT_NAME" : "平湖市",
			"SPELLING" : "Pinghu"
		},
		{
			"ID" : "330483",
			"PARENT_ID" : "330400",
			"DISTRICT_NAME" : "桐乡市",
			"SPELLING" : "Tongxiang"
		},
		{
			"ID" : "330500",
			"PARENT_ID" : "330000",
			"DISTRICT_NAME" : "湖州市",
			"SPELLING" : "Huzhou"
		},
		{
			"ID" : "330502",
			"PARENT_ID" : "330500",
			"DISTRICT_NAME" : "吴兴区",
			"SPELLING" : "Wuxing"
		},
		{
			"ID" : "330503",
			"PARENT_ID" : "330500",
			"DISTRICT_NAME" : "南浔区",
			"SPELLING" : "Nanxun"
		},
		{
			"ID" : "330521",
			"PARENT_ID" : "330500",
			"DISTRICT_NAME" : "德清县",
			"SPELLING" : "Deqing"
		},
		{
			"ID" : "330522",
			"PARENT_ID" : "330500",
			"DISTRICT_NAME" : "长兴县",
			"SPELLING" : "Changxing"
		},
		{
			"ID" : "330523",
			"PARENT_ID" : "330500",
			"DISTRICT_NAME" : "安吉县",
			"SPELLING" : "Anji"
		},
		{
			"ID" : "330600",
			"PARENT_ID" : "330000",
			"DISTRICT_NAME" : "绍兴市",
			"SPELLING" : "Shaoxing"
		},
		{
			"ID" : "330602",
			"PARENT_ID" : "330600",
			"DISTRICT_NAME" : "越城区",
			"SPELLING" : "Yuecheng"
		},
		{
			"ID" : "330603",
			"PARENT_ID" : "330600",
			"DISTRICT_NAME" : "柯桥区",
			"SPELLING" : "Keqiao "
		},
		{
			"ID" : "330604",
			"PARENT_ID" : "330600",
			"DISTRICT_NAME" : "上虞区",
			"SPELLING" : "Shangyu"
		},
		{
			"ID" : "330624",
			"PARENT_ID" : "330600",
			"DISTRICT_NAME" : "新昌县",
			"SPELLING" : "Xinchang"
		},
		{
			"ID" : "330681",
			"PARENT_ID" : "330600",
			"DISTRICT_NAME" : "诸暨市",
			"SPELLING" : "Zhuji"
		},
		{
			"ID" : "330683",
			"PARENT_ID" : "330600",
			"DISTRICT_NAME" : "嵊州市",
			"SPELLING" : "Shengzhou"
		},
		{
			"ID" : "330700",
			"PARENT_ID" : "330000",
			"DISTRICT_NAME" : "金华市",
			"SPELLING" : "Jinhua"
		},
		{
			"ID" : "330702",
			"PARENT_ID" : "330700",
			"DISTRICT_NAME" : "婺城区",
			"SPELLING" : "Wucheng"
		},
		{
			"ID" : "330703",
			"PARENT_ID" : "330700",
			"DISTRICT_NAME" : "金东区",
			"SPELLING" : "Jindong"
		},
		{
			"ID" : "330723",
			"PARENT_ID" : "330700",
			"DISTRICT_NAME" : "武义县",
			"SPELLING" : "Wuyi"
		},
		{
			"ID" : "330726",
			"PARENT_ID" : "330700",
			"DISTRICT_NAME" : "浦江县",
			"SPELLING" : "Pujiang"
		},
		{
			"ID" : "330727",
			"PARENT_ID" : "330700",
			"DISTRICT_NAME" : "磐安县",
			"SPELLING" : "Panan"
		},
		{
			"ID" : "330781",
			"PARENT_ID" : "330700",
			"DISTRICT_NAME" : "兰溪市",
			"SPELLING" : "Lanxi"
		},
		{
			"ID" : "330782",
			"PARENT_ID" : "330700",
			"DISTRICT_NAME" : "义乌市",
			"SPELLING" : "Yiwu"
		},
		{
			"ID" : "330783",
			"PARENT_ID" : "330700",
			"DISTRICT_NAME" : "东阳市",
			"SPELLING" : "Dongyang"
		},
		{
			"ID" : "330784",
			"PARENT_ID" : "330700",
			"DISTRICT_NAME" : "永康市",
			"SPELLING" : "Yongkang"
		},
		{
			"ID" : "330800",
			"PARENT_ID" : "330000",
			"DISTRICT_NAME" : "衢州市",
			"SPELLING" : "Quzhou"
		},
		{
			"ID" : "330802",
			"PARENT_ID" : "330800",
			"DISTRICT_NAME" : "柯城区",
			"SPELLING" : "Kecheng"
		},
		{
			"ID" : "330803",
			"PARENT_ID" : "330800",
			"DISTRICT_NAME" : "衢江区",
			"SPELLING" : "Qujiang"
		},
		{
			"ID" : "330822",
			"PARENT_ID" : "330800",
			"DISTRICT_NAME" : "常山县",
			"SPELLING" : "Changshan"
		},
		{
			"ID" : "330824",
			"PARENT_ID" : "330800",
			"DISTRICT_NAME" : "开化县",
			"SPELLING" : "Kaihua"
		},
		{
			"ID" : "330825",
			"PARENT_ID" : "330800",
			"DISTRICT_NAME" : "龙游县",
			"SPELLING" : "Longyou"
		},
		{
			"ID" : "330881",
			"PARENT_ID" : "330800",
			"DISTRICT_NAME" : "江山市",
			"SPELLING" : "Jiangshan"
		},
		{
			"ID" : "330900",
			"PARENT_ID" : "330000",
			"DISTRICT_NAME" : "舟山市",
			"SPELLING" : "Zhoushan"
		},
		{
			"ID" : "330902",
			"PARENT_ID" : "330900",
			"DISTRICT_NAME" : "定海区",
			"SPELLING" : "Dinghai"
		},
		{
			"ID" : "330903",
			"PARENT_ID" : "330900",
			"DISTRICT_NAME" : "普陀区",
			"SPELLING" : "Putuo"
		},
		{
			"ID" : "330921",
			"PARENT_ID" : "330900",
			"DISTRICT_NAME" : "岱山县",
			"SPELLING" : "Daishan"
		},
		{
			"ID" : "330922",
			"PARENT_ID" : "330900",
			"DISTRICT_NAME" : "嵊泗县",
			"SPELLING" : "Shengsi"
		},
		{
			"ID" : "331000",
			"PARENT_ID" : "330000",
			"DISTRICT_NAME" : "台州市",
			"SPELLING" : "Taizhou"
		},
		{
			"ID" : "331002",
			"PARENT_ID" : "331000",
			"DISTRICT_NAME" : "椒江区",
			"SPELLING" : "Jiaojiang"
		},
		{
			"ID" : "331003",
			"PARENT_ID" : "331000",
			"DISTRICT_NAME" : "黄岩区",
			"SPELLING" : "Huangyan"
		},
		{
			"ID" : "331004",
			"PARENT_ID" : "331000",
			"DISTRICT_NAME" : "路桥区",
			"SPELLING" : "Luqiao"
		},
		{
			"ID" : "331021",
			"PARENT_ID" : "331000",
			"DISTRICT_NAME" : "玉环县",
			"SPELLING" : "Yuhuan"
		},
		{
			"ID" : "331022",
			"PARENT_ID" : "331000",
			"DISTRICT_NAME" : "三门县",
			"SPELLING" : "Sanmen"
		},
		{
			"ID" : "331023",
			"PARENT_ID" : "331000",
			"DISTRICT_NAME" : "天台县",
			"SPELLING" : "Tiantai"
		},
		{
			"ID" : "331024",
			"PARENT_ID" : "331000",
			"DISTRICT_NAME" : "仙居县",
			"SPELLING" : "Xianju"
		},
		{
			"ID" : "331081",
			"PARENT_ID" : "331000",
			"DISTRICT_NAME" : "温岭市",
			"SPELLING" : "Wenling"
		},
		{
			"ID" : "331082",
			"PARENT_ID" : "331000",
			"DISTRICT_NAME" : "临海市",
			"SPELLING" : "Linhai"
		},
		{
			"ID" : "331100",
			"PARENT_ID" : "330000",
			"DISTRICT_NAME" : "丽水市",
			"SPELLING" : "Lishui"
		},
		{
			"ID" : "331102",
			"PARENT_ID" : "331100",
			"DISTRICT_NAME" : "莲都区",
			"SPELLING" : "Liandu"
		},
		{
			"ID" : "331121",
			"PARENT_ID" : "331100",
			"DISTRICT_NAME" : "青田县",
			"SPELLING" : "Qingtian"
		},
		{
			"ID" : "331122",
			"PARENT_ID" : "331100",
			"DISTRICT_NAME" : "缙云县",
			"SPELLING" : "Jinyun"
		},
		{
			"ID" : "331123",
			"PARENT_ID" : "331100",
			"DISTRICT_NAME" : "遂昌县",
			"SPELLING" : "Suichang"
		},
		{
			"ID" : "331124",
			"PARENT_ID" : "331100",
			"DISTRICT_NAME" : "松阳县",
			"SPELLING" : "Songyang"
		},
		{
			"ID" : "331125",
			"PARENT_ID" : "331100",
			"DISTRICT_NAME" : "云和县",
			"SPELLING" : "Yunhe"
		},
		{
			"ID" : "331126",
			"PARENT_ID" : "331100",
			"DISTRICT_NAME" : "庆元县",
			"SPELLING" : "Qingyuan"
		},
		{
			"ID" : "331127",
			"PARENT_ID" : "331100",
			"DISTRICT_NAME" : "景宁畲族自治县",
			"SPELLING" : "Jingning"
		},
		{
			"ID" : "331181",
			"PARENT_ID" : "331100",
			"DISTRICT_NAME" : "龙泉市",
			"SPELLING" : "Longquan"
		},
		{
			"ID" : "331200",
			"PARENT_ID" : "330000",
			"DISTRICT_NAME" : "舟山群岛新区",
			"SPELLING" : "Zhoushan"
		},
		{
			"ID" : "331201",
			"PARENT_ID" : "331200",
			"DISTRICT_NAME" : "金塘岛",
			"SPELLING" : "Jintang"
		},
		{
			"ID" : "331202",
			"PARENT_ID" : "331200",
			"DISTRICT_NAME" : "六横岛",
			"SPELLING" : "Liuheng"
		},
		{
			"ID" : "331203",
			"PARENT_ID" : "331200",
			"DISTRICT_NAME" : "衢山岛",
			"SPELLING" : "Qushan"
		},
		{
			"ID" : "331204",
			"PARENT_ID" : "331200",
			"DISTRICT_NAME" : "舟山本岛西北部",
			"SPELLING" : "Zhoushan"
		},
		{
			"ID" : "331205",
			"PARENT_ID" : "331200",
			"DISTRICT_NAME" : "岱山岛西南部",
			"SPELLING" : "Daishan"
		},
		{
			"ID" : "331206",
			"PARENT_ID" : "331200",
			"DISTRICT_NAME" : "泗礁岛",
			"SPELLING" : "Sijiao"
		},
		{
			"ID" : "331207",
			"PARENT_ID" : "331200",
			"DISTRICT_NAME" : "朱家尖岛",
			"SPELLING" : "Zhujiajian"
		},
		{
			"ID" : "331208",
			"PARENT_ID" : "331200",
			"DISTRICT_NAME" : "洋山岛",
			"SPELLING" : "Yangshan"
		},
		{
			"ID" : "331209",
			"PARENT_ID" : "331200",
			"DISTRICT_NAME" : "长涂岛",
			"SPELLING" : "Changtu"
		},
		{
			"ID" : "331210",
			"PARENT_ID" : "331200",
			"DISTRICT_NAME" : "虾峙岛",
			"SPELLING" : "Xiazhi"
		},
		{
			"ID" : "340000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "安徽省",
			"SPELLING" : "Anhui"
		},
		{
			"ID" : "340100",
			"PARENT_ID" : "340000",
			"DISTRICT_NAME" : "合肥市",
			"SPELLING" : "Hefei"
		},
		{
			"ID" : "340102",
			"PARENT_ID" : "340100",
			"DISTRICT_NAME" : "瑶海区",
			"SPELLING" : "Yaohai"
		},
		{
			"ID" : "340103",
			"PARENT_ID" : "340100",
			"DISTRICT_NAME" : "庐阳区",
			"SPELLING" : "Luyang"
		},
		{
			"ID" : "340104",
			"PARENT_ID" : "340100",
			"DISTRICT_NAME" : "蜀山区",
			"SPELLING" : "Shushan"
		},
		{
			"ID" : "340111",
			"PARENT_ID" : "340100",
			"DISTRICT_NAME" : "包河区",
			"SPELLING" : "Baohe"
		},
		{
			"ID" : "340121",
			"PARENT_ID" : "340100",
			"DISTRICT_NAME" : "长丰县",
			"SPELLING" : "Changfeng"
		},
		{
			"ID" : "340122",
			"PARENT_ID" : "340100",
			"DISTRICT_NAME" : "肥东县",
			"SPELLING" : "Feidong"
		},
		{
			"ID" : "340123",
			"PARENT_ID" : "340100",
			"DISTRICT_NAME" : "肥西县",
			"SPELLING" : "Feixi"
		},
		{
			"ID" : "340124",
			"PARENT_ID" : "340100",
			"DISTRICT_NAME" : "庐江县",
			"SPELLING" : "Lujiang"
		},
		{
			"ID" : "340181",
			"PARENT_ID" : "340100",
			"DISTRICT_NAME" : "巢湖市",
			"SPELLING" : "Chaohu"
		},
		{
			"ID" : "340200",
			"PARENT_ID" : "340000",
			"DISTRICT_NAME" : "芜湖市",
			"SPELLING" : "Wuhu"
		},
		{
			"ID" : "340202",
			"PARENT_ID" : "340200",
			"DISTRICT_NAME" : "镜湖区",
			"SPELLING" : "Jinghu"
		},
		{
			"ID" : "340203",
			"PARENT_ID" : "340200",
			"DISTRICT_NAME" : "弋江区",
			"SPELLING" : "Yijiang"
		},
		{
			"ID" : "340207",
			"PARENT_ID" : "340200",
			"DISTRICT_NAME" : "鸠江区",
			"SPELLING" : "Jiujiang"
		},
		{
			"ID" : "340208",
			"PARENT_ID" : "340200",
			"DISTRICT_NAME" : "三山区",
			"SPELLING" : "Sanshan"
		},
		{
			"ID" : "340221",
			"PARENT_ID" : "340200",
			"DISTRICT_NAME" : "芜湖县",
			"SPELLING" : "Wuhu"
		},
		{
			"ID" : "340222",
			"PARENT_ID" : "340200",
			"DISTRICT_NAME" : "繁昌县",
			"SPELLING" : "Fanchang"
		},
		{
			"ID" : "340223",
			"PARENT_ID" : "340200",
			"DISTRICT_NAME" : "南陵县",
			"SPELLING" : "Nanling"
		},
		{
			"ID" : "340225",
			"PARENT_ID" : "340200",
			"DISTRICT_NAME" : "无为县",
			"SPELLING" : "Wuwei"
		},
		{
			"ID" : "340300",
			"PARENT_ID" : "340000",
			"DISTRICT_NAME" : "蚌埠市",
			"SPELLING" : "Bengbu"
		},
		{
			"ID" : "340302",
			"PARENT_ID" : "340300",
			"DISTRICT_NAME" : "龙子湖区",
			"SPELLING" : "Longzihu"
		},
		{
			"ID" : "340303",
			"PARENT_ID" : "340300",
			"DISTRICT_NAME" : "蚌山区",
			"SPELLING" : "Bengshan"
		},
		{
			"ID" : "340304",
			"PARENT_ID" : "340300",
			"DISTRICT_NAME" : "禹会区",
			"SPELLING" : "Yuhui"
		},
		{
			"ID" : "340311",
			"PARENT_ID" : "340300",
			"DISTRICT_NAME" : "淮上区",
			"SPELLING" : "Huaishang"
		},
		{
			"ID" : "340321",
			"PARENT_ID" : "340300",
			"DISTRICT_NAME" : "怀远县",
			"SPELLING" : "Huaiyuan"
		},
		{
			"ID" : "340322",
			"PARENT_ID" : "340300",
			"DISTRICT_NAME" : "五河县",
			"SPELLING" : "Wuhe"
		},
		{
			"ID" : "340323",
			"PARENT_ID" : "340300",
			"DISTRICT_NAME" : "固镇县",
			"SPELLING" : "Guzhen"
		},
		{
			"ID" : "340400",
			"PARENT_ID" : "340000",
			"DISTRICT_NAME" : "淮南市",
			"SPELLING" : "Huainan"
		},
		{
			"ID" : "340402",
			"PARENT_ID" : "340400",
			"DISTRICT_NAME" : "大通区",
			"SPELLING" : "Datong"
		},
		{
			"ID" : "340403",
			"PARENT_ID" : "340400",
			"DISTRICT_NAME" : "田家庵区",
			"SPELLING" : "Tianjiaan"
		},
		{
			"ID" : "340404",
			"PARENT_ID" : "340400",
			"DISTRICT_NAME" : "谢家集区",
			"SPELLING" : "Xiejiaji"
		},
		{
			"ID" : "340405",
			"PARENT_ID" : "340400",
			"DISTRICT_NAME" : "八公山区",
			"SPELLING" : "Bagongshan"
		},
		{
			"ID" : "340406",
			"PARENT_ID" : "340400",
			"DISTRICT_NAME" : "潘集区",
			"SPELLING" : "Panji"
		},
		{
			"ID" : "340421",
			"PARENT_ID" : "340400",
			"DISTRICT_NAME" : "凤台县",
			"SPELLING" : "Fengtai"
		},
		{
			"ID" : "340500",
			"PARENT_ID" : "340000",
			"DISTRICT_NAME" : "马鞍山市",
			"SPELLING" : "Maanshan"
		},
		{
			"ID" : "340503",
			"PARENT_ID" : "340500",
			"DISTRICT_NAME" : "花山区",
			"SPELLING" : "Huashan"
		},
		{
			"ID" : "340504",
			"PARENT_ID" : "340500",
			"DISTRICT_NAME" : "雨山区",
			"SPELLING" : "Yushan"
		},
		{
			"ID" : "340506",
			"PARENT_ID" : "340500",
			"DISTRICT_NAME" : "博望区",
			"SPELLING" : "Bowang"
		},
		{
			"ID" : "340521",
			"PARENT_ID" : "340500",
			"DISTRICT_NAME" : "当涂县",
			"SPELLING" : "Dangtu"
		},
		{
			"ID" : "340522",
			"PARENT_ID" : "340500",
			"DISTRICT_NAME" : "含山县",
			"SPELLING" : "Hanshan "
		},
		{
			"ID" : "340523",
			"PARENT_ID" : "340500",
			"DISTRICT_NAME" : "和县",
			"SPELLING" : "Hexian"
		},
		{
			"ID" : "340600",
			"PARENT_ID" : "340000",
			"DISTRICT_NAME" : "淮北市",
			"SPELLING" : "Huaibei"
		},
		{
			"ID" : "340602",
			"PARENT_ID" : "340600",
			"DISTRICT_NAME" : "杜集区",
			"SPELLING" : "Duji"
		},
		{
			"ID" : "340603",
			"PARENT_ID" : "340600",
			"DISTRICT_NAME" : "相山区",
			"SPELLING" : "Xiangshan"
		},
		{
			"ID" : "340604",
			"PARENT_ID" : "340600",
			"DISTRICT_NAME" : "烈山区",
			"SPELLING" : "Lieshan"
		},
		{
			"ID" : "340621",
			"PARENT_ID" : "340600",
			"DISTRICT_NAME" : "濉溪县",
			"SPELLING" : "Suixi"
		},
		{
			"ID" : "340700",
			"PARENT_ID" : "340000",
			"DISTRICT_NAME" : "铜陵市",
			"SPELLING" : "Tongling"
		},
		{
			"ID" : "340702",
			"PARENT_ID" : "340700",
			"DISTRICT_NAME" : "铜官山区",
			"SPELLING" : "Tongguanshan"
		},
		{
			"ID" : "340703",
			"PARENT_ID" : "340700",
			"DISTRICT_NAME" : "狮子山区",
			"SPELLING" : "Shizishan"
		},
		{
			"ID" : "340711",
			"PARENT_ID" : "340700",
			"DISTRICT_NAME" : "郊区",
			"SPELLING" : "Jiaoqu"
		},
		{
			"ID" : "340721",
			"PARENT_ID" : "340700",
			"DISTRICT_NAME" : "铜陵县",
			"SPELLING" : "Tongling"
		},
		{
			"ID" : "340800",
			"PARENT_ID" : "340000",
			"DISTRICT_NAME" : "安庆市",
			"SPELLING" : "Anqing"
		},
		{
			"ID" : "340802",
			"PARENT_ID" : "340800",
			"DISTRICT_NAME" : "迎江区",
			"SPELLING" : "Yingjiang"
		},
		{
			"ID" : "340803",
			"PARENT_ID" : "340800",
			"DISTRICT_NAME" : "大观区",
			"SPELLING" : "Daguan"
		},
		{
			"ID" : "340811",
			"PARENT_ID" : "340800",
			"DISTRICT_NAME" : "宜秀区",
			"SPELLING" : "Yixiu"
		},
		{
			"ID" : "340822",
			"PARENT_ID" : "340800",
			"DISTRICT_NAME" : "怀宁县",
			"SPELLING" : "Huaining"
		},
		{
			"ID" : "340823",
			"PARENT_ID" : "340800",
			"DISTRICT_NAME" : "枞阳县",
			"SPELLING" : "Zongyang"
		},
		{
			"ID" : "340824",
			"PARENT_ID" : "340800",
			"DISTRICT_NAME" : "潜山县",
			"SPELLING" : "Qianshan"
		},
		{
			"ID" : "340825",
			"PARENT_ID" : "340800",
			"DISTRICT_NAME" : "太湖县",
			"SPELLING" : "Taihu"
		},
		{
			"ID" : "340826",
			"PARENT_ID" : "340800",
			"DISTRICT_NAME" : "宿松县",
			"SPELLING" : "Susong"
		},
		{
			"ID" : "340827",
			"PARENT_ID" : "340800",
			"DISTRICT_NAME" : "望江县",
			"SPELLING" : "Wangjiang"
		},
		{
			"ID" : "340828",
			"PARENT_ID" : "340800",
			"DISTRICT_NAME" : "岳西县",
			"SPELLING" : "Yuexi"
		},
		{
			"ID" : "340881",
			"PARENT_ID" : "340800",
			"DISTRICT_NAME" : "桐城市",
			"SPELLING" : "Tongcheng"
		},
		{
			"ID" : "341000",
			"PARENT_ID" : "340000",
			"DISTRICT_NAME" : "黄山市",
			"SPELLING" : "Huangshan"
		},
		{
			"ID" : "341002",
			"PARENT_ID" : "341000",
			"DISTRICT_NAME" : "屯溪区",
			"SPELLING" : "Tunxi"
		},
		{
			"ID" : "341003",
			"PARENT_ID" : "341000",
			"DISTRICT_NAME" : "黄山区",
			"SPELLING" : "Huangshan"
		},
		{
			"ID" : "341004",
			"PARENT_ID" : "341000",
			"DISTRICT_NAME" : "徽州区",
			"SPELLING" : "Huizhou"
		},
		{
			"ID" : "341021",
			"PARENT_ID" : "341000",
			"DISTRICT_NAME" : "歙县",
			"SPELLING" : "Shexian"
		},
		{
			"ID" : "341022",
			"PARENT_ID" : "341000",
			"DISTRICT_NAME" : "休宁县",
			"SPELLING" : "Xiuning"
		},
		{
			"ID" : "341023",
			"PARENT_ID" : "341000",
			"DISTRICT_NAME" : "黟县",
			"SPELLING" : "Yixian"
		},
		{
			"ID" : "341024",
			"PARENT_ID" : "341000",
			"DISTRICT_NAME" : "祁门县",
			"SPELLING" : "Qimen"
		},
		{
			"ID" : "341100",
			"PARENT_ID" : "340000",
			"DISTRICT_NAME" : "滁州市",
			"SPELLING" : "Chuzhou"
		},
		{
			"ID" : "341102",
			"PARENT_ID" : "341100",
			"DISTRICT_NAME" : "琅琊区",
			"SPELLING" : "Langya"
		},
		{
			"ID" : "341103",
			"PARENT_ID" : "341100",
			"DISTRICT_NAME" : "南谯区",
			"SPELLING" : "Nanqiao"
		},
		{
			"ID" : "341122",
			"PARENT_ID" : "341100",
			"DISTRICT_NAME" : "来安县",
			"SPELLING" : "Laian"
		},
		{
			"ID" : "341124",
			"PARENT_ID" : "341100",
			"DISTRICT_NAME" : "全椒县",
			"SPELLING" : "Quanjiao"
		},
		{
			"ID" : "341125",
			"PARENT_ID" : "341100",
			"DISTRICT_NAME" : "定远县",
			"SPELLING" : "Dingyuan"
		},
		{
			"ID" : "341126",
			"PARENT_ID" : "341100",
			"DISTRICT_NAME" : "凤阳县",
			"SPELLING" : "Fengyang"
		},
		{
			"ID" : "341181",
			"PARENT_ID" : "341100",
			"DISTRICT_NAME" : "天长市",
			"SPELLING" : "Tianchang"
		},
		{
			"ID" : "341182",
			"PARENT_ID" : "341100",
			"DISTRICT_NAME" : "明光市",
			"SPELLING" : "Mingguang"
		},
		{
			"ID" : "341200",
			"PARENT_ID" : "340000",
			"DISTRICT_NAME" : "阜阳市",
			"SPELLING" : "Fuyang"
		},
		{
			"ID" : "341202",
			"PARENT_ID" : "341200",
			"DISTRICT_NAME" : "颍州区",
			"SPELLING" : "Yingzhou"
		},
		{
			"ID" : "341203",
			"PARENT_ID" : "341200",
			"DISTRICT_NAME" : "颍东区",
			"SPELLING" : "Yingdong"
		},
		{
			"ID" : "341204",
			"PARENT_ID" : "341200",
			"DISTRICT_NAME" : "颍泉区",
			"SPELLING" : "Yingquan"
		},
		{
			"ID" : "341221",
			"PARENT_ID" : "341200",
			"DISTRICT_NAME" : "临泉县",
			"SPELLING" : "Linquan"
		},
		{
			"ID" : "341222",
			"PARENT_ID" : "341200",
			"DISTRICT_NAME" : "太和县",
			"SPELLING" : "Taihe"
		},
		{
			"ID" : "341225",
			"PARENT_ID" : "341200",
			"DISTRICT_NAME" : "阜南县",
			"SPELLING" : "Funan"
		},
		{
			"ID" : "341226",
			"PARENT_ID" : "341200",
			"DISTRICT_NAME" : "颍上县",
			"SPELLING" : "Yingshang"
		},
		{
			"ID" : "341282",
			"PARENT_ID" : "341200",
			"DISTRICT_NAME" : "界首市",
			"SPELLING" : "Jieshou"
		},
		{
			"ID" : "341300",
			"PARENT_ID" : "340000",
			"DISTRICT_NAME" : "宿州市",
			"SPELLING" : "Suzhou"
		},
		{
			"ID" : "341302",
			"PARENT_ID" : "341300",
			"DISTRICT_NAME" : "埇桥区",
			"SPELLING" : "Yongqiao"
		},
		{
			"ID" : "341321",
			"PARENT_ID" : "341300",
			"DISTRICT_NAME" : "砀山县",
			"SPELLING" : "Dangshan"
		},
		{
			"ID" : "341322",
			"PARENT_ID" : "341300",
			"DISTRICT_NAME" : "萧县",
			"SPELLING" : "Xiaoxian"
		},
		{
			"ID" : "341323",
			"PARENT_ID" : "341300",
			"DISTRICT_NAME" : "灵璧县",
			"SPELLING" : "Lingbi"
		},
		{
			"ID" : "341324",
			"PARENT_ID" : "341300",
			"DISTRICT_NAME" : "泗县",
			"SPELLING" : "Sixian"
		},
		{
			"ID" : "341500",
			"PARENT_ID" : "340000",
			"DISTRICT_NAME" : "六安市",
			"SPELLING" : "Luan"
		},
		{
			"ID" : "341502",
			"PARENT_ID" : "341500",
			"DISTRICT_NAME" : "金安区",
			"SPELLING" : "Jinan"
		},
		{
			"ID" : "341503",
			"PARENT_ID" : "341500",
			"DISTRICT_NAME" : "裕安区",
			"SPELLING" : "Yuan"
		},
		{
			"ID" : "341521",
			"PARENT_ID" : "341500",
			"DISTRICT_NAME" : "寿县",
			"SPELLING" : "Shouxian"
		},
		{
			"ID" : "341522",
			"PARENT_ID" : "341500",
			"DISTRICT_NAME" : "霍邱县",
			"SPELLING" : "Huoqiu"
		},
		{
			"ID" : "341523",
			"PARENT_ID" : "341500",
			"DISTRICT_NAME" : "舒城县",
			"SPELLING" : "Shucheng"
		},
		{
			"ID" : "341524",
			"PARENT_ID" : "341500",
			"DISTRICT_NAME" : "金寨县",
			"SPELLING" : "Jinzhai"
		},
		{
			"ID" : "341525",
			"PARENT_ID" : "341500",
			"DISTRICT_NAME" : "霍山县",
			"SPELLING" : "Huoshan"
		},
		{
			"ID" : "341600",
			"PARENT_ID" : "340000",
			"DISTRICT_NAME" : "亳州市",
			"SPELLING" : "Bozhou"
		},
		{
			"ID" : "341602",
			"PARENT_ID" : "341600",
			"DISTRICT_NAME" : "谯城区",
			"SPELLING" : "Qiaocheng"
		},
		{
			"ID" : "341621",
			"PARENT_ID" : "341600",
			"DISTRICT_NAME" : "涡阳县",
			"SPELLING" : "Guoyang"
		},
		{
			"ID" : "341622",
			"PARENT_ID" : "341600",
			"DISTRICT_NAME" : "蒙城县",
			"SPELLING" : "Mengcheng"
		},
		{
			"ID" : "341623",
			"PARENT_ID" : "341600",
			"DISTRICT_NAME" : "利辛县",
			"SPELLING" : "Lixin"
		},
		{
			"ID" : "341700",
			"PARENT_ID" : "340000",
			"DISTRICT_NAME" : "池州市",
			"SPELLING" : "Chizhou"
		},
		{
			"ID" : "341702",
			"PARENT_ID" : "341700",
			"DISTRICT_NAME" : "贵池区",
			"SPELLING" : "Guichi"
		},
		{
			"ID" : "341721",
			"PARENT_ID" : "341700",
			"DISTRICT_NAME" : "东至县",
			"SPELLING" : "Dongzhi"
		},
		{
			"ID" : "341722",
			"PARENT_ID" : "341700",
			"DISTRICT_NAME" : "石台县",
			"SPELLING" : "Shitai"
		},
		{
			"ID" : "341723",
			"PARENT_ID" : "341700",
			"DISTRICT_NAME" : "青阳县",
			"SPELLING" : "Qingyang"
		},
		{
			"ID" : "341800",
			"PARENT_ID" : "340000",
			"DISTRICT_NAME" : "宣城市",
			"SPELLING" : "Xuancheng"
		},
		{
			"ID" : "341802",
			"PARENT_ID" : "341800",
			"DISTRICT_NAME" : "宣州区",
			"SPELLING" : "Xuanzhou"
		},
		{
			"ID" : "341821",
			"PARENT_ID" : "341800",
			"DISTRICT_NAME" : "郎溪县",
			"SPELLING" : "Langxi"
		},
		{
			"ID" : "341822",
			"PARENT_ID" : "341800",
			"DISTRICT_NAME" : "广德县",
			"SPELLING" : "Guangde"
		},
		{
			"ID" : "341823",
			"PARENT_ID" : "341800",
			"DISTRICT_NAME" : "泾县",
			"SPELLING" : "Jingxian"
		},
		{
			"ID" : "341824",
			"PARENT_ID" : "341800",
			"DISTRICT_NAME" : "绩溪县",
			"SPELLING" : "Jixi"
		},
		{
			"ID" : "341825",
			"PARENT_ID" : "341800",
			"DISTRICT_NAME" : "旌德县",
			"SPELLING" : "Jingde"
		},
		{
			"ID" : "341881",
			"PARENT_ID" : "341800",
			"DISTRICT_NAME" : "宁国市",
			"SPELLING" : "Ningguo"
		},
		{
			"ID" : "350000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "福建省",
			"SPELLING" : "Fujian"
		},
		{
			"ID" : "350100",
			"PARENT_ID" : "350000",
			"DISTRICT_NAME" : "福州市",
			"SPELLING" : "Fuzhou"
		},
		{
			"ID" : "350102",
			"PARENT_ID" : "350100",
			"DISTRICT_NAME" : "鼓楼区",
			"SPELLING" : "Gulou"
		},
		{
			"ID" : "350103",
			"PARENT_ID" : "350100",
			"DISTRICT_NAME" : "台江区",
			"SPELLING" : "Taijiang"
		},
		{
			"ID" : "350104",
			"PARENT_ID" : "350100",
			"DISTRICT_NAME" : "仓山区",
			"SPELLING" : "Cangshan"
		},
		{
			"ID" : "350105",
			"PARENT_ID" : "350100",
			"DISTRICT_NAME" : "马尾区",
			"SPELLING" : "Mawei"
		},
		{
			"ID" : "350111",
			"PARENT_ID" : "350100",
			"DISTRICT_NAME" : "晋安区",
			"SPELLING" : "Jinan"
		},
		{
			"ID" : "350121",
			"PARENT_ID" : "350100",
			"DISTRICT_NAME" : "闽侯县",
			"SPELLING" : "Minhou"
		},
		{
			"ID" : "350122",
			"PARENT_ID" : "350100",
			"DISTRICT_NAME" : "连江县",
			"SPELLING" : "Lianjiang"
		},
		{
			"ID" : "350123",
			"PARENT_ID" : "350100",
			"DISTRICT_NAME" : "罗源县",
			"SPELLING" : "Luoyuan"
		},
		{
			"ID" : "350124",
			"PARENT_ID" : "350100",
			"DISTRICT_NAME" : "闽清县",
			"SPELLING" : "Minqing"
		},
		{
			"ID" : "350125",
			"PARENT_ID" : "350100",
			"DISTRICT_NAME" : "永泰县",
			"SPELLING" : "Yongtai"
		},
		{
			"ID" : "350128",
			"PARENT_ID" : "350100",
			"DISTRICT_NAME" : "平潭县",
			"SPELLING" : "Pingtan"
		},
		{
			"ID" : "350181",
			"PARENT_ID" : "350100",
			"DISTRICT_NAME" : "福清市",
			"SPELLING" : "Fuqing"
		},
		{
			"ID" : "350182",
			"PARENT_ID" : "350100",
			"DISTRICT_NAME" : "长乐市",
			"SPELLING" : "Changle"
		},
		{
			"ID" : "350200",
			"PARENT_ID" : "350000",
			"DISTRICT_NAME" : "厦门市",
			"SPELLING" : "Xiamen"
		},
		{
			"ID" : "350203",
			"PARENT_ID" : "350200",
			"DISTRICT_NAME" : "思明区",
			"SPELLING" : "Siming"
		},
		{
			"ID" : "350205",
			"PARENT_ID" : "350200",
			"DISTRICT_NAME" : "海沧区",
			"SPELLING" : "Haicang"
		},
		{
			"ID" : "350206",
			"PARENT_ID" : "350200",
			"DISTRICT_NAME" : "湖里区",
			"SPELLING" : "Huli"
		},
		{
			"ID" : "350211",
			"PARENT_ID" : "350200",
			"DISTRICT_NAME" : "集美区",
			"SPELLING" : "Jimei"
		},
		{
			"ID" : "350212",
			"PARENT_ID" : "350200",
			"DISTRICT_NAME" : "同安区",
			"SPELLING" : "Tongan"
		},
		{
			"ID" : "350213",
			"PARENT_ID" : "350200",
			"DISTRICT_NAME" : "翔安区",
			"SPELLING" : "Xiangan"
		},
		{
			"ID" : "350300",
			"PARENT_ID" : "350000",
			"DISTRICT_NAME" : "莆田市",
			"SPELLING" : "Putian"
		},
		{
			"ID" : "350302",
			"PARENT_ID" : "350300",
			"DISTRICT_NAME" : "城厢区",
			"SPELLING" : "Chengxiang"
		},
		{
			"ID" : "350303",
			"PARENT_ID" : "350300",
			"DISTRICT_NAME" : "涵江区",
			"SPELLING" : "Hanjiang"
		},
		{
			"ID" : "350304",
			"PARENT_ID" : "350300",
			"DISTRICT_NAME" : "荔城区",
			"SPELLING" : "Licheng"
		},
		{
			"ID" : "350305",
			"PARENT_ID" : "350300",
			"DISTRICT_NAME" : "秀屿区",
			"SPELLING" : "Xiuyu"
		},
		{
			"ID" : "350322",
			"PARENT_ID" : "350300",
			"DISTRICT_NAME" : "仙游县",
			"SPELLING" : "Xianyou"
		},
		{
			"ID" : "350400",
			"PARENT_ID" : "350000",
			"DISTRICT_NAME" : "三明市",
			"SPELLING" : "Sanming"
		},
		{
			"ID" : "350402",
			"PARENT_ID" : "350400",
			"DISTRICT_NAME" : "梅列区",
			"SPELLING" : "Meilie"
		},
		{
			"ID" : "350403",
			"PARENT_ID" : "350400",
			"DISTRICT_NAME" : "三元区",
			"SPELLING" : "Sanyuan"
		},
		{
			"ID" : "350421",
			"PARENT_ID" : "350400",
			"DISTRICT_NAME" : "明溪县",
			"SPELLING" : "Mingxi"
		},
		{
			"ID" : "350423",
			"PARENT_ID" : "350400",
			"DISTRICT_NAME" : "清流县",
			"SPELLING" : "Qingliu"
		},
		{
			"ID" : "350424",
			"PARENT_ID" : "350400",
			"DISTRICT_NAME" : "宁化县",
			"SPELLING" : "Ninghua"
		},
		{
			"ID" : "350425",
			"PARENT_ID" : "350400",
			"DISTRICT_NAME" : "大田县",
			"SPELLING" : "Datian"
		},
		{
			"ID" : "350426",
			"PARENT_ID" : "350400",
			"DISTRICT_NAME" : "尤溪县",
			"SPELLING" : "Youxi"
		},
		{
			"ID" : "350427",
			"PARENT_ID" : "350400",
			"DISTRICT_NAME" : "沙县",
			"SPELLING" : "Shaxian"
		},
		{
			"ID" : "350428",
			"PARENT_ID" : "350400",
			"DISTRICT_NAME" : "将乐县",
			"SPELLING" : "Jiangle"
		},
		{
			"ID" : "350429",
			"PARENT_ID" : "350400",
			"DISTRICT_NAME" : "泰宁县",
			"SPELLING" : "Taining"
		},
		{
			"ID" : "350430",
			"PARENT_ID" : "350400",
			"DISTRICT_NAME" : "建宁县",
			"SPELLING" : "Jianning"
		},
		{
			"ID" : "350481",
			"PARENT_ID" : "350400",
			"DISTRICT_NAME" : "永安市",
			"SPELLING" : "Yongan"
		},
		{
			"ID" : "350500",
			"PARENT_ID" : "350000",
			"DISTRICT_NAME" : "泉州市",
			"SPELLING" : "Quanzhou"
		},
		{
			"ID" : "350502",
			"PARENT_ID" : "350500",
			"DISTRICT_NAME" : "鲤城区",
			"SPELLING" : "Licheng"
		},
		{
			"ID" : "350503",
			"PARENT_ID" : "350500",
			"DISTRICT_NAME" : "丰泽区",
			"SPELLING" : "Fengze"
		},
		{
			"ID" : "350504",
			"PARENT_ID" : "350500",
			"DISTRICT_NAME" : "洛江区",
			"SPELLING" : "Luojiang"
		},
		{
			"ID" : "350505",
			"PARENT_ID" : "350500",
			"DISTRICT_NAME" : "泉港区",
			"SPELLING" : "Quangang"
		},
		{
			"ID" : "350521",
			"PARENT_ID" : "350500",
			"DISTRICT_NAME" : "惠安县",
			"SPELLING" : "Huian"
		},
		{
			"ID" : "350524",
			"PARENT_ID" : "350500",
			"DISTRICT_NAME" : "安溪县",
			"SPELLING" : "Anxi"
		},
		{
			"ID" : "350525",
			"PARENT_ID" : "350500",
			"DISTRICT_NAME" : "永春县",
			"SPELLING" : "Yongchun"
		},
		{
			"ID" : "350526",
			"PARENT_ID" : "350500",
			"DISTRICT_NAME" : "德化县",
			"SPELLING" : "Dehua"
		},
		{
			"ID" : "350527",
			"PARENT_ID" : "350500",
			"DISTRICT_NAME" : "金门县",
			"SPELLING" : "Jinmen"
		},
		{
			"ID" : "350581",
			"PARENT_ID" : "350500",
			"DISTRICT_NAME" : "石狮市",
			"SPELLING" : "Shishi"
		},
		{
			"ID" : "350582",
			"PARENT_ID" : "350500",
			"DISTRICT_NAME" : "晋江市",
			"SPELLING" : "Jinjiang"
		},
		{
			"ID" : "350583",
			"PARENT_ID" : "350500",
			"DISTRICT_NAME" : "南安市",
			"SPELLING" : "Nanan"
		},
		{
			"ID" : "350600",
			"PARENT_ID" : "350000",
			"DISTRICT_NAME" : "漳州市",
			"SPELLING" : "Zhangzhou"
		},
		{
			"ID" : "350602",
			"PARENT_ID" : "350600",
			"DISTRICT_NAME" : "芗城区",
			"SPELLING" : "Xiangcheng"
		},
		{
			"ID" : "350603",
			"PARENT_ID" : "350600",
			"DISTRICT_NAME" : "龙文区",
			"SPELLING" : "Longwen"
		},
		{
			"ID" : "350622",
			"PARENT_ID" : "350600",
			"DISTRICT_NAME" : "云霄县",
			"SPELLING" : "Yunxiao"
		},
		{
			"ID" : "350623",
			"PARENT_ID" : "350600",
			"DISTRICT_NAME" : "漳浦县",
			"SPELLING" : "Zhangpu"
		},
		{
			"ID" : "350624",
			"PARENT_ID" : "350600",
			"DISTRICT_NAME" : "诏安县",
			"SPELLING" : "Zhaoan"
		},
		{
			"ID" : "350625",
			"PARENT_ID" : "350600",
			"DISTRICT_NAME" : "长泰县",
			"SPELLING" : "Changtai"
		},
		{
			"ID" : "350626",
			"PARENT_ID" : "350600",
			"DISTRICT_NAME" : "东山县",
			"SPELLING" : "Dongshan"
		},
		{
			"ID" : "350627",
			"PARENT_ID" : "350600",
			"DISTRICT_NAME" : "南靖县",
			"SPELLING" : "Nanjing"
		},
		{
			"ID" : "350628",
			"PARENT_ID" : "350600",
			"DISTRICT_NAME" : "平和县",
			"SPELLING" : "Pinghe"
		},
		{
			"ID" : "350629",
			"PARENT_ID" : "350600",
			"DISTRICT_NAME" : "华安县",
			"SPELLING" : "Huaan"
		},
		{
			"ID" : "350681",
			"PARENT_ID" : "350600",
			"DISTRICT_NAME" : "龙海市",
			"SPELLING" : "Longhai"
		},
		{
			"ID" : "350700",
			"PARENT_ID" : "350000",
			"DISTRICT_NAME" : "南平市",
			"SPELLING" : "Nanping"
		},
		{
			"ID" : "350702",
			"PARENT_ID" : "350700",
			"DISTRICT_NAME" : "延平区",
			"SPELLING" : "Yanping"
		},
		{
			"ID" : "350703",
			"PARENT_ID" : "350700",
			"DISTRICT_NAME" : "建阳区",
			"SPELLING" : "Jianyang"
		},
		{
			"ID" : "350721",
			"PARENT_ID" : "350700",
			"DISTRICT_NAME" : "顺昌县",
			"SPELLING" : "Shunchang"
		},
		{
			"ID" : "350722",
			"PARENT_ID" : "350700",
			"DISTRICT_NAME" : "浦城县",
			"SPELLING" : "Pucheng"
		},
		{
			"ID" : "350723",
			"PARENT_ID" : "350700",
			"DISTRICT_NAME" : "光泽县",
			"SPELLING" : "Guangze"
		},
		{
			"ID" : "350724",
			"PARENT_ID" : "350700",
			"DISTRICT_NAME" : "松溪县",
			"SPELLING" : "Songxi"
		},
		{
			"ID" : "350725",
			"PARENT_ID" : "350700",
			"DISTRICT_NAME" : "政和县",
			"SPELLING" : "Zhenghe"
		},
		{
			"ID" : "350781",
			"PARENT_ID" : "350700",
			"DISTRICT_NAME" : "邵武市",
			"SPELLING" : "Shaowu"
		},
		{
			"ID" : "350782",
			"PARENT_ID" : "350700",
			"DISTRICT_NAME" : "武夷山市",
			"SPELLING" : "Wuyishan"
		},
		{
			"ID" : "350783",
			"PARENT_ID" : "350700",
			"DISTRICT_NAME" : "建瓯市",
			"SPELLING" : "Jianou"
		},
		{
			"ID" : "350800",
			"PARENT_ID" : "350000",
			"DISTRICT_NAME" : "龙岩市",
			"SPELLING" : "Longyan"
		},
		{
			"ID" : "350802",
			"PARENT_ID" : "350800",
			"DISTRICT_NAME" : "新罗区",
			"SPELLING" : "Xinluo"
		},
		{
			"ID" : "350821",
			"PARENT_ID" : "350800",
			"DISTRICT_NAME" : "长汀县",
			"SPELLING" : "Changting"
		},
		{
			"ID" : "350822",
			"PARENT_ID" : "350800",
			"DISTRICT_NAME" : "永定区",
			"SPELLING" : "Yongding"
		},
		{
			"ID" : "350823",
			"PARENT_ID" : "350800",
			"DISTRICT_NAME" : "上杭县",
			"SPELLING" : "Shanghang"
		},
		{
			"ID" : "350824",
			"PARENT_ID" : "350800",
			"DISTRICT_NAME" : "武平县",
			"SPELLING" : "Wuping"
		},
		{
			"ID" : "350825",
			"PARENT_ID" : "350800",
			"DISTRICT_NAME" : "连城县",
			"SPELLING" : "Liancheng"
		},
		{
			"ID" : "350881",
			"PARENT_ID" : "350800",
			"DISTRICT_NAME" : "漳平市",
			"SPELLING" : "Zhangping"
		},
		{
			"ID" : "350900",
			"PARENT_ID" : "350000",
			"DISTRICT_NAME" : "宁德市",
			"SPELLING" : "Ningde"
		},
		{
			"ID" : "350902",
			"PARENT_ID" : "350900",
			"DISTRICT_NAME" : "蕉城区",
			"SPELLING" : "Jiaocheng"
		},
		{
			"ID" : "350921",
			"PARENT_ID" : "350900",
			"DISTRICT_NAME" : "霞浦县",
			"SPELLING" : "Xiapu"
		},
		{
			"ID" : "350922",
			"PARENT_ID" : "350900",
			"DISTRICT_NAME" : "古田县",
			"SPELLING" : "Gutian"
		},
		{
			"ID" : "350923",
			"PARENT_ID" : "350900",
			"DISTRICT_NAME" : "屏南县",
			"SPELLING" : "Pingnan"
		},
		{
			"ID" : "350924",
			"PARENT_ID" : "350900",
			"DISTRICT_NAME" : "寿宁县",
			"SPELLING" : "Shouning"
		},
		{
			"ID" : "350925",
			"PARENT_ID" : "350900",
			"DISTRICT_NAME" : "周宁县",
			"SPELLING" : "Zhouning"
		},
		{
			"ID" : "350926",
			"PARENT_ID" : "350900",
			"DISTRICT_NAME" : "柘荣县",
			"SPELLING" : "Zherong"
		},
		{
			"ID" : "350981",
			"PARENT_ID" : "350900",
			"DISTRICT_NAME" : "福安市",
			"SPELLING" : "Fuan"
		},
		{
			"ID" : "350982",
			"PARENT_ID" : "350900",
			"DISTRICT_NAME" : "福鼎市",
			"SPELLING" : "Fuding"
		},
		{
			"ID" : "360000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "江西省",
			"SPELLING" : "Jiangxi"
		},
		{
			"ID" : "360100",
			"PARENT_ID" : "360000",
			"DISTRICT_NAME" : "南昌市",
			"SPELLING" : "Nanchang"
		},
		{
			"ID" : "360102",
			"PARENT_ID" : "360100",
			"DISTRICT_NAME" : "东湖区",
			"SPELLING" : "Donghu"
		},
		{
			"ID" : "360103",
			"PARENT_ID" : "360100",
			"DISTRICT_NAME" : "西湖区",
			"SPELLING" : "Xihu"
		},
		{
			"ID" : "360104",
			"PARENT_ID" : "360100",
			"DISTRICT_NAME" : "青云谱区",
			"SPELLING" : "Qingyunpu"
		},
		{
			"ID" : "360105",
			"PARENT_ID" : "360100",
			"DISTRICT_NAME" : "湾里区",
			"SPELLING" : "Wanli"
		},
		{
			"ID" : "360111",
			"PARENT_ID" : "360100",
			"DISTRICT_NAME" : "青山湖区",
			"SPELLING" : "Qingshanhu"
		},
		{
			"ID" : "360121",
			"PARENT_ID" : "360100",
			"DISTRICT_NAME" : "南昌县",
			"SPELLING" : "Nanchang"
		},
		{
			"ID" : "360122",
			"PARENT_ID" : "360100",
			"DISTRICT_NAME" : "新建县",
			"SPELLING" : "Xinjian"
		},
		{
			"ID" : "360123",
			"PARENT_ID" : "360100",
			"DISTRICT_NAME" : "安义县",
			"SPELLING" : "Anyi"
		},
		{
			"ID" : "360124",
			"PARENT_ID" : "360100",
			"DISTRICT_NAME" : "进贤县",
			"SPELLING" : "Jinxian"
		},
		{
			"ID" : "360200",
			"PARENT_ID" : "360000",
			"DISTRICT_NAME" : "景德镇市",
			"SPELLING" : "Jingdezhen"
		},
		{
			"ID" : "360202",
			"PARENT_ID" : "360200",
			"DISTRICT_NAME" : "昌江区",
			"SPELLING" : "Changjiang"
		},
		{
			"ID" : "360203",
			"PARENT_ID" : "360200",
			"DISTRICT_NAME" : "珠山区",
			"SPELLING" : "Zhushan"
		},
		{
			"ID" : "360222",
			"PARENT_ID" : "360200",
			"DISTRICT_NAME" : "浮梁县",
			"SPELLING" : "Fuliang"
		},
		{
			"ID" : "360281",
			"PARENT_ID" : "360200",
			"DISTRICT_NAME" : "乐平市",
			"SPELLING" : "Leping"
		},
		{
			"ID" : "360300",
			"PARENT_ID" : "360000",
			"DISTRICT_NAME" : "萍乡市",
			"SPELLING" : "Pingxiang"
		},
		{
			"ID" : "360302",
			"PARENT_ID" : "360300",
			"DISTRICT_NAME" : "安源区",
			"SPELLING" : "Anyuan"
		},
		{
			"ID" : "360313",
			"PARENT_ID" : "360300",
			"DISTRICT_NAME" : "湘东区",
			"SPELLING" : "Xiangdong"
		},
		{
			"ID" : "360321",
			"PARENT_ID" : "360300",
			"DISTRICT_NAME" : "莲花县",
			"SPELLING" : "Lianhua"
		},
		{
			"ID" : "360322",
			"PARENT_ID" : "360300",
			"DISTRICT_NAME" : "上栗县",
			"SPELLING" : "Shangli"
		},
		{
			"ID" : "360323",
			"PARENT_ID" : "360300",
			"DISTRICT_NAME" : "芦溪县",
			"SPELLING" : "Luxi"
		},
		{
			"ID" : "360400",
			"PARENT_ID" : "360000",
			"DISTRICT_NAME" : "九江市",
			"SPELLING" : "Jiujiang"
		},
		{
			"ID" : "360402",
			"PARENT_ID" : "360400",
			"DISTRICT_NAME" : "庐山区",
			"SPELLING" : "Lushan"
		},
		{
			"ID" : "360403",
			"PARENT_ID" : "360400",
			"DISTRICT_NAME" : "浔阳区",
			"SPELLING" : "Xunyang"
		},
		{
			"ID" : "360421",
			"PARENT_ID" : "360400",
			"DISTRICT_NAME" : "九江县",
			"SPELLING" : "Jiujiang"
		},
		{
			"ID" : "360423",
			"PARENT_ID" : "360400",
			"DISTRICT_NAME" : "武宁县",
			"SPELLING" : "Wuning"
		},
		{
			"ID" : "360424",
			"PARENT_ID" : "360400",
			"DISTRICT_NAME" : "修水县",
			"SPELLING" : "Xiushui"
		},
		{
			"ID" : "360425",
			"PARENT_ID" : "360400",
			"DISTRICT_NAME" : "永修县",
			"SPELLING" : "Yongxiu"
		},
		{
			"ID" : "360426",
			"PARENT_ID" : "360400",
			"DISTRICT_NAME" : "德安县",
			"SPELLING" : "Dean"
		},
		{
			"ID" : "360427",
			"PARENT_ID" : "360400",
			"DISTRICT_NAME" : "星子县",
			"SPELLING" : "Xingzi"
		},
		{
			"ID" : "360428",
			"PARENT_ID" : "360400",
			"DISTRICT_NAME" : "都昌县",
			"SPELLING" : "Duchang"
		},
		{
			"ID" : "360429",
			"PARENT_ID" : "360400",
			"DISTRICT_NAME" : "湖口县",
			"SPELLING" : "Hukou"
		},
		{
			"ID" : "360430",
			"PARENT_ID" : "360400",
			"DISTRICT_NAME" : "彭泽县",
			"SPELLING" : "Pengze"
		},
		{
			"ID" : "360481",
			"PARENT_ID" : "360400",
			"DISTRICT_NAME" : "瑞昌市",
			"SPELLING" : "Ruichang"
		},
		{
			"ID" : "360482",
			"PARENT_ID" : "360400",
			"DISTRICT_NAME" : "共青城市",
			"SPELLING" : "Gongqingcheng"
		},
		{
			"ID" : "360500",
			"PARENT_ID" : "360000",
			"DISTRICT_NAME" : "新余市",
			"SPELLING" : "Xinyu"
		},
		{
			"ID" : "360502",
			"PARENT_ID" : "360500",
			"DISTRICT_NAME" : "渝水区",
			"SPELLING" : "Yushui"
		},
		{
			"ID" : "360521",
			"PARENT_ID" : "360500",
			"DISTRICT_NAME" : "分宜县",
			"SPELLING" : "Fenyi"
		},
		{
			"ID" : "360600",
			"PARENT_ID" : "360000",
			"DISTRICT_NAME" : "鹰潭市",
			"SPELLING" : "Yingtan"
		},
		{
			"ID" : "360602",
			"PARENT_ID" : "360600",
			"DISTRICT_NAME" : "月湖区",
			"SPELLING" : "Yuehu"
		},
		{
			"ID" : "360622",
			"PARENT_ID" : "360600",
			"DISTRICT_NAME" : "余江县",
			"SPELLING" : "Yujiang"
		},
		{
			"ID" : "360681",
			"PARENT_ID" : "360600",
			"DISTRICT_NAME" : "贵溪市",
			"SPELLING" : "Guixi"
		},
		{
			"ID" : "360700",
			"PARENT_ID" : "360000",
			"DISTRICT_NAME" : "赣州市",
			"SPELLING" : "Ganzhou"
		},
		{
			"ID" : "360702",
			"PARENT_ID" : "360700",
			"DISTRICT_NAME" : "章贡区",
			"SPELLING" : "Zhanggong"
		},
		{
			"ID" : "360703",
			"PARENT_ID" : "360700",
			"DISTRICT_NAME" : "南康区",
			"SPELLING" : "Nankang"
		},
		{
			"ID" : "360721",
			"PARENT_ID" : "360700",
			"DISTRICT_NAME" : "赣县",
			"SPELLING" : "Ganxian"
		},
		{
			"ID" : "360722",
			"PARENT_ID" : "360700",
			"DISTRICT_NAME" : "信丰县",
			"SPELLING" : "Xinfeng"
		},
		{
			"ID" : "360723",
			"PARENT_ID" : "360700",
			"DISTRICT_NAME" : "大余县",
			"SPELLING" : "Dayu"
		},
		{
			"ID" : "360724",
			"PARENT_ID" : "360700",
			"DISTRICT_NAME" : "上犹县",
			"SPELLING" : "Shangyou"
		},
		{
			"ID" : "360725",
			"PARENT_ID" : "360700",
			"DISTRICT_NAME" : "崇义县",
			"SPELLING" : "Chongyi"
		},
		{
			"ID" : "360726",
			"PARENT_ID" : "360700",
			"DISTRICT_NAME" : "安远县",
			"SPELLING" : "Anyuan"
		},
		{
			"ID" : "360727",
			"PARENT_ID" : "360700",
			"DISTRICT_NAME" : "龙南县",
			"SPELLING" : "Longnan"
		},
		{
			"ID" : "360728",
			"PARENT_ID" : "360700",
			"DISTRICT_NAME" : "定南县",
			"SPELLING" : "Dingnan"
		},
		{
			"ID" : "360729",
			"PARENT_ID" : "360700",
			"DISTRICT_NAME" : "全南县",
			"SPELLING" : "Quannan"
		},
		{
			"ID" : "360730",
			"PARENT_ID" : "360700",
			"DISTRICT_NAME" : "宁都县",
			"SPELLING" : "Ningdu"
		},
		{
			"ID" : "360731",
			"PARENT_ID" : "360700",
			"DISTRICT_NAME" : "于都县",
			"SPELLING" : "Yudu"
		},
		{
			"ID" : "360732",
			"PARENT_ID" : "360700",
			"DISTRICT_NAME" : "兴国县",
			"SPELLING" : "Xingguo"
		},
		{
			"ID" : "360733",
			"PARENT_ID" : "360700",
			"DISTRICT_NAME" : "会昌县",
			"SPELLING" : "Huichang"
		},
		{
			"ID" : "360734",
			"PARENT_ID" : "360700",
			"DISTRICT_NAME" : "寻乌县",
			"SPELLING" : "Xunwu"
		},
		{
			"ID" : "360735",
			"PARENT_ID" : "360700",
			"DISTRICT_NAME" : "石城县",
			"SPELLING" : "Shicheng"
		},
		{
			"ID" : "360781",
			"PARENT_ID" : "360700",
			"DISTRICT_NAME" : "瑞金市",
			"SPELLING" : "Ruijin"
		},
		{
			"ID" : "360800",
			"PARENT_ID" : "360000",
			"DISTRICT_NAME" : "吉安市",
			"SPELLING" : "Jian"
		},
		{
			"ID" : "360802",
			"PARENT_ID" : "360800",
			"DISTRICT_NAME" : "吉州区",
			"SPELLING" : "Jizhou"
		},
		{
			"ID" : "360803",
			"PARENT_ID" : "360800",
			"DISTRICT_NAME" : "青原区",
			"SPELLING" : "Qingyuan"
		},
		{
			"ID" : "360821",
			"PARENT_ID" : "360800",
			"DISTRICT_NAME" : "吉安县",
			"SPELLING" : "Jian"
		},
		{
			"ID" : "360822",
			"PARENT_ID" : "360800",
			"DISTRICT_NAME" : "吉水县",
			"SPELLING" : "Jishui"
		},
		{
			"ID" : "360823",
			"PARENT_ID" : "360800",
			"DISTRICT_NAME" : "峡江县",
			"SPELLING" : "Xiajiang"
		},
		{
			"ID" : "360824",
			"PARENT_ID" : "360800",
			"DISTRICT_NAME" : "新干县",
			"SPELLING" : "Xingan"
		},
		{
			"ID" : "360825",
			"PARENT_ID" : "360800",
			"DISTRICT_NAME" : "永丰县",
			"SPELLING" : "Yongfeng"
		},
		{
			"ID" : "360826",
			"PARENT_ID" : "360800",
			"DISTRICT_NAME" : "泰和县",
			"SPELLING" : "Taihe"
		},
		{
			"ID" : "360827",
			"PARENT_ID" : "360800",
			"DISTRICT_NAME" : "遂川县",
			"SPELLING" : "Suichuan"
		},
		{
			"ID" : "360828",
			"PARENT_ID" : "360800",
			"DISTRICT_NAME" : "万安县",
			"SPELLING" : "Wanan"
		},
		{
			"ID" : "360829",
			"PARENT_ID" : "360800",
			"DISTRICT_NAME" : "安福县",
			"SPELLING" : "Anfu"
		},
		{
			"ID" : "360830",
			"PARENT_ID" : "360800",
			"DISTRICT_NAME" : "永新县",
			"SPELLING" : "Yongxin"
		},
		{
			"ID" : "360881",
			"PARENT_ID" : "360800",
			"DISTRICT_NAME" : "井冈山市",
			"SPELLING" : "Jinggangshan"
		},
		{
			"ID" : "360900",
			"PARENT_ID" : "360000",
			"DISTRICT_NAME" : "宜春市",
			"SPELLING" : "Yichun"
		},
		{
			"ID" : "360902",
			"PARENT_ID" : "360900",
			"DISTRICT_NAME" : "袁州区",
			"SPELLING" : "Yuanzhou"
		},
		{
			"ID" : "360921",
			"PARENT_ID" : "360900",
			"DISTRICT_NAME" : "奉新县",
			"SPELLING" : "Fengxin"
		},
		{
			"ID" : "360922",
			"PARENT_ID" : "360900",
			"DISTRICT_NAME" : "万载县",
			"SPELLING" : "Wanzai"
		},
		{
			"ID" : "360923",
			"PARENT_ID" : "360900",
			"DISTRICT_NAME" : "上高县",
			"SPELLING" : "Shanggao"
		},
		{
			"ID" : "360924",
			"PARENT_ID" : "360900",
			"DISTRICT_NAME" : "宜丰县",
			"SPELLING" : "Yifeng"
		},
		{
			"ID" : "360925",
			"PARENT_ID" : "360900",
			"DISTRICT_NAME" : "靖安县",
			"SPELLING" : "Jingan"
		},
		{
			"ID" : "360926",
			"PARENT_ID" : "360900",
			"DISTRICT_NAME" : "铜鼓县",
			"SPELLING" : "Tonggu"
		},
		{
			"ID" : "360981",
			"PARENT_ID" : "360900",
			"DISTRICT_NAME" : "丰城市",
			"SPELLING" : "Fengcheng"
		},
		{
			"ID" : "360982",
			"PARENT_ID" : "360900",
			"DISTRICT_NAME" : "樟树市",
			"SPELLING" : "Zhangshu"
		},
		{
			"ID" : "360983",
			"PARENT_ID" : "360900",
			"DISTRICT_NAME" : "高安市",
			"SPELLING" : "Gaoan"
		},
		{
			"ID" : "361000",
			"PARENT_ID" : "360000",
			"DISTRICT_NAME" : "抚州市",
			"SPELLING" : "Fuzhou"
		},
		{
			"ID" : "361002",
			"PARENT_ID" : "361000",
			"DISTRICT_NAME" : "临川区",
			"SPELLING" : "Linchuan"
		},
		{
			"ID" : "361021",
			"PARENT_ID" : "361000",
			"DISTRICT_NAME" : "南城县",
			"SPELLING" : "Nancheng"
		},
		{
			"ID" : "361022",
			"PARENT_ID" : "361000",
			"DISTRICT_NAME" : "黎川县",
			"SPELLING" : "Lichuan"
		},
		{
			"ID" : "361023",
			"PARENT_ID" : "361000",
			"DISTRICT_NAME" : "南丰县",
			"SPELLING" : "Nanfeng"
		},
		{
			"ID" : "361024",
			"PARENT_ID" : "361000",
			"DISTRICT_NAME" : "崇仁县",
			"SPELLING" : "Chongren"
		},
		{
			"ID" : "361025",
			"PARENT_ID" : "361000",
			"DISTRICT_NAME" : "乐安县",
			"SPELLING" : "Lean"
		},
		{
			"ID" : "361026",
			"PARENT_ID" : "361000",
			"DISTRICT_NAME" : "宜黄县",
			"SPELLING" : "Yihuang"
		},
		{
			"ID" : "361027",
			"PARENT_ID" : "361000",
			"DISTRICT_NAME" : "金溪县",
			"SPELLING" : "Jinxi"
		},
		{
			"ID" : "361028",
			"PARENT_ID" : "361000",
			"DISTRICT_NAME" : "资溪县",
			"SPELLING" : "Zixi"
		},
		{
			"ID" : "361029",
			"PARENT_ID" : "361000",
			"DISTRICT_NAME" : "东乡县",
			"SPELLING" : "Dongxiang"
		},
		{
			"ID" : "361030",
			"PARENT_ID" : "361000",
			"DISTRICT_NAME" : "广昌县",
			"SPELLING" : "Guangchang"
		},
		{
			"ID" : "361100",
			"PARENT_ID" : "360000",
			"DISTRICT_NAME" : "上饶市",
			"SPELLING" : "Shangrao"
		},
		{
			"ID" : "361102",
			"PARENT_ID" : "361100",
			"DISTRICT_NAME" : "信州区",
			"SPELLING" : "Xinzhou"
		},
		{
			"ID" : "361121",
			"PARENT_ID" : "361100",
			"DISTRICT_NAME" : "上饶县",
			"SPELLING" : "Shangrao"
		},
		{
			"ID" : "361122",
			"PARENT_ID" : "361100",
			"DISTRICT_NAME" : "广丰县",
			"SPELLING" : "Guangfeng"
		},
		{
			"ID" : "361123",
			"PARENT_ID" : "361100",
			"DISTRICT_NAME" : "玉山县",
			"SPELLING" : "Yushan"
		},
		{
			"ID" : "361124",
			"PARENT_ID" : "361100",
			"DISTRICT_NAME" : "铅山县",
			"SPELLING" : "Yanshan"
		},
		{
			"ID" : "361125",
			"PARENT_ID" : "361100",
			"DISTRICT_NAME" : "横峰县",
			"SPELLING" : "Hengfeng"
		},
		{
			"ID" : "361126",
			"PARENT_ID" : "361100",
			"DISTRICT_NAME" : "弋阳县",
			"SPELLING" : "Yiyang"
		},
		{
			"ID" : "361127",
			"PARENT_ID" : "361100",
			"DISTRICT_NAME" : "余干县",
			"SPELLING" : "Yugan"
		},
		{
			"ID" : "361128",
			"PARENT_ID" : "361100",
			"DISTRICT_NAME" : "鄱阳县",
			"SPELLING" : "Poyang"
		},
		{
			"ID" : "361129",
			"PARENT_ID" : "361100",
			"DISTRICT_NAME" : "万年县",
			"SPELLING" : "Wannian"
		},
		{
			"ID" : "361130",
			"PARENT_ID" : "361100",
			"DISTRICT_NAME" : "婺源县",
			"SPELLING" : "Wuyuan"
		},
		{
			"ID" : "361181",
			"PARENT_ID" : "361100",
			"DISTRICT_NAME" : "德兴市",
			"SPELLING" : "Dexing"
		},
		{
			"ID" : "370000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "山东省",
			"SPELLING" : "Shandong"
		},
		{
			"ID" : "370100",
			"PARENT_ID" : "370000",
			"DISTRICT_NAME" : "济南市",
			"SPELLING" : "Jinan"
		},
		{
			"ID" : "370102",
			"PARENT_ID" : "370100",
			"DISTRICT_NAME" : "历下区",
			"SPELLING" : "Lixia"
		},
		{
			"ID" : "370103",
			"PARENT_ID" : "370100",
			"DISTRICT_NAME" : "市中区",
			"SPELLING" : "Shizhongqu"
		},
		{
			"ID" : "370104",
			"PARENT_ID" : "370100",
			"DISTRICT_NAME" : "槐荫区",
			"SPELLING" : "Huaiyin"
		},
		{
			"ID" : "370105",
			"PARENT_ID" : "370100",
			"DISTRICT_NAME" : "天桥区",
			"SPELLING" : "Tianqiao"
		},
		{
			"ID" : "370112",
			"PARENT_ID" : "370100",
			"DISTRICT_NAME" : "历城区",
			"SPELLING" : "Licheng"
		},
		{
			"ID" : "370113",
			"PARENT_ID" : "370100",
			"DISTRICT_NAME" : "长清区",
			"SPELLING" : "Changqing"
		},
		{
			"ID" : "370124",
			"PARENT_ID" : "370100",
			"DISTRICT_NAME" : "平阴县",
			"SPELLING" : "Pingyin"
		},
		{
			"ID" : "370125",
			"PARENT_ID" : "370100",
			"DISTRICT_NAME" : "济阳县",
			"SPELLING" : "Jiyang"
		},
		{
			"ID" : "370126",
			"PARENT_ID" : "370100",
			"DISTRICT_NAME" : "商河县",
			"SPELLING" : "Shanghe"
		},
		{
			"ID" : "370181",
			"PARENT_ID" : "370100",
			"DISTRICT_NAME" : "章丘市",
			"SPELLING" : "Zhangqiu"
		},
		{
			"ID" : "370200",
			"PARENT_ID" : "370000",
			"DISTRICT_NAME" : "青岛市",
			"SPELLING" : "Qingdao"
		},
		{
			"ID" : "370202",
			"PARENT_ID" : "370200",
			"DISTRICT_NAME" : "市南区",
			"SPELLING" : "Shinan"
		},
		{
			"ID" : "370203",
			"PARENT_ID" : "370200",
			"DISTRICT_NAME" : "市北区",
			"SPELLING" : "Shibei"
		},
		{
			"ID" : "370211",
			"PARENT_ID" : "370200",
			"DISTRICT_NAME" : "黄岛区",
			"SPELLING" : "Huangdao"
		},
		{
			"ID" : "370212",
			"PARENT_ID" : "370200",
			"DISTRICT_NAME" : "崂山区",
			"SPELLING" : "Laoshan"
		},
		{
			"ID" : "370213",
			"PARENT_ID" : "370200",
			"DISTRICT_NAME" : "李沧区",
			"SPELLING" : "Licang"
		},
		{
			"ID" : "370214",
			"PARENT_ID" : "370200",
			"DISTRICT_NAME" : "城阳区",
			"SPELLING" : "Chengyang"
		},
		{
			"ID" : "370281",
			"PARENT_ID" : "370200",
			"DISTRICT_NAME" : "胶州市",
			"SPELLING" : "Jiaozhou"
		},
		{
			"ID" : "370282",
			"PARENT_ID" : "370200",
			"DISTRICT_NAME" : "即墨市",
			"SPELLING" : "Jimo"
		},
		{
			"ID" : "370283",
			"PARENT_ID" : "370200",
			"DISTRICT_NAME" : "平度市",
			"SPELLING" : "Pingdu"
		},
		{
			"ID" : "370285",
			"PARENT_ID" : "370200",
			"DISTRICT_NAME" : "莱西市",
			"SPELLING" : "Laixi"
		},
		{
			"ID" : "370286",
			"PARENT_ID" : "370200",
			"DISTRICT_NAME" : "西海岸新区",
			"SPELLING" : "Xihaian"
		},
		{
			"ID" : "370300",
			"PARENT_ID" : "370000",
			"DISTRICT_NAME" : "淄博市",
			"SPELLING" : "Zibo"
		},
		{
			"ID" : "370302",
			"PARENT_ID" : "370300",
			"DISTRICT_NAME" : "淄川区",
			"SPELLING" : "Zichuan"
		},
		{
			"ID" : "370303",
			"PARENT_ID" : "370300",
			"DISTRICT_NAME" : "张店区",
			"SPELLING" : "Zhangdian"
		},
		{
			"ID" : "370304",
			"PARENT_ID" : "370300",
			"DISTRICT_NAME" : "博山区",
			"SPELLING" : "Boshan"
		},
		{
			"ID" : "370305",
			"PARENT_ID" : "370300",
			"DISTRICT_NAME" : "临淄区",
			"SPELLING" : "Linzi"
		},
		{
			"ID" : "370306",
			"PARENT_ID" : "370300",
			"DISTRICT_NAME" : "周村区",
			"SPELLING" : "Zhoucun"
		},
		{
			"ID" : "370321",
			"PARENT_ID" : "370300",
			"DISTRICT_NAME" : "桓台县",
			"SPELLING" : "Huantai"
		},
		{
			"ID" : "370322",
			"PARENT_ID" : "370300",
			"DISTRICT_NAME" : "高青县",
			"SPELLING" : "Gaoqing"
		},
		{
			"ID" : "370323",
			"PARENT_ID" : "370300",
			"DISTRICT_NAME" : "沂源县",
			"SPELLING" : "Yiyuan"
		},
		{
			"ID" : "370400",
			"PARENT_ID" : "370000",
			"DISTRICT_NAME" : "枣庄市",
			"SPELLING" : "Zaozhuang"
		},
		{
			"ID" : "370402",
			"PARENT_ID" : "370400",
			"DISTRICT_NAME" : "市中区",
			"SPELLING" : "Shizhongqu"
		},
		{
			"ID" : "370403",
			"PARENT_ID" : "370400",
			"DISTRICT_NAME" : "薛城区",
			"SPELLING" : "Xuecheng"
		},
		{
			"ID" : "370404",
			"PARENT_ID" : "370400",
			"DISTRICT_NAME" : "峄城区",
			"SPELLING" : "Yicheng"
		},
		{
			"ID" : "370405",
			"PARENT_ID" : "370400",
			"DISTRICT_NAME" : "台儿庄区",
			"SPELLING" : "Taierzhuang"
		},
		{
			"ID" : "370406",
			"PARENT_ID" : "370400",
			"DISTRICT_NAME" : "山亭区",
			"SPELLING" : "Shanting"
		},
		{
			"ID" : "370481",
			"PARENT_ID" : "370400",
			"DISTRICT_NAME" : "滕州市",
			"SPELLING" : "Tengzhou"
		},
		{
			"ID" : "370500",
			"PARENT_ID" : "370000",
			"DISTRICT_NAME" : "东营市",
			"SPELLING" : "Dongying"
		},
		{
			"ID" : "370502",
			"PARENT_ID" : "370500",
			"DISTRICT_NAME" : "东营区",
			"SPELLING" : "Dongying"
		},
		{
			"ID" : "370503",
			"PARENT_ID" : "370500",
			"DISTRICT_NAME" : "河口区",
			"SPELLING" : "Hekou"
		},
		{
			"ID" : "370521",
			"PARENT_ID" : "370500",
			"DISTRICT_NAME" : "垦利县",
			"SPELLING" : "Kenli"
		},
		{
			"ID" : "370522",
			"PARENT_ID" : "370500",
			"DISTRICT_NAME" : "利津县",
			"SPELLING" : "Lijin"
		},
		{
			"ID" : "370523",
			"PARENT_ID" : "370500",
			"DISTRICT_NAME" : "广饶县",
			"SPELLING" : "Guangrao"
		},
		{
			"ID" : "370600",
			"PARENT_ID" : "370000",
			"DISTRICT_NAME" : "烟台市",
			"SPELLING" : "Yantai"
		},
		{
			"ID" : "370602",
			"PARENT_ID" : "370600",
			"DISTRICT_NAME" : "芝罘区",
			"SPELLING" : "Zhifu"
		},
		{
			"ID" : "370611",
			"PARENT_ID" : "370600",
			"DISTRICT_NAME" : "福山区",
			"SPELLING" : "Fushan"
		},
		{
			"ID" : "370612",
			"PARENT_ID" : "370600",
			"DISTRICT_NAME" : "牟平区",
			"SPELLING" : "Muping"
		},
		{
			"ID" : "370613",
			"PARENT_ID" : "370600",
			"DISTRICT_NAME" : "莱山区",
			"SPELLING" : "Laishan"
		},
		{
			"ID" : "370634",
			"PARENT_ID" : "370600",
			"DISTRICT_NAME" : "长岛县",
			"SPELLING" : "Changdao"
		},
		{
			"ID" : "370681",
			"PARENT_ID" : "370600",
			"DISTRICT_NAME" : "龙口市",
			"SPELLING" : "Longkou"
		},
		{
			"ID" : "370682",
			"PARENT_ID" : "370600",
			"DISTRICT_NAME" : "莱阳市",
			"SPELLING" : "Laiyang"
		},
		{
			"ID" : "370683",
			"PARENT_ID" : "370600",
			"DISTRICT_NAME" : "莱州市",
			"SPELLING" : "Laizhou"
		},
		{
			"ID" : "370684",
			"PARENT_ID" : "370600",
			"DISTRICT_NAME" : "蓬莱市",
			"SPELLING" : "Penglai"
		},
		{
			"ID" : "370685",
			"PARENT_ID" : "370600",
			"DISTRICT_NAME" : "招远市",
			"SPELLING" : "Zhaoyuan"
		},
		{
			"ID" : "370686",
			"PARENT_ID" : "370600",
			"DISTRICT_NAME" : "栖霞市",
			"SPELLING" : "Qixia"
		},
		{
			"ID" : "370687",
			"PARENT_ID" : "370600",
			"DISTRICT_NAME" : "海阳市",
			"SPELLING" : "Haiyang"
		},
		{
			"ID" : "370700",
			"PARENT_ID" : "370000",
			"DISTRICT_NAME" : "潍坊市",
			"SPELLING" : "Weifang"
		},
		{
			"ID" : "370702",
			"PARENT_ID" : "370700",
			"DISTRICT_NAME" : "潍城区",
			"SPELLING" : "Weicheng"
		},
		{
			"ID" : "370703",
			"PARENT_ID" : "370700",
			"DISTRICT_NAME" : "寒亭区",
			"SPELLING" : "Hanting"
		},
		{
			"ID" : "370704",
			"PARENT_ID" : "370700",
			"DISTRICT_NAME" : "坊子区",
			"SPELLING" : "Fangzi"
		},
		{
			"ID" : "370705",
			"PARENT_ID" : "370700",
			"DISTRICT_NAME" : "奎文区",
			"SPELLING" : "Kuiwen"
		},
		{
			"ID" : "370724",
			"PARENT_ID" : "370700",
			"DISTRICT_NAME" : "临朐县",
			"SPELLING" : "Linqu"
		},
		{
			"ID" : "370725",
			"PARENT_ID" : "370700",
			"DISTRICT_NAME" : "昌乐县",
			"SPELLING" : "Changle"
		},
		{
			"ID" : "370781",
			"PARENT_ID" : "370700",
			"DISTRICT_NAME" : "青州市",
			"SPELLING" : "Qingzhou"
		},
		{
			"ID" : "370782",
			"PARENT_ID" : "370700",
			"DISTRICT_NAME" : "诸城市",
			"SPELLING" : "Zhucheng"
		},
		{
			"ID" : "370783",
			"PARENT_ID" : "370700",
			"DISTRICT_NAME" : "寿光市",
			"SPELLING" : "Shouguang"
		},
		{
			"ID" : "370784",
			"PARENT_ID" : "370700",
			"DISTRICT_NAME" : "安丘市",
			"SPELLING" : "Anqiu"
		},
		{
			"ID" : "370785",
			"PARENT_ID" : "370700",
			"DISTRICT_NAME" : "高密市",
			"SPELLING" : "Gaomi"
		},
		{
			"ID" : "370786",
			"PARENT_ID" : "370700",
			"DISTRICT_NAME" : "昌邑市",
			"SPELLING" : "Changyi"
		},
		{
			"ID" : "370800",
			"PARENT_ID" : "370000",
			"DISTRICT_NAME" : "济宁市",
			"SPELLING" : "Jining"
		},
		{
			"ID" : "370811",
			"PARENT_ID" : "370800",
			"DISTRICT_NAME" : "任城区",
			"SPELLING" : "Rencheng"
		},
		{
			"ID" : "370812",
			"PARENT_ID" : "370800",
			"DISTRICT_NAME" : "兖州区",
			"SPELLING" : "Yanzhou "
		},
		{
			"ID" : "370826",
			"PARENT_ID" : "370800",
			"DISTRICT_NAME" : "微山县",
			"SPELLING" : "Weishan"
		},
		{
			"ID" : "370827",
			"PARENT_ID" : "370800",
			"DISTRICT_NAME" : "鱼台县",
			"SPELLING" : "Yutai"
		},
		{
			"ID" : "370828",
			"PARENT_ID" : "370800",
			"DISTRICT_NAME" : "金乡县",
			"SPELLING" : "Jinxiang"
		},
		{
			"ID" : "370829",
			"PARENT_ID" : "370800",
			"DISTRICT_NAME" : "嘉祥县",
			"SPELLING" : "Jiaxiang"
		},
		{
			"ID" : "370830",
			"PARENT_ID" : "370800",
			"DISTRICT_NAME" : "汶上县",
			"SPELLING" : "Wenshang"
		},
		{
			"ID" : "370831",
			"PARENT_ID" : "370800",
			"DISTRICT_NAME" : "泗水县",
			"SPELLING" : "Sishui"
		},
		{
			"ID" : "370832",
			"PARENT_ID" : "370800",
			"DISTRICT_NAME" : "梁山县",
			"SPELLING" : "Liangshan"
		},
		{
			"ID" : "370881",
			"PARENT_ID" : "370800",
			"DISTRICT_NAME" : "曲阜市",
			"SPELLING" : "Qufu"
		},
		{
			"ID" : "370883",
			"PARENT_ID" : "370800",
			"DISTRICT_NAME" : "邹城市",
			"SPELLING" : "Zoucheng"
		},
		{
			"ID" : "370900",
			"PARENT_ID" : "370000",
			"DISTRICT_NAME" : "泰安市",
			"SPELLING" : "Taian"
		},
		{
			"ID" : "370902",
			"PARENT_ID" : "370900",
			"DISTRICT_NAME" : "泰山区",
			"SPELLING" : "Taishan"
		},
		{
			"ID" : "370911",
			"PARENT_ID" : "370900",
			"DISTRICT_NAME" : "岱岳区",
			"SPELLING" : "Daiyue"
		},
		{
			"ID" : "370921",
			"PARENT_ID" : "370900",
			"DISTRICT_NAME" : "宁阳县",
			"SPELLING" : "Ningyang"
		},
		{
			"ID" : "370923",
			"PARENT_ID" : "370900",
			"DISTRICT_NAME" : "东平县",
			"SPELLING" : "Dongping"
		},
		{
			"ID" : "370982",
			"PARENT_ID" : "370900",
			"DISTRICT_NAME" : "新泰市",
			"SPELLING" : "Xintai"
		},
		{
			"ID" : "370983",
			"PARENT_ID" : "370900",
			"DISTRICT_NAME" : "肥城市",
			"SPELLING" : "Feicheng"
		},
		{
			"ID" : "371000",
			"PARENT_ID" : "370000",
			"DISTRICT_NAME" : "威海市",
			"SPELLING" : "Weihai"
		},
		{
			"ID" : "371002",
			"PARENT_ID" : "371000",
			"DISTRICT_NAME" : "环翠区",
			"SPELLING" : "Huancui"
		},
		{
			"ID" : "371003",
			"PARENT_ID" : "371000",
			"DISTRICT_NAME" : "文登区",
			"SPELLING" : "Wendeng"
		},
		{
			"ID" : "371082",
			"PARENT_ID" : "371000",
			"DISTRICT_NAME" : "荣成市",
			"SPELLING" : "Rongcheng"
		},
		{
			"ID" : "371083",
			"PARENT_ID" : "371000",
			"DISTRICT_NAME" : "乳山市",
			"SPELLING" : "Rushan"
		},
		{
			"ID" : "371100",
			"PARENT_ID" : "370000",
			"DISTRICT_NAME" : "日照市",
			"SPELLING" : "Rizhao"
		},
		{
			"ID" : "371102",
			"PARENT_ID" : "371100",
			"DISTRICT_NAME" : "东港区",
			"SPELLING" : "Donggang"
		},
		{
			"ID" : "371103",
			"PARENT_ID" : "371100",
			"DISTRICT_NAME" : "岚山区",
			"SPELLING" : "Lanshan"
		},
		{
			"ID" : "371121",
			"PARENT_ID" : "371100",
			"DISTRICT_NAME" : "五莲县",
			"SPELLING" : "Wulian"
		},
		{
			"ID" : "371122",
			"PARENT_ID" : "371100",
			"DISTRICT_NAME" : "莒县",
			"SPELLING" : "Juxian"
		},
		{
			"ID" : "371200",
			"PARENT_ID" : "370000",
			"DISTRICT_NAME" : "莱芜市",
			"SPELLING" : "Laiwu"
		},
		{
			"ID" : "371202",
			"PARENT_ID" : "371200",
			"DISTRICT_NAME" : "莱城区",
			"SPELLING" : "Laicheng"
		},
		{
			"ID" : "371203",
			"PARENT_ID" : "371200",
			"DISTRICT_NAME" : "钢城区",
			"SPELLING" : "Gangcheng"
		},
		{
			"ID" : "371300",
			"PARENT_ID" : "370000",
			"DISTRICT_NAME" : "临沂市",
			"SPELLING" : "Linyi"
		},
		{
			"ID" : "371302",
			"PARENT_ID" : "371300",
			"DISTRICT_NAME" : "兰山区",
			"SPELLING" : "Lanshan"
		},
		{
			"ID" : "371311",
			"PARENT_ID" : "371300",
			"DISTRICT_NAME" : "罗庄区",
			"SPELLING" : "Luozhuang"
		},
		{
			"ID" : "371312",
			"PARENT_ID" : "371300",
			"DISTRICT_NAME" : "河东区",
			"SPELLING" : "Hedong"
		},
		{
			"ID" : "371321",
			"PARENT_ID" : "371300",
			"DISTRICT_NAME" : "沂南县",
			"SPELLING" : "Yinan"
		},
		{
			"ID" : "371322",
			"PARENT_ID" : "371300",
			"DISTRICT_NAME" : "郯城县",
			"SPELLING" : "Tancheng"
		},
		{
			"ID" : "371323",
			"PARENT_ID" : "371300",
			"DISTRICT_NAME" : "沂水县",
			"SPELLING" : "Yishui"
		},
		{
			"ID" : "371324",
			"PARENT_ID" : "371300",
			"DISTRICT_NAME" : "兰陵县",
			"SPELLING" : "Lanling"
		},
		{
			"ID" : "371325",
			"PARENT_ID" : "371300",
			"DISTRICT_NAME" : "费县",
			"SPELLING" : "Feixian"
		},
		{
			"ID" : "371326",
			"PARENT_ID" : "371300",
			"DISTRICT_NAME" : "平邑县",
			"SPELLING" : "Pingyi"
		},
		{
			"ID" : "371327",
			"PARENT_ID" : "371300",
			"DISTRICT_NAME" : "莒南县",
			"SPELLING" : "Junan"
		},
		{
			"ID" : "371328",
			"PARENT_ID" : "371300",
			"DISTRICT_NAME" : "蒙阴县",
			"SPELLING" : "Mengyin"
		},
		{
			"ID" : "371329",
			"PARENT_ID" : "371300",
			"DISTRICT_NAME" : "临沭县",
			"SPELLING" : "Linshu"
		},
		{
			"ID" : "371400",
			"PARENT_ID" : "370000",
			"DISTRICT_NAME" : "德州市",
			"SPELLING" : "Dezhou"
		},
		{
			"ID" : "371402",
			"PARENT_ID" : "371400",
			"DISTRICT_NAME" : "德城区",
			"SPELLING" : "Decheng"
		},
		{
			"ID" : "371403",
			"PARENT_ID" : "371400",
			"DISTRICT_NAME" : "陵城区",
			"SPELLING" : "Lingcheng"
		},
		{
			"ID" : "371422",
			"PARENT_ID" : "371400",
			"DISTRICT_NAME" : "宁津县",
			"SPELLING" : "Ningjin"
		},
		{
			"ID" : "371423",
			"PARENT_ID" : "371400",
			"DISTRICT_NAME" : "庆云县",
			"SPELLING" : "Qingyun"
		},
		{
			"ID" : "371424",
			"PARENT_ID" : "371400",
			"DISTRICT_NAME" : "临邑县",
			"SPELLING" : "Linyi"
		},
		{
			"ID" : "371425",
			"PARENT_ID" : "371400",
			"DISTRICT_NAME" : "齐河县",
			"SPELLING" : "Qihe"
		},
		{
			"ID" : "371426",
			"PARENT_ID" : "371400",
			"DISTRICT_NAME" : "平原县",
			"SPELLING" : "Pingyuan"
		},
		{
			"ID" : "371427",
			"PARENT_ID" : "371400",
			"DISTRICT_NAME" : "夏津县",
			"SPELLING" : "Xiajin"
		},
		{
			"ID" : "371428",
			"PARENT_ID" : "371400",
			"DISTRICT_NAME" : "武城县",
			"SPELLING" : "Wucheng"
		},
		{
			"ID" : "371481",
			"PARENT_ID" : "371400",
			"DISTRICT_NAME" : "乐陵市",
			"SPELLING" : "Leling"
		},
		{
			"ID" : "371482",
			"PARENT_ID" : "371400",
			"DISTRICT_NAME" : "禹城市",
			"SPELLING" : "Yucheng"
		},
		{
			"ID" : "371500",
			"PARENT_ID" : "370000",
			"DISTRICT_NAME" : "聊城市",
			"SPELLING" : "Liaocheng"
		},
		{
			"ID" : "371502",
			"PARENT_ID" : "371500",
			"DISTRICT_NAME" : "东昌府区",
			"SPELLING" : "Dongchangfu"
		},
		{
			"ID" : "371521",
			"PARENT_ID" : "371500",
			"DISTRICT_NAME" : "阳谷县",
			"SPELLING" : "Yanggu"
		},
		{
			"ID" : "371522",
			"PARENT_ID" : "371500",
			"DISTRICT_NAME" : "莘县",
			"SPELLING" : "Shenxian"
		},
		{
			"ID" : "371523",
			"PARENT_ID" : "371500",
			"DISTRICT_NAME" : "茌平县",
			"SPELLING" : "Chiping"
		},
		{
			"ID" : "371524",
			"PARENT_ID" : "371500",
			"DISTRICT_NAME" : "东阿县",
			"SPELLING" : "Donge"
		},
		{
			"ID" : "371525",
			"PARENT_ID" : "371500",
			"DISTRICT_NAME" : "冠县",
			"SPELLING" : "Guanxian"
		},
		{
			"ID" : "371526",
			"PARENT_ID" : "371500",
			"DISTRICT_NAME" : "高唐县",
			"SPELLING" : "Gaotang"
		},
		{
			"ID" : "371581",
			"PARENT_ID" : "371500",
			"DISTRICT_NAME" : "临清市",
			"SPELLING" : "Linqing"
		},
		{
			"ID" : "371600",
			"PARENT_ID" : "370000",
			"DISTRICT_NAME" : "滨州市",
			"SPELLING" : "Binzhou"
		},
		{
			"ID" : "371602",
			"PARENT_ID" : "371600",
			"DISTRICT_NAME" : "滨城区",
			"SPELLING" : "Bincheng"
		},
		{
			"ID" : "371603",
			"PARENT_ID" : "371600",
			"DISTRICT_NAME" : "沾化区",
			"SPELLING" : "Zhanhua"
		},
		{
			"ID" : "371621",
			"PARENT_ID" : "371600",
			"DISTRICT_NAME" : "惠民县",
			"SPELLING" : "Huimin"
		},
		{
			"ID" : "371622",
			"PARENT_ID" : "371600",
			"DISTRICT_NAME" : "阳信县",
			"SPELLING" : "Yangxin"
		},
		{
			"ID" : "371623",
			"PARENT_ID" : "371600",
			"DISTRICT_NAME" : "无棣县",
			"SPELLING" : "Wudi"
		},
		{
			"ID" : "371625",
			"PARENT_ID" : "371600",
			"DISTRICT_NAME" : "博兴县",
			"SPELLING" : "Boxing"
		},
		{
			"ID" : "371626",
			"PARENT_ID" : "371600",
			"DISTRICT_NAME" : "邹平县",
			"SPELLING" : "Zouping"
		},
		{
			"ID" : "371627",
			"PARENT_ID" : "371600",
			"DISTRICT_NAME" : "北海新区",
			"SPELLING" : "Beihaixinqu"
		},
		{
			"ID" : "371700",
			"PARENT_ID" : "370000",
			"DISTRICT_NAME" : "菏泽市",
			"SPELLING" : "Heze"
		},
		{
			"ID" : "371702",
			"PARENT_ID" : "371700",
			"DISTRICT_NAME" : "牡丹区",
			"SPELLING" : "Mudan"
		},
		{
			"ID" : "371721",
			"PARENT_ID" : "371700",
			"DISTRICT_NAME" : "曹县",
			"SPELLING" : "Caoxian"
		},
		{
			"ID" : "371722",
			"PARENT_ID" : "371700",
			"DISTRICT_NAME" : "单县",
			"SPELLING" : "Shanxian"
		},
		{
			"ID" : "371723",
			"PARENT_ID" : "371700",
			"DISTRICT_NAME" : "成武县",
			"SPELLING" : "Chengwu"
		},
		{
			"ID" : "371724",
			"PARENT_ID" : "371700",
			"DISTRICT_NAME" : "巨野县",
			"SPELLING" : "Juye"
		},
		{
			"ID" : "371725",
			"PARENT_ID" : "371700",
			"DISTRICT_NAME" : "郓城县",
			"SPELLING" : "Yuncheng"
		},
		{
			"ID" : "371726",
			"PARENT_ID" : "371700",
			"DISTRICT_NAME" : "鄄城县",
			"SPELLING" : "Juancheng"
		},
		{
			"ID" : "371727",
			"PARENT_ID" : "371700",
			"DISTRICT_NAME" : "定陶县",
			"SPELLING" : "Dingtao"
		},
		{
			"ID" : "371728",
			"PARENT_ID" : "371700",
			"DISTRICT_NAME" : "东明县",
			"SPELLING" : "Dongming"
		},
		{
			"ID" : "410000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "河南省",
			"SPELLING" : "Henan"
		},
		{
			"ID" : "410100",
			"PARENT_ID" : "410000",
			"DISTRICT_NAME" : "郑州市",
			"SPELLING" : "Zhengzhou"
		},
		{
			"ID" : "410102",
			"PARENT_ID" : "410100",
			"DISTRICT_NAME" : "中原区",
			"SPELLING" : "Zhongyuan"
		},
		{
			"ID" : "410103",
			"PARENT_ID" : "410100",
			"DISTRICT_NAME" : "二七区",
			"SPELLING" : "Erqi"
		},
		{
			"ID" : "410104",
			"PARENT_ID" : "410100",
			"DISTRICT_NAME" : "管城回族区",
			"SPELLING" : "Guancheng"
		},
		{
			"ID" : "410105",
			"PARENT_ID" : "410100",
			"DISTRICT_NAME" : "金水区",
			"SPELLING" : "Jinshui"
		},
		{
			"ID" : "410106",
			"PARENT_ID" : "410100",
			"DISTRICT_NAME" : "上街区",
			"SPELLING" : "Shangjie"
		},
		{
			"ID" : "410108",
			"PARENT_ID" : "410100",
			"DISTRICT_NAME" : "惠济区",
			"SPELLING" : "Huiji"
		},
		{
			"ID" : "410122",
			"PARENT_ID" : "410100",
			"DISTRICT_NAME" : "中牟县",
			"SPELLING" : "Zhongmu"
		},
		{
			"ID" : "410181",
			"PARENT_ID" : "410100",
			"DISTRICT_NAME" : "巩义市",
			"SPELLING" : "Gongyi"
		},
		{
			"ID" : "410182",
			"PARENT_ID" : "410100",
			"DISTRICT_NAME" : "荥阳市",
			"SPELLING" : "Xingyang"
		},
		{
			"ID" : "410183",
			"PARENT_ID" : "410100",
			"DISTRICT_NAME" : "新密市",
			"SPELLING" : "Xinmi"
		},
		{
			"ID" : "410184",
			"PARENT_ID" : "410100",
			"DISTRICT_NAME" : "新郑市",
			"SPELLING" : "Xinzheng"
		},
		{
			"ID" : "410185",
			"PARENT_ID" : "410100",
			"DISTRICT_NAME" : "登封市",
			"SPELLING" : "Dengfeng"
		},
		{
			"ID" : "410200",
			"PARENT_ID" : "410000",
			"DISTRICT_NAME" : "开封市",
			"SPELLING" : "Kaifeng"
		},
		{
			"ID" : "410202",
			"PARENT_ID" : "410200",
			"DISTRICT_NAME" : "龙亭区",
			"SPELLING" : "Longting"
		},
		{
			"ID" : "410203",
			"PARENT_ID" : "410200",
			"DISTRICT_NAME" : "顺河回族区",
			"SPELLING" : "Shunhe"
		},
		{
			"ID" : "410204",
			"PARENT_ID" : "410200",
			"DISTRICT_NAME" : "鼓楼区",
			"SPELLING" : "Gulou"
		},
		{
			"ID" : "410205",
			"PARENT_ID" : "410200",
			"DISTRICT_NAME" : "禹王台区",
			"SPELLING" : "Yuwangtai"
		},
		{
			"ID" : "410212",
			"PARENT_ID" : "410200",
			"DISTRICT_NAME" : "祥符区",
			"SPELLING" : "Xiangfu"
		},
		{
			"ID" : "410221",
			"PARENT_ID" : "410200",
			"DISTRICT_NAME" : "杞县",
			"SPELLING" : "Qixian"
		},
		{
			"ID" : "410222",
			"PARENT_ID" : "410200",
			"DISTRICT_NAME" : "通许县",
			"SPELLING" : "Tongxu"
		},
		{
			"ID" : "410223",
			"PARENT_ID" : "410200",
			"DISTRICT_NAME" : "尉氏县",
			"SPELLING" : "Weishi"
		},
		{
			"ID" : "410225",
			"PARENT_ID" : "410200",
			"DISTRICT_NAME" : "兰考县",
			"SPELLING" : "Lankao"
		},
		{
			"ID" : "410300",
			"PARENT_ID" : "410000",
			"DISTRICT_NAME" : "洛阳市",
			"SPELLING" : "Luoyang"
		},
		{
			"ID" : "410302",
			"PARENT_ID" : "410300",
			"DISTRICT_NAME" : "老城区",
			"SPELLING" : "Laocheng"
		},
		{
			"ID" : "410303",
			"PARENT_ID" : "410300",
			"DISTRICT_NAME" : "西工区",
			"SPELLING" : "Xigong"
		},
		{
			"ID" : "410304",
			"PARENT_ID" : "410300",
			"DISTRICT_NAME" : "瀍河回族区",
			"SPELLING" : "Chanhe"
		},
		{
			"ID" : "410305",
			"PARENT_ID" : "410300",
			"DISTRICT_NAME" : "涧西区",
			"SPELLING" : "Jianxi"
		},
		{
			"ID" : "410306",
			"PARENT_ID" : "410300",
			"DISTRICT_NAME" : "吉利区",
			"SPELLING" : "Jili"
		},
		{
			"ID" : "410311",
			"PARENT_ID" : "410300",
			"DISTRICT_NAME" : "洛龙区",
			"SPELLING" : "Luolong"
		},
		{
			"ID" : "410322",
			"PARENT_ID" : "410300",
			"DISTRICT_NAME" : "孟津县",
			"SPELLING" : "Mengjin"
		},
		{
			"ID" : "410323",
			"PARENT_ID" : "410300",
			"DISTRICT_NAME" : "新安县",
			"SPELLING" : "Xinan"
		},
		{
			"ID" : "410324",
			"PARENT_ID" : "410300",
			"DISTRICT_NAME" : "栾川县",
			"SPELLING" : "Luanchuan"
		},
		{
			"ID" : "410325",
			"PARENT_ID" : "410300",
			"DISTRICT_NAME" : "嵩县",
			"SPELLING" : "Songxian"
		},
		{
			"ID" : "410326",
			"PARENT_ID" : "410300",
			"DISTRICT_NAME" : "汝阳县",
			"SPELLING" : "Ruyang"
		},
		{
			"ID" : "410327",
			"PARENT_ID" : "410300",
			"DISTRICT_NAME" : "宜阳县",
			"SPELLING" : "Yiyang"
		},
		{
			"ID" : "410328",
			"PARENT_ID" : "410300",
			"DISTRICT_NAME" : "洛宁县",
			"SPELLING" : "Luoning"
		},
		{
			"ID" : "410329",
			"PARENT_ID" : "410300",
			"DISTRICT_NAME" : "伊川县",
			"SPELLING" : "Yichuan"
		},
		{
			"ID" : "410381",
			"PARENT_ID" : "410300",
			"DISTRICT_NAME" : "偃师市",
			"SPELLING" : "Yanshi"
		},
		{
			"ID" : "410400",
			"PARENT_ID" : "410000",
			"DISTRICT_NAME" : "平顶山市",
			"SPELLING" : "Pingdingshan"
		},
		{
			"ID" : "410402",
			"PARENT_ID" : "410400",
			"DISTRICT_NAME" : "新华区",
			"SPELLING" : "Xinhua"
		},
		{
			"ID" : "410403",
			"PARENT_ID" : "410400",
			"DISTRICT_NAME" : "卫东区",
			"SPELLING" : "Weidong"
		},
		{
			"ID" : "410404",
			"PARENT_ID" : "410400",
			"DISTRICT_NAME" : "石龙区",
			"SPELLING" : "Shilong"
		},
		{
			"ID" : "410411",
			"PARENT_ID" : "410400",
			"DISTRICT_NAME" : "湛河区",
			"SPELLING" : "Zhanhe"
		},
		{
			"ID" : "410421",
			"PARENT_ID" : "410400",
			"DISTRICT_NAME" : "宝丰县",
			"SPELLING" : "Baofeng"
		},
		{
			"ID" : "410422",
			"PARENT_ID" : "410400",
			"DISTRICT_NAME" : "叶县",
			"SPELLING" : "Yexian"
		},
		{
			"ID" : "410423",
			"PARENT_ID" : "410400",
			"DISTRICT_NAME" : "鲁山县",
			"SPELLING" : "Lushan"
		},
		{
			"ID" : "410425",
			"PARENT_ID" : "410400",
			"DISTRICT_NAME" : "郏县",
			"SPELLING" : "Jiaxian"
		},
		{
			"ID" : "410481",
			"PARENT_ID" : "410400",
			"DISTRICT_NAME" : "舞钢市",
			"SPELLING" : "Wugang"
		},
		{
			"ID" : "410482",
			"PARENT_ID" : "410400",
			"DISTRICT_NAME" : "汝州市",
			"SPELLING" : "Ruzhou"
		},
		{
			"ID" : "410500",
			"PARENT_ID" : "410000",
			"DISTRICT_NAME" : "安阳市",
			"SPELLING" : "Anyang"
		},
		{
			"ID" : "410502",
			"PARENT_ID" : "410500",
			"DISTRICT_NAME" : "文峰区",
			"SPELLING" : "Wenfeng"
		},
		{
			"ID" : "410503",
			"PARENT_ID" : "410500",
			"DISTRICT_NAME" : "北关区",
			"SPELLING" : "Beiguan"
		},
		{
			"ID" : "410505",
			"PARENT_ID" : "410500",
			"DISTRICT_NAME" : "殷都区",
			"SPELLING" : "Yindu"
		},
		{
			"ID" : "410506",
			"PARENT_ID" : "410500",
			"DISTRICT_NAME" : "龙安区",
			"SPELLING" : "Longan"
		},
		{
			"ID" : "410522",
			"PARENT_ID" : "410500",
			"DISTRICT_NAME" : "安阳县",
			"SPELLING" : "Anyang"
		},
		{
			"ID" : "410523",
			"PARENT_ID" : "410500",
			"DISTRICT_NAME" : "汤阴县",
			"SPELLING" : "Tangyin"
		},
		{
			"ID" : "410526",
			"PARENT_ID" : "410500",
			"DISTRICT_NAME" : "滑县",
			"SPELLING" : "Huaxian"
		},
		{
			"ID" : "410527",
			"PARENT_ID" : "410500",
			"DISTRICT_NAME" : "内黄县",
			"SPELLING" : "Neihuang"
		},
		{
			"ID" : "410581",
			"PARENT_ID" : "410500",
			"DISTRICT_NAME" : "林州市",
			"SPELLING" : "Linzhou"
		},
		{
			"ID" : "410600",
			"PARENT_ID" : "410000",
			"DISTRICT_NAME" : "鹤壁市",
			"SPELLING" : "Hebi"
		},
		{
			"ID" : "410602",
			"PARENT_ID" : "410600",
			"DISTRICT_NAME" : "鹤山区",
			"SPELLING" : "Heshan"
		},
		{
			"ID" : "410603",
			"PARENT_ID" : "410600",
			"DISTRICT_NAME" : "山城区",
			"SPELLING" : "Shancheng"
		},
		{
			"ID" : "410611",
			"PARENT_ID" : "410600",
			"DISTRICT_NAME" : "淇滨区",
			"SPELLING" : "Qibin"
		},
		{
			"ID" : "410621",
			"PARENT_ID" : "410600",
			"DISTRICT_NAME" : "浚县",
			"SPELLING" : "Xunxian"
		},
		{
			"ID" : "410622",
			"PARENT_ID" : "410600",
			"DISTRICT_NAME" : "淇县",
			"SPELLING" : "Qixian"
		},
		{
			"ID" : "410700",
			"PARENT_ID" : "410000",
			"DISTRICT_NAME" : "新乡市",
			"SPELLING" : "Xinxiang"
		},
		{
			"ID" : "410702",
			"PARENT_ID" : "410700",
			"DISTRICT_NAME" : "红旗区",
			"SPELLING" : "Hongqi"
		},
		{
			"ID" : "410703",
			"PARENT_ID" : "410700",
			"DISTRICT_NAME" : "卫滨区",
			"SPELLING" : "Weibin"
		},
		{
			"ID" : "410704",
			"PARENT_ID" : "410700",
			"DISTRICT_NAME" : "凤泉区",
			"SPELLING" : "Fengquan"
		},
		{
			"ID" : "410711",
			"PARENT_ID" : "410700",
			"DISTRICT_NAME" : "牧野区",
			"SPELLING" : "Muye"
		},
		{
			"ID" : "410721",
			"PARENT_ID" : "410700",
			"DISTRICT_NAME" : "新乡县",
			"SPELLING" : "Xinxiang"
		},
		{
			"ID" : "410724",
			"PARENT_ID" : "410700",
			"DISTRICT_NAME" : "获嘉县",
			"SPELLING" : "Huojia"
		},
		{
			"ID" : "410725",
			"PARENT_ID" : "410700",
			"DISTRICT_NAME" : "原阳县",
			"SPELLING" : "Yuanyang"
		},
		{
			"ID" : "410726",
			"PARENT_ID" : "410700",
			"DISTRICT_NAME" : "延津县",
			"SPELLING" : "Yanjin"
		},
		{
			"ID" : "410727",
			"PARENT_ID" : "410700",
			"DISTRICT_NAME" : "封丘县",
			"SPELLING" : "Fengqiu"
		},
		{
			"ID" : "410728",
			"PARENT_ID" : "410700",
			"DISTRICT_NAME" : "长垣县",
			"SPELLING" : "Changyuan"
		},
		{
			"ID" : "410781",
			"PARENT_ID" : "410700",
			"DISTRICT_NAME" : "卫辉市",
			"SPELLING" : "Weihui"
		},
		{
			"ID" : "410782",
			"PARENT_ID" : "410700",
			"DISTRICT_NAME" : "辉县市",
			"SPELLING" : "Huixian"
		},
		{
			"ID" : "410800",
			"PARENT_ID" : "410000",
			"DISTRICT_NAME" : "焦作市",
			"SPELLING" : "Jiaozuo"
		},
		{
			"ID" : "410802",
			"PARENT_ID" : "410800",
			"DISTRICT_NAME" : "解放区",
			"SPELLING" : "Jiefang"
		},
		{
			"ID" : "410803",
			"PARENT_ID" : "410800",
			"DISTRICT_NAME" : "中站区",
			"SPELLING" : "Zhongzhan"
		},
		{
			"ID" : "410804",
			"PARENT_ID" : "410800",
			"DISTRICT_NAME" : "马村区",
			"SPELLING" : "Macun"
		},
		{
			"ID" : "410811",
			"PARENT_ID" : "410800",
			"DISTRICT_NAME" : "山阳区",
			"SPELLING" : "Shanyang"
		},
		{
			"ID" : "410821",
			"PARENT_ID" : "410800",
			"DISTRICT_NAME" : "修武县",
			"SPELLING" : "Xiuwu"
		},
		{
			"ID" : "410822",
			"PARENT_ID" : "410800",
			"DISTRICT_NAME" : "博爱县",
			"SPELLING" : "Boai"
		},
		{
			"ID" : "410823",
			"PARENT_ID" : "410800",
			"DISTRICT_NAME" : "武陟县",
			"SPELLING" : "Wuzhi"
		},
		{
			"ID" : "410825",
			"PARENT_ID" : "410800",
			"DISTRICT_NAME" : "温县",
			"SPELLING" : "Wenxian"
		},
		{
			"ID" : "410882",
			"PARENT_ID" : "410800",
			"DISTRICT_NAME" : "沁阳市",
			"SPELLING" : "Qinyang"
		},
		{
			"ID" : "410883",
			"PARENT_ID" : "410800",
			"DISTRICT_NAME" : "孟州市",
			"SPELLING" : "Mengzhou"
		},
		{
			"ID" : "410900",
			"PARENT_ID" : "410000",
			"DISTRICT_NAME" : "濮阳市",
			"SPELLING" : "Puyang"
		},
		{
			"ID" : "410902",
			"PARENT_ID" : "410900",
			"DISTRICT_NAME" : "华龙区",
			"SPELLING" : "Hualong"
		},
		{
			"ID" : "410922",
			"PARENT_ID" : "410900",
			"DISTRICT_NAME" : "清丰县",
			"SPELLING" : "Qingfeng"
		},
		{
			"ID" : "410923",
			"PARENT_ID" : "410900",
			"DISTRICT_NAME" : "南乐县",
			"SPELLING" : "Nanle"
		},
		{
			"ID" : "410926",
			"PARENT_ID" : "410900",
			"DISTRICT_NAME" : "范县",
			"SPELLING" : "Fanxian"
		},
		{
			"ID" : "410927",
			"PARENT_ID" : "410900",
			"DISTRICT_NAME" : "台前县",
			"SPELLING" : "Taiqian"
		},
		{
			"ID" : "410928",
			"PARENT_ID" : "410900",
			"DISTRICT_NAME" : "濮阳县",
			"SPELLING" : "Puyang"
		},
		{
			"ID" : "411000",
			"PARENT_ID" : "410000",
			"DISTRICT_NAME" : "许昌市",
			"SPELLING" : "Xuchang"
		},
		{
			"ID" : "411002",
			"PARENT_ID" : "411000",
			"DISTRICT_NAME" : "魏都区",
			"SPELLING" : "Weidu"
		},
		{
			"ID" : "411023",
			"PARENT_ID" : "411000",
			"DISTRICT_NAME" : "许昌县",
			"SPELLING" : "Xuchang"
		},
		{
			"ID" : "411024",
			"PARENT_ID" : "411000",
			"DISTRICT_NAME" : "鄢陵县",
			"SPELLING" : "Yanling"
		},
		{
			"ID" : "411025",
			"PARENT_ID" : "411000",
			"DISTRICT_NAME" : "襄城县",
			"SPELLING" : "Xiangcheng"
		},
		{
			"ID" : "411081",
			"PARENT_ID" : "411000",
			"DISTRICT_NAME" : "禹州市",
			"SPELLING" : "Yuzhou"
		},
		{
			"ID" : "411082",
			"PARENT_ID" : "411000",
			"DISTRICT_NAME" : "长葛市",
			"SPELLING" : "Changge"
		},
		{
			"ID" : "411100",
			"PARENT_ID" : "410000",
			"DISTRICT_NAME" : "漯河市",
			"SPELLING" : "Luohe"
		},
		{
			"ID" : "411102",
			"PARENT_ID" : "411100",
			"DISTRICT_NAME" : "源汇区",
			"SPELLING" : "Yuanhui"
		},
		{
			"ID" : "411103",
			"PARENT_ID" : "411100",
			"DISTRICT_NAME" : "郾城区",
			"SPELLING" : "Yancheng"
		},
		{
			"ID" : "411104",
			"PARENT_ID" : "411100",
			"DISTRICT_NAME" : "召陵区",
			"SPELLING" : "Zhaoling"
		},
		{
			"ID" : "411121",
			"PARENT_ID" : "411100",
			"DISTRICT_NAME" : "舞阳县",
			"SPELLING" : "Wuyang"
		},
		{
			"ID" : "411122",
			"PARENT_ID" : "411100",
			"DISTRICT_NAME" : "临颍县",
			"SPELLING" : "Linying"
		},
		{
			"ID" : "411200",
			"PARENT_ID" : "410000",
			"DISTRICT_NAME" : "三门峡市",
			"SPELLING" : "Sanmenxia"
		},
		{
			"ID" : "411202",
			"PARENT_ID" : "411200",
			"DISTRICT_NAME" : "湖滨区",
			"SPELLING" : "Hubin"
		},
		{
			"ID" : "411221",
			"PARENT_ID" : "411200",
			"DISTRICT_NAME" : "渑池县",
			"SPELLING" : "Mianchi"
		},
		{
			"ID" : "411222",
			"PARENT_ID" : "411200",
			"DISTRICT_NAME" : "陕县",
			"SPELLING" : "Shanxian"
		},
		{
			"ID" : "411224",
			"PARENT_ID" : "411200",
			"DISTRICT_NAME" : "卢氏县",
			"SPELLING" : "Lushi"
		},
		{
			"ID" : "411281",
			"PARENT_ID" : "411200",
			"DISTRICT_NAME" : "义马市",
			"SPELLING" : "Yima"
		},
		{
			"ID" : "411282",
			"PARENT_ID" : "411200",
			"DISTRICT_NAME" : "灵宝市",
			"SPELLING" : "Lingbao"
		},
		{
			"ID" : "411300",
			"PARENT_ID" : "410000",
			"DISTRICT_NAME" : "南阳市",
			"SPELLING" : "Nanyang"
		},
		{
			"ID" : "411302",
			"PARENT_ID" : "411300",
			"DISTRICT_NAME" : "宛城区",
			"SPELLING" : "Wancheng"
		},
		{
			"ID" : "411303",
			"PARENT_ID" : "411300",
			"DISTRICT_NAME" : "卧龙区",
			"SPELLING" : "Wolong"
		},
		{
			"ID" : "411321",
			"PARENT_ID" : "411300",
			"DISTRICT_NAME" : "南召县",
			"SPELLING" : "Nanzhao"
		},
		{
			"ID" : "411322",
			"PARENT_ID" : "411300",
			"DISTRICT_NAME" : "方城县",
			"SPELLING" : "Fangcheng"
		},
		{
			"ID" : "411323",
			"PARENT_ID" : "411300",
			"DISTRICT_NAME" : "西峡县",
			"SPELLING" : "Xixia"
		},
		{
			"ID" : "411324",
			"PARENT_ID" : "411300",
			"DISTRICT_NAME" : "镇平县",
			"SPELLING" : "Zhenping"
		},
		{
			"ID" : "411325",
			"PARENT_ID" : "411300",
			"DISTRICT_NAME" : "内乡县",
			"SPELLING" : "Neixiang"
		},
		{
			"ID" : "411326",
			"PARENT_ID" : "411300",
			"DISTRICT_NAME" : "淅川县",
			"SPELLING" : "Xichuan"
		},
		{
			"ID" : "411327",
			"PARENT_ID" : "411300",
			"DISTRICT_NAME" : "社旗县",
			"SPELLING" : "Sheqi"
		},
		{
			"ID" : "411328",
			"PARENT_ID" : "411300",
			"DISTRICT_NAME" : "唐河县",
			"SPELLING" : "Tanghe"
		},
		{
			"ID" : "411329",
			"PARENT_ID" : "411300",
			"DISTRICT_NAME" : "新野县",
			"SPELLING" : "Xinye"
		},
		{
			"ID" : "411330",
			"PARENT_ID" : "411300",
			"DISTRICT_NAME" : "桐柏县",
			"SPELLING" : "Tongbai"
		},
		{
			"ID" : "411381",
			"PARENT_ID" : "411300",
			"DISTRICT_NAME" : "邓州市",
			"SPELLING" : "Dengzhou"
		},
		{
			"ID" : "411400",
			"PARENT_ID" : "410000",
			"DISTRICT_NAME" : "商丘市",
			"SPELLING" : "Shangqiu"
		},
		{
			"ID" : "411402",
			"PARENT_ID" : "411400",
			"DISTRICT_NAME" : "梁园区",
			"SPELLING" : "Liangyuan"
		},
		{
			"ID" : "411403",
			"PARENT_ID" : "411400",
			"DISTRICT_NAME" : "睢阳区",
			"SPELLING" : "Suiyang"
		},
		{
			"ID" : "411421",
			"PARENT_ID" : "411400",
			"DISTRICT_NAME" : "民权县",
			"SPELLING" : "Minquan"
		},
		{
			"ID" : "411422",
			"PARENT_ID" : "411400",
			"DISTRICT_NAME" : "睢县",
			"SPELLING" : "Suixian"
		},
		{
			"ID" : "411423",
			"PARENT_ID" : "411400",
			"DISTRICT_NAME" : "宁陵县",
			"SPELLING" : "Ningling"
		},
		{
			"ID" : "411424",
			"PARENT_ID" : "411400",
			"DISTRICT_NAME" : "柘城县",
			"SPELLING" : "Zhecheng"
		},
		{
			"ID" : "411425",
			"PARENT_ID" : "411400",
			"DISTRICT_NAME" : "虞城县",
			"SPELLING" : "Yucheng"
		},
		{
			"ID" : "411426",
			"PARENT_ID" : "411400",
			"DISTRICT_NAME" : "夏邑县",
			"SPELLING" : "Xiayi"
		},
		{
			"ID" : "411481",
			"PARENT_ID" : "411400",
			"DISTRICT_NAME" : "永城市",
			"SPELLING" : "Yongcheng"
		},
		{
			"ID" : "411500",
			"PARENT_ID" : "410000",
			"DISTRICT_NAME" : "信阳市",
			"SPELLING" : "Xinyang"
		},
		{
			"ID" : "411502",
			"PARENT_ID" : "411500",
			"DISTRICT_NAME" : "浉河区",
			"SPELLING" : "Shihe"
		},
		{
			"ID" : "411503",
			"PARENT_ID" : "411500",
			"DISTRICT_NAME" : "平桥区",
			"SPELLING" : "Pingqiao"
		},
		{
			"ID" : "411521",
			"PARENT_ID" : "411500",
			"DISTRICT_NAME" : "罗山县",
			"SPELLING" : "Luoshan"
		},
		{
			"ID" : "411522",
			"PARENT_ID" : "411500",
			"DISTRICT_NAME" : "光山县",
			"SPELLING" : "Guangshan"
		},
		{
			"ID" : "411523",
			"PARENT_ID" : "411500",
			"DISTRICT_NAME" : "新县",
			"SPELLING" : "Xinxian"
		},
		{
			"ID" : "411524",
			"PARENT_ID" : "411500",
			"DISTRICT_NAME" : "商城县",
			"SPELLING" : "Shangcheng"
		},
		{
			"ID" : "411525",
			"PARENT_ID" : "411500",
			"DISTRICT_NAME" : "固始县",
			"SPELLING" : "Gushi"
		},
		{
			"ID" : "411526",
			"PARENT_ID" : "411500",
			"DISTRICT_NAME" : "潢川县",
			"SPELLING" : "Huangchuan"
		},
		{
			"ID" : "411527",
			"PARENT_ID" : "411500",
			"DISTRICT_NAME" : "淮滨县",
			"SPELLING" : "Huaibin"
		},
		{
			"ID" : "411528",
			"PARENT_ID" : "411500",
			"DISTRICT_NAME" : "息县",
			"SPELLING" : "Xixian"
		},
		{
			"ID" : "411600",
			"PARENT_ID" : "410000",
			"DISTRICT_NAME" : "周口市",
			"SPELLING" : "Zhoukou"
		},
		{
			"ID" : "411602",
			"PARENT_ID" : "411600",
			"DISTRICT_NAME" : "川汇区",
			"SPELLING" : "Chuanhui"
		},
		{
			"ID" : "411621",
			"PARENT_ID" : "411600",
			"DISTRICT_NAME" : "扶沟县",
			"SPELLING" : "Fugou"
		},
		{
			"ID" : "411622",
			"PARENT_ID" : "411600",
			"DISTRICT_NAME" : "西华县",
			"SPELLING" : "Xihua"
		},
		{
			"ID" : "411623",
			"PARENT_ID" : "411600",
			"DISTRICT_NAME" : "商水县",
			"SPELLING" : "Shangshui"
		},
		{
			"ID" : "411624",
			"PARENT_ID" : "411600",
			"DISTRICT_NAME" : "沈丘县",
			"SPELLING" : "Shenqiu"
		},
		{
			"ID" : "411625",
			"PARENT_ID" : "411600",
			"DISTRICT_NAME" : "郸城县",
			"SPELLING" : "Dancheng"
		},
		{
			"ID" : "411626",
			"PARENT_ID" : "411600",
			"DISTRICT_NAME" : "淮阳县",
			"SPELLING" : "Huaiyang"
		},
		{
			"ID" : "411627",
			"PARENT_ID" : "411600",
			"DISTRICT_NAME" : "太康县",
			"SPELLING" : "Taikang"
		},
		{
			"ID" : "411628",
			"PARENT_ID" : "411600",
			"DISTRICT_NAME" : "鹿邑县",
			"SPELLING" : "Luyi"
		},
		{
			"ID" : "411681",
			"PARENT_ID" : "411600",
			"DISTRICT_NAME" : "项城市",
			"SPELLING" : "Xiangcheng"
		},
		{
			"ID" : "411700",
			"PARENT_ID" : "410000",
			"DISTRICT_NAME" : "驻马店市",
			"SPELLING" : "Zhumadian"
		},
		{
			"ID" : "411702",
			"PARENT_ID" : "411700",
			"DISTRICT_NAME" : "驿城区",
			"SPELLING" : "Yicheng"
		},
		{
			"ID" : "411721",
			"PARENT_ID" : "411700",
			"DISTRICT_NAME" : "西平县",
			"SPELLING" : "Xiping"
		},
		{
			"ID" : "411722",
			"PARENT_ID" : "411700",
			"DISTRICT_NAME" : "上蔡县",
			"SPELLING" : "Shangcai"
		},
		{
			"ID" : "411723",
			"PARENT_ID" : "411700",
			"DISTRICT_NAME" : "平舆县",
			"SPELLING" : "Pingyu"
		},
		{
			"ID" : "411724",
			"PARENT_ID" : "411700",
			"DISTRICT_NAME" : "正阳县",
			"SPELLING" : "Zhengyang"
		},
		{
			"ID" : "411725",
			"PARENT_ID" : "411700",
			"DISTRICT_NAME" : "确山县",
			"SPELLING" : "Queshan"
		},
		{
			"ID" : "411726",
			"PARENT_ID" : "411700",
			"DISTRICT_NAME" : "泌阳县",
			"SPELLING" : "Biyang"
		},
		{
			"ID" : "411727",
			"PARENT_ID" : "411700",
			"DISTRICT_NAME" : "汝南县",
			"SPELLING" : "Runan"
		},
		{
			"ID" : "411728",
			"PARENT_ID" : "411700",
			"DISTRICT_NAME" : "遂平县",
			"SPELLING" : "Suiping"
		},
		{
			"ID" : "411729",
			"PARENT_ID" : "411700",
			"DISTRICT_NAME" : "新蔡县",
			"SPELLING" : "Xincai"
		},
		{
			"ID" : "419000",
			"PARENT_ID" : "410000",
			"DISTRICT_NAME" : "济源市",
			"SPELLING" : "Jiyuan"
		},
		{
			"ID" : "419001",
			"PARENT_ID" : "419000",
			"DISTRICT_NAME" : "济源",
			"SPELLING" : "Jiyuan"
		},
		{
			"ID" : "420000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "湖北省",
			"SPELLING" : "Hubei"
		},
		{
			"ID" : "420100",
			"PARENT_ID" : "420000",
			"DISTRICT_NAME" : "武汉市",
			"SPELLING" : "Wuhan"
		},
		{
			"ID" : "420102",
			"PARENT_ID" : "420100",
			"DISTRICT_NAME" : "江岸区",
			"SPELLING" : "Jiangan"
		},
		{
			"ID" : "420103",
			"PARENT_ID" : "420100",
			"DISTRICT_NAME" : "江汉区",
			"SPELLING" : "Jianghan"
		},
		{
			"ID" : "420104",
			"PARENT_ID" : "420100",
			"DISTRICT_NAME" : "硚口区",
			"SPELLING" : "Qiaokou"
		},
		{
			"ID" : "420105",
			"PARENT_ID" : "420100",
			"DISTRICT_NAME" : "汉阳区",
			"SPELLING" : "Hanyang"
		},
		{
			"ID" : "420106",
			"PARENT_ID" : "420100",
			"DISTRICT_NAME" : "武昌区",
			"SPELLING" : "Wuchang"
		},
		{
			"ID" : "420107",
			"PARENT_ID" : "420100",
			"DISTRICT_NAME" : "青山区",
			"SPELLING" : "Qingshan"
		},
		{
			"ID" : "420111",
			"PARENT_ID" : "420100",
			"DISTRICT_NAME" : "洪山区",
			"SPELLING" : "Hongshan"
		},
		{
			"ID" : "420112",
			"PARENT_ID" : "420100",
			"DISTRICT_NAME" : "东西湖区",
			"SPELLING" : "Dongxihu"
		},
		{
			"ID" : "420113",
			"PARENT_ID" : "420100",
			"DISTRICT_NAME" : "汉南区",
			"SPELLING" : "Hannan"
		},
		{
			"ID" : "420114",
			"PARENT_ID" : "420100",
			"DISTRICT_NAME" : "蔡甸区",
			"SPELLING" : "Caidian"
		},
		{
			"ID" : "420115",
			"PARENT_ID" : "420100",
			"DISTRICT_NAME" : "江夏区",
			"SPELLING" : "Jiangxia"
		},
		{
			"ID" : "420116",
			"PARENT_ID" : "420100",
			"DISTRICT_NAME" : "黄陂区",
			"SPELLING" : "Huangpi"
		},
		{
			"ID" : "420117",
			"PARENT_ID" : "420100",
			"DISTRICT_NAME" : "新洲区",
			"SPELLING" : "Xinzhou"
		},
		{
			"ID" : "420200",
			"PARENT_ID" : "420000",
			"DISTRICT_NAME" : "黄石市",
			"SPELLING" : "Huangshi"
		},
		{
			"ID" : "420202",
			"PARENT_ID" : "420200",
			"DISTRICT_NAME" : "黄石港区",
			"SPELLING" : "Huangshigang"
		},
		{
			"ID" : "420203",
			"PARENT_ID" : "420200",
			"DISTRICT_NAME" : "西塞山区",
			"SPELLING" : "Xisaishan"
		},
		{
			"ID" : "420204",
			"PARENT_ID" : "420200",
			"DISTRICT_NAME" : "下陆区",
			"SPELLING" : "Xialu"
		},
		{
			"ID" : "420205",
			"PARENT_ID" : "420200",
			"DISTRICT_NAME" : "铁山区",
			"SPELLING" : "Tieshan"
		},
		{
			"ID" : "420222",
			"PARENT_ID" : "420200",
			"DISTRICT_NAME" : "阳新县",
			"SPELLING" : "Yangxin"
		},
		{
			"ID" : "420281",
			"PARENT_ID" : "420200",
			"DISTRICT_NAME" : "大冶市",
			"SPELLING" : "Daye"
		},
		{
			"ID" : "420300",
			"PARENT_ID" : "420000",
			"DISTRICT_NAME" : "十堰市",
			"SPELLING" : "Shiyan"
		},
		{
			"ID" : "420302",
			"PARENT_ID" : "420300",
			"DISTRICT_NAME" : "茅箭区",
			"SPELLING" : "Maojian"
		},
		{
			"ID" : "420303",
			"PARENT_ID" : "420300",
			"DISTRICT_NAME" : "张湾区",
			"SPELLING" : "Zhangwan"
		},
		{
			"ID" : "420304",
			"PARENT_ID" : "420300",
			"DISTRICT_NAME" : "郧阳区",
			"SPELLING" : "Yunyang"
		},
		{
			"ID" : "420322",
			"PARENT_ID" : "420300",
			"DISTRICT_NAME" : "郧西县",
			"SPELLING" : "Yunxi"
		},
		{
			"ID" : "420323",
			"PARENT_ID" : "420300",
			"DISTRICT_NAME" : "竹山县",
			"SPELLING" : "Zhushan"
		},
		{
			"ID" : "420324",
			"PARENT_ID" : "420300",
			"DISTRICT_NAME" : "竹溪县",
			"SPELLING" : "Zhuxi"
		},
		{
			"ID" : "420325",
			"PARENT_ID" : "420300",
			"DISTRICT_NAME" : "房县",
			"SPELLING" : "Fangxian"
		},
		{
			"ID" : "420381",
			"PARENT_ID" : "420300",
			"DISTRICT_NAME" : "丹江口市",
			"SPELLING" : "Danjiangkou"
		},
		{
			"ID" : "420500",
			"PARENT_ID" : "420000",
			"DISTRICT_NAME" : "宜昌市",
			"SPELLING" : "Yichang"
		},
		{
			"ID" : "420502",
			"PARENT_ID" : "420500",
			"DISTRICT_NAME" : "西陵区",
			"SPELLING" : "Xiling"
		},
		{
			"ID" : "420503",
			"PARENT_ID" : "420500",
			"DISTRICT_NAME" : "伍家岗区",
			"SPELLING" : "Wujiagang"
		},
		{
			"ID" : "420504",
			"PARENT_ID" : "420500",
			"DISTRICT_NAME" : "点军区",
			"SPELLING" : "Dianjun"
		},
		{
			"ID" : "420505",
			"PARENT_ID" : "420500",
			"DISTRICT_NAME" : "猇亭区",
			"SPELLING" : "Xiaoting"
		},
		{
			"ID" : "420506",
			"PARENT_ID" : "420500",
			"DISTRICT_NAME" : "夷陵区",
			"SPELLING" : "Yiling"
		},
		{
			"ID" : "420525",
			"PARENT_ID" : "420500",
			"DISTRICT_NAME" : "远安县",
			"SPELLING" : "Yuanan"
		},
		{
			"ID" : "420526",
			"PARENT_ID" : "420500",
			"DISTRICT_NAME" : "兴山县",
			"SPELLING" : "Xingshan"
		},
		{
			"ID" : "420527",
			"PARENT_ID" : "420500",
			"DISTRICT_NAME" : "秭归县",
			"SPELLING" : "Zigui"
		},
		{
			"ID" : "420528",
			"PARENT_ID" : "420500",
			"DISTRICT_NAME" : "长阳土家族自治县",
			"SPELLING" : "Changyang"
		},
		{
			"ID" : "420529",
			"PARENT_ID" : "420500",
			"DISTRICT_NAME" : "五峰土家族自治县",
			"SPELLING" : "Wufeng"
		},
		{
			"ID" : "420581",
			"PARENT_ID" : "420500",
			"DISTRICT_NAME" : "宜都市",
			"SPELLING" : "Yidu"
		},
		{
			"ID" : "420582",
			"PARENT_ID" : "420500",
			"DISTRICT_NAME" : "当阳市",
			"SPELLING" : "Dangyang"
		},
		{
			"ID" : "420583",
			"PARENT_ID" : "420500",
			"DISTRICT_NAME" : "枝江市",
			"SPELLING" : "Zhijiang"
		},
		{
			"ID" : "420600",
			"PARENT_ID" : "420000",
			"DISTRICT_NAME" : "襄阳市",
			"SPELLING" : "Xiangyang"
		},
		{
			"ID" : "420602",
			"PARENT_ID" : "420600",
			"DISTRICT_NAME" : "襄城区",
			"SPELLING" : "Xiangcheng"
		},
		{
			"ID" : "420606",
			"PARENT_ID" : "420600",
			"DISTRICT_NAME" : "樊城区",
			"SPELLING" : "Fancheng"
		},
		{
			"ID" : "420607",
			"PARENT_ID" : "420600",
			"DISTRICT_NAME" : "襄州区",
			"SPELLING" : "Xiangzhou"
		},
		{
			"ID" : "420624",
			"PARENT_ID" : "420600",
			"DISTRICT_NAME" : "南漳县",
			"SPELLING" : "Nanzhang"
		},
		{
			"ID" : "420625",
			"PARENT_ID" : "420600",
			"DISTRICT_NAME" : "谷城县",
			"SPELLING" : "Gucheng"
		},
		{
			"ID" : "420626",
			"PARENT_ID" : "420600",
			"DISTRICT_NAME" : "保康县",
			"SPELLING" : "Baokang"
		},
		{
			"ID" : "420682",
			"PARENT_ID" : "420600",
			"DISTRICT_NAME" : "老河口市",
			"SPELLING" : "Laohekou"
		},
		{
			"ID" : "420683",
			"PARENT_ID" : "420600",
			"DISTRICT_NAME" : "枣阳市",
			"SPELLING" : "Zaoyang"
		},
		{
			"ID" : "420684",
			"PARENT_ID" : "420600",
			"DISTRICT_NAME" : "宜城市",
			"SPELLING" : "Yicheng"
		},
		{
			"ID" : "420700",
			"PARENT_ID" : "420000",
			"DISTRICT_NAME" : "鄂州市",
			"SPELLING" : "Ezhou"
		},
		{
			"ID" : "420702",
			"PARENT_ID" : "420700",
			"DISTRICT_NAME" : "梁子湖区",
			"SPELLING" : "Liangzihu"
		},
		{
			"ID" : "420703",
			"PARENT_ID" : "420700",
			"DISTRICT_NAME" : "华容区",
			"SPELLING" : "Huarong"
		},
		{
			"ID" : "420704",
			"PARENT_ID" : "420700",
			"DISTRICT_NAME" : "鄂城区",
			"SPELLING" : "Echeng"
		},
		{
			"ID" : "420800",
			"PARENT_ID" : "420000",
			"DISTRICT_NAME" : "荆门市",
			"SPELLING" : "Jingmen"
		},
		{
			"ID" : "420802",
			"PARENT_ID" : "420800",
			"DISTRICT_NAME" : "东宝区",
			"SPELLING" : "Dongbao"
		},
		{
			"ID" : "420804",
			"PARENT_ID" : "420800",
			"DISTRICT_NAME" : "掇刀区",
			"SPELLING" : "Duodao"
		},
		{
			"ID" : "420821",
			"PARENT_ID" : "420800",
			"DISTRICT_NAME" : "京山县",
			"SPELLING" : "Jingshan"
		},
		{
			"ID" : "420822",
			"PARENT_ID" : "420800",
			"DISTRICT_NAME" : "沙洋县",
			"SPELLING" : "Shayang"
		},
		{
			"ID" : "420881",
			"PARENT_ID" : "420800",
			"DISTRICT_NAME" : "钟祥市",
			"SPELLING" : "Zhongxiang"
		},
		{
			"ID" : "420900",
			"PARENT_ID" : "420000",
			"DISTRICT_NAME" : "孝感市",
			"SPELLING" : "Xiaogan"
		},
		{
			"ID" : "420902",
			"PARENT_ID" : "420900",
			"DISTRICT_NAME" : "孝南区",
			"SPELLING" : "Xiaonan"
		},
		{
			"ID" : "420921",
			"PARENT_ID" : "420900",
			"DISTRICT_NAME" : "孝昌县",
			"SPELLING" : "Xiaochang"
		},
		{
			"ID" : "420922",
			"PARENT_ID" : "420900",
			"DISTRICT_NAME" : "大悟县",
			"SPELLING" : "Dawu"
		},
		{
			"ID" : "420923",
			"PARENT_ID" : "420900",
			"DISTRICT_NAME" : "云梦县",
			"SPELLING" : "Yunmeng"
		},
		{
			"ID" : "420981",
			"PARENT_ID" : "420900",
			"DISTRICT_NAME" : "应城市",
			"SPELLING" : "Yingcheng"
		},
		{
			"ID" : "420982",
			"PARENT_ID" : "420900",
			"DISTRICT_NAME" : "安陆市",
			"SPELLING" : "Anlu"
		},
		{
			"ID" : "420984",
			"PARENT_ID" : "420900",
			"DISTRICT_NAME" : "汉川市",
			"SPELLING" : "Hanchuan"
		},
		{
			"ID" : "421000",
			"PARENT_ID" : "420000",
			"DISTRICT_NAME" : "荆州市",
			"SPELLING" : "Jingzhou"
		},
		{
			"ID" : "421002",
			"PARENT_ID" : "421000",
			"DISTRICT_NAME" : "沙市区",
			"SPELLING" : "Shashi"
		},
		{
			"ID" : "421003",
			"PARENT_ID" : "421000",
			"DISTRICT_NAME" : "荆州区",
			"SPELLING" : "Jingzhou"
		},
		{
			"ID" : "421022",
			"PARENT_ID" : "421000",
			"DISTRICT_NAME" : "公安县",
			"SPELLING" : "Gongan"
		},
		{
			"ID" : "421023",
			"PARENT_ID" : "421000",
			"DISTRICT_NAME" : "监利县",
			"SPELLING" : "Jianli"
		},
		{
			"ID" : "421024",
			"PARENT_ID" : "421000",
			"DISTRICT_NAME" : "江陵县",
			"SPELLING" : "Jiangling"
		},
		{
			"ID" : "421081",
			"PARENT_ID" : "421000",
			"DISTRICT_NAME" : "石首市",
			"SPELLING" : "Shishou"
		},
		{
			"ID" : "421083",
			"PARENT_ID" : "421000",
			"DISTRICT_NAME" : "洪湖市",
			"SPELLING" : "Honghu"
		},
		{
			"ID" : "421087",
			"PARENT_ID" : "421000",
			"DISTRICT_NAME" : "松滋市",
			"SPELLING" : "Songzi"
		},
		{
			"ID" : "421100",
			"PARENT_ID" : "420000",
			"DISTRICT_NAME" : "黄冈市",
			"SPELLING" : "Huanggang"
		},
		{
			"ID" : "421102",
			"PARENT_ID" : "421100",
			"DISTRICT_NAME" : "黄州区",
			"SPELLING" : "Huangzhou"
		},
		{
			"ID" : "421121",
			"PARENT_ID" : "421100",
			"DISTRICT_NAME" : "团风县",
			"SPELLING" : "Tuanfeng"
		},
		{
			"ID" : "421122",
			"PARENT_ID" : "421100",
			"DISTRICT_NAME" : "红安县",
			"SPELLING" : "Hongan"
		},
		{
			"ID" : "421123",
			"PARENT_ID" : "421100",
			"DISTRICT_NAME" : "罗田县",
			"SPELLING" : "Luotian"
		},
		{
			"ID" : "421124",
			"PARENT_ID" : "421100",
			"DISTRICT_NAME" : "英山县",
			"SPELLING" : "Yingshan"
		},
		{
			"ID" : "421125",
			"PARENT_ID" : "421100",
			"DISTRICT_NAME" : "浠水县",
			"SPELLING" : "Xishui"
		},
		{
			"ID" : "421126",
			"PARENT_ID" : "421100",
			"DISTRICT_NAME" : "蕲春县",
			"SPELLING" : "Qichun"
		},
		{
			"ID" : "421127",
			"PARENT_ID" : "421100",
			"DISTRICT_NAME" : "黄梅县",
			"SPELLING" : "Huangmei"
		},
		{
			"ID" : "421181",
			"PARENT_ID" : "421100",
			"DISTRICT_NAME" : "麻城市",
			"SPELLING" : "Macheng"
		},
		{
			"ID" : "421182",
			"PARENT_ID" : "421100",
			"DISTRICT_NAME" : "武穴市",
			"SPELLING" : "Wuxue"
		},
		{
			"ID" : "421200",
			"PARENT_ID" : "420000",
			"DISTRICT_NAME" : "咸宁市",
			"SPELLING" : "Xianning"
		},
		{
			"ID" : "421202",
			"PARENT_ID" : "421200",
			"DISTRICT_NAME" : "咸安区",
			"SPELLING" : "Xianan"
		},
		{
			"ID" : "421221",
			"PARENT_ID" : "421200",
			"DISTRICT_NAME" : "嘉鱼县",
			"SPELLING" : "Jiayu"
		},
		{
			"ID" : "421222",
			"PARENT_ID" : "421200",
			"DISTRICT_NAME" : "通城县",
			"SPELLING" : "Tongcheng"
		},
		{
			"ID" : "421223",
			"PARENT_ID" : "421200",
			"DISTRICT_NAME" : "崇阳县",
			"SPELLING" : "Chongyang"
		},
		{
			"ID" : "421224",
			"PARENT_ID" : "421200",
			"DISTRICT_NAME" : "通山县",
			"SPELLING" : "Tongshan"
		},
		{
			"ID" : "421281",
			"PARENT_ID" : "421200",
			"DISTRICT_NAME" : "赤壁市",
			"SPELLING" : "Chibi"
		},
		{
			"ID" : "421300",
			"PARENT_ID" : "420000",
			"DISTRICT_NAME" : "随州市",
			"SPELLING" : "Suizhou"
		},
		{
			"ID" : "421303",
			"PARENT_ID" : "421300",
			"DISTRICT_NAME" : "曾都区",
			"SPELLING" : "Zengdu"
		},
		{
			"ID" : "421321",
			"PARENT_ID" : "421300",
			"DISTRICT_NAME" : "随县",
			"SPELLING" : "Suixian"
		},
		{
			"ID" : "421381",
			"PARENT_ID" : "421300",
			"DISTRICT_NAME" : "广水市",
			"SPELLING" : "Guangshui"
		},
		{
			"ID" : "422800",
			"PARENT_ID" : "420000",
			"DISTRICT_NAME" : "恩施土家族苗族自治州",
			"SPELLING" : "Enshi"
		},
		{
			"ID" : "422801",
			"PARENT_ID" : "422800",
			"DISTRICT_NAME" : "恩施市",
			"SPELLING" : "Enshi"
		},
		{
			"ID" : "422802",
			"PARENT_ID" : "422800",
			"DISTRICT_NAME" : "利川市",
			"SPELLING" : "Lichuan"
		},
		{
			"ID" : "422822",
			"PARENT_ID" : "422800",
			"DISTRICT_NAME" : "建始县",
			"SPELLING" : "Jianshi"
		},
		{
			"ID" : "422823",
			"PARENT_ID" : "422800",
			"DISTRICT_NAME" : "巴东县",
			"SPELLING" : "Badong"
		},
		{
			"ID" : "422825",
			"PARENT_ID" : "422800",
			"DISTRICT_NAME" : "宣恩县",
			"SPELLING" : "Xuanen"
		},
		{
			"ID" : "422826",
			"PARENT_ID" : "422800",
			"DISTRICT_NAME" : "咸丰县",
			"SPELLING" : "Xianfeng"
		},
		{
			"ID" : "422827",
			"PARENT_ID" : "422800",
			"DISTRICT_NAME" : "来凤县",
			"SPELLING" : "Laifeng"
		},
		{
			"ID" : "422828",
			"PARENT_ID" : "422800",
			"DISTRICT_NAME" : "鹤峰县",
			"SPELLING" : "Hefeng"
		},
		{
			"ID" : "430000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "湖南省",
			"SPELLING" : "Hunan"
		},
		{
			"ID" : "430100",
			"PARENT_ID" : "430000",
			"DISTRICT_NAME" : "长沙市",
			"SPELLING" : "Changsha"
		},
		{
			"ID" : "430102",
			"PARENT_ID" : "430100",
			"DISTRICT_NAME" : "芙蓉区",
			"SPELLING" : "Furong"
		},
		{
			"ID" : "430103",
			"PARENT_ID" : "430100",
			"DISTRICT_NAME" : "天心区",
			"SPELLING" : "Tianxin"
		},
		{
			"ID" : "430104",
			"PARENT_ID" : "430100",
			"DISTRICT_NAME" : "岳麓区",
			"SPELLING" : "Yuelu"
		},
		{
			"ID" : "430105",
			"PARENT_ID" : "430100",
			"DISTRICT_NAME" : "开福区",
			"SPELLING" : "Kaifu"
		},
		{
			"ID" : "430111",
			"PARENT_ID" : "430100",
			"DISTRICT_NAME" : "雨花区",
			"SPELLING" : "Yuhua"
		},
		{
			"ID" : "430112",
			"PARENT_ID" : "430100",
			"DISTRICT_NAME" : "望城区",
			"SPELLING" : "Wangcheng"
		},
		{
			"ID" : "430121",
			"PARENT_ID" : "430100",
			"DISTRICT_NAME" : "长沙县",
			"SPELLING" : "Changsha"
		},
		{
			"ID" : "430124",
			"PARENT_ID" : "430100",
			"DISTRICT_NAME" : "宁乡县",
			"SPELLING" : "Ningxiang"
		},
		{
			"ID" : "430181",
			"PARENT_ID" : "430100",
			"DISTRICT_NAME" : "浏阳市",
			"SPELLING" : "Liuyang"
		},
		{
			"ID" : "430200",
			"PARENT_ID" : "430000",
			"DISTRICT_NAME" : "株洲市",
			"SPELLING" : "Zhuzhou"
		},
		{
			"ID" : "430202",
			"PARENT_ID" : "430200",
			"DISTRICT_NAME" : "荷塘区",
			"SPELLING" : "Hetang"
		},
		{
			"ID" : "430203",
			"PARENT_ID" : "430200",
			"DISTRICT_NAME" : "芦淞区",
			"SPELLING" : "Lusong"
		},
		{
			"ID" : "430204",
			"PARENT_ID" : "430200",
			"DISTRICT_NAME" : "石峰区",
			"SPELLING" : "Shifeng"
		},
		{
			"ID" : "430211",
			"PARENT_ID" : "430200",
			"DISTRICT_NAME" : "天元区",
			"SPELLING" : "Tianyuan"
		},
		{
			"ID" : "430221",
			"PARENT_ID" : "430200",
			"DISTRICT_NAME" : "株洲县",
			"SPELLING" : "Zhuzhou"
		},
		{
			"ID" : "430223",
			"PARENT_ID" : "430200",
			"DISTRICT_NAME" : "攸县",
			"SPELLING" : "Youxian"
		},
		{
			"ID" : "430224",
			"PARENT_ID" : "430200",
			"DISTRICT_NAME" : "茶陵县",
			"SPELLING" : "Chaling"
		},
		{
			"ID" : "430225",
			"PARENT_ID" : "430200",
			"DISTRICT_NAME" : "炎陵县",
			"SPELLING" : "Yanling"
		},
		{
			"ID" : "430281",
			"PARENT_ID" : "430200",
			"DISTRICT_NAME" : "醴陵市",
			"SPELLING" : "Liling"
		},
		{
			"ID" : "430300",
			"PARENT_ID" : "430000",
			"DISTRICT_NAME" : "湘潭市",
			"SPELLING" : "Xiangtan"
		},
		{
			"ID" : "430302",
			"PARENT_ID" : "430300",
			"DISTRICT_NAME" : "雨湖区",
			"SPELLING" : "Yuhu"
		},
		{
			"ID" : "430304",
			"PARENT_ID" : "430300",
			"DISTRICT_NAME" : "岳塘区",
			"SPELLING" : "Yuetang"
		},
		{
			"ID" : "430321",
			"PARENT_ID" : "430300",
			"DISTRICT_NAME" : "湘潭县",
			"SPELLING" : "Xiangtan"
		},
		{
			"ID" : "430381",
			"PARENT_ID" : "430300",
			"DISTRICT_NAME" : "湘乡市",
			"SPELLING" : "Xiangxiang"
		},
		{
			"ID" : "430382",
			"PARENT_ID" : "430300",
			"DISTRICT_NAME" : "韶山市",
			"SPELLING" : "Shaoshan"
		},
		{
			"ID" : "430400",
			"PARENT_ID" : "430000",
			"DISTRICT_NAME" : "衡阳市",
			"SPELLING" : "Hengyang"
		},
		{
			"ID" : "430405",
			"PARENT_ID" : "430400",
			"DISTRICT_NAME" : "珠晖区",
			"SPELLING" : "Zhuhui"
		},
		{
			"ID" : "430406",
			"PARENT_ID" : "430400",
			"DISTRICT_NAME" : "雁峰区",
			"SPELLING" : "Yanfeng"
		},
		{
			"ID" : "430407",
			"PARENT_ID" : "430400",
			"DISTRICT_NAME" : "石鼓区",
			"SPELLING" : "Shigu"
		},
		{
			"ID" : "430408",
			"PARENT_ID" : "430400",
			"DISTRICT_NAME" : "蒸湘区",
			"SPELLING" : "Zhengxiang"
		},
		{
			"ID" : "430412",
			"PARENT_ID" : "430400",
			"DISTRICT_NAME" : "南岳区",
			"SPELLING" : "Nanyue"
		},
		{
			"ID" : "430421",
			"PARENT_ID" : "430400",
			"DISTRICT_NAME" : "衡阳县",
			"SPELLING" : "Hengyang"
		},
		{
			"ID" : "430422",
			"PARENT_ID" : "430400",
			"DISTRICT_NAME" : "衡南县",
			"SPELLING" : "Hengnan"
		},
		{
			"ID" : "430423",
			"PARENT_ID" : "430400",
			"DISTRICT_NAME" : "衡山县",
			"SPELLING" : "Hengshan"
		},
		{
			"ID" : "430424",
			"PARENT_ID" : "430400",
			"DISTRICT_NAME" : "衡东县",
			"SPELLING" : "Hengdong"
		},
		{
			"ID" : "430426",
			"PARENT_ID" : "430400",
			"DISTRICT_NAME" : "祁东县",
			"SPELLING" : "Qidong"
		},
		{
			"ID" : "430481",
			"PARENT_ID" : "430400",
			"DISTRICT_NAME" : "耒阳市",
			"SPELLING" : "Leiyang"
		},
		{
			"ID" : "430482",
			"PARENT_ID" : "430400",
			"DISTRICT_NAME" : "常宁市",
			"SPELLING" : "Changning"
		},
		{
			"ID" : "430500",
			"PARENT_ID" : "430000",
			"DISTRICT_NAME" : "邵阳市",
			"SPELLING" : "Shaoyang"
		},
		{
			"ID" : "430502",
			"PARENT_ID" : "430500",
			"DISTRICT_NAME" : "双清区",
			"SPELLING" : "Shuangqing"
		},
		{
			"ID" : "430503",
			"PARENT_ID" : "430500",
			"DISTRICT_NAME" : "大祥区",
			"SPELLING" : "Daxiang"
		},
		{
			"ID" : "430511",
			"PARENT_ID" : "430500",
			"DISTRICT_NAME" : "北塔区",
			"SPELLING" : "Beita"
		},
		{
			"ID" : "430521",
			"PARENT_ID" : "430500",
			"DISTRICT_NAME" : "邵东县",
			"SPELLING" : "Shaodong"
		},
		{
			"ID" : "430522",
			"PARENT_ID" : "430500",
			"DISTRICT_NAME" : "新邵县",
			"SPELLING" : "Xinshao"
		},
		{
			"ID" : "430523",
			"PARENT_ID" : "430500",
			"DISTRICT_NAME" : "邵阳县",
			"SPELLING" : "Shaoyang"
		},
		{
			"ID" : "430524",
			"PARENT_ID" : "430500",
			"DISTRICT_NAME" : "隆回县",
			"SPELLING" : "Longhui"
		},
		{
			"ID" : "430525",
			"PARENT_ID" : "430500",
			"DISTRICT_NAME" : "洞口县",
			"SPELLING" : "Dongkou"
		},
		{
			"ID" : "430527",
			"PARENT_ID" : "430500",
			"DISTRICT_NAME" : "绥宁县",
			"SPELLING" : "Suining"
		},
		{
			"ID" : "430528",
			"PARENT_ID" : "430500",
			"DISTRICT_NAME" : "新宁县",
			"SPELLING" : "Xinning"
		},
		{
			"ID" : "430529",
			"PARENT_ID" : "430500",
			"DISTRICT_NAME" : "城步苗族自治县",
			"SPELLING" : "Chengbu"
		},
		{
			"ID" : "430581",
			"PARENT_ID" : "430500",
			"DISTRICT_NAME" : "武冈市",
			"SPELLING" : "Wugang"
		},
		{
			"ID" : "430600",
			"PARENT_ID" : "430000",
			"DISTRICT_NAME" : "岳阳市",
			"SPELLING" : "Yueyang"
		},
		{
			"ID" : "430602",
			"PARENT_ID" : "430600",
			"DISTRICT_NAME" : "岳阳楼区",
			"SPELLING" : "Yueyanglou"
		},
		{
			"ID" : "430603",
			"PARENT_ID" : "430600",
			"DISTRICT_NAME" : "云溪区",
			"SPELLING" : "Yunxi"
		},
		{
			"ID" : "430611",
			"PARENT_ID" : "430600",
			"DISTRICT_NAME" : "君山区",
			"SPELLING" : "Junshan"
		},
		{
			"ID" : "430621",
			"PARENT_ID" : "430600",
			"DISTRICT_NAME" : "岳阳县",
			"SPELLING" : "Yueyang"
		},
		{
			"ID" : "430623",
			"PARENT_ID" : "430600",
			"DISTRICT_NAME" : "华容县",
			"SPELLING" : "Huarong"
		},
		{
			"ID" : "430624",
			"PARENT_ID" : "430600",
			"DISTRICT_NAME" : "湘阴县",
			"SPELLING" : "Xiangyin"
		},
		{
			"ID" : "430626",
			"PARENT_ID" : "430600",
			"DISTRICT_NAME" : "平江县",
			"SPELLING" : "Pingjiang"
		},
		{
			"ID" : "430681",
			"PARENT_ID" : "430600",
			"DISTRICT_NAME" : "汨罗市",
			"SPELLING" : "Miluo"
		},
		{
			"ID" : "430682",
			"PARENT_ID" : "430600",
			"DISTRICT_NAME" : "临湘市",
			"SPELLING" : "Linxiang"
		},
		{
			"ID" : "430700",
			"PARENT_ID" : "430000",
			"DISTRICT_NAME" : "常德市",
			"SPELLING" : "Changde"
		},
		{
			"ID" : "430702",
			"PARENT_ID" : "430700",
			"DISTRICT_NAME" : "武陵区",
			"SPELLING" : "Wuling"
		},
		{
			"ID" : "430703",
			"PARENT_ID" : "430700",
			"DISTRICT_NAME" : "鼎城区",
			"SPELLING" : "Dingcheng"
		},
		{
			"ID" : "430721",
			"PARENT_ID" : "430700",
			"DISTRICT_NAME" : "安乡县",
			"SPELLING" : "Anxiang"
		},
		{
			"ID" : "430722",
			"PARENT_ID" : "430700",
			"DISTRICT_NAME" : "汉寿县",
			"SPELLING" : "Hanshou"
		},
		{
			"ID" : "430723",
			"PARENT_ID" : "430700",
			"DISTRICT_NAME" : "澧县",
			"SPELLING" : "Lixian"
		},
		{
			"ID" : "430724",
			"PARENT_ID" : "430700",
			"DISTRICT_NAME" : "临澧县",
			"SPELLING" : "Linli"
		},
		{
			"ID" : "430725",
			"PARENT_ID" : "430700",
			"DISTRICT_NAME" : "桃源县",
			"SPELLING" : "Taoyuan"
		},
		{
			"ID" : "430726",
			"PARENT_ID" : "430700",
			"DISTRICT_NAME" : "石门县",
			"SPELLING" : "Shimen"
		},
		{
			"ID" : "430781",
			"PARENT_ID" : "430700",
			"DISTRICT_NAME" : "津市市",
			"SPELLING" : "Jinshi"
		},
		{
			"ID" : "430800",
			"PARENT_ID" : "430000",
			"DISTRICT_NAME" : "张家界市",
			"SPELLING" : "Zhangjiajie"
		},
		{
			"ID" : "430802",
			"PARENT_ID" : "430800",
			"DISTRICT_NAME" : "永定区",
			"SPELLING" : "Yongding"
		},
		{
			"ID" : "430811",
			"PARENT_ID" : "430800",
			"DISTRICT_NAME" : "武陵源区",
			"SPELLING" : "Wulingyuan"
		},
		{
			"ID" : "430821",
			"PARENT_ID" : "430800",
			"DISTRICT_NAME" : "慈利县",
			"SPELLING" : "Cili"
		},
		{
			"ID" : "430822",
			"PARENT_ID" : "430800",
			"DISTRICT_NAME" : "桑植县",
			"SPELLING" : "Sangzhi"
		},
		{
			"ID" : "430900",
			"PARENT_ID" : "430000",
			"DISTRICT_NAME" : "益阳市",
			"SPELLING" : "Yiyang"
		},
		{
			"ID" : "430902",
			"PARENT_ID" : "430900",
			"DISTRICT_NAME" : "资阳区",
			"SPELLING" : "Ziyang"
		},
		{
			"ID" : "430903",
			"PARENT_ID" : "430900",
			"DISTRICT_NAME" : "赫山区",
			"SPELLING" : "Heshan"
		},
		{
			"ID" : "430921",
			"PARENT_ID" : "430900",
			"DISTRICT_NAME" : "南县",
			"SPELLING" : "Nanxian"
		},
		{
			"ID" : "430922",
			"PARENT_ID" : "430900",
			"DISTRICT_NAME" : "桃江县",
			"SPELLING" : "Taojiang"
		},
		{
			"ID" : "430923",
			"PARENT_ID" : "430900",
			"DISTRICT_NAME" : "安化县",
			"SPELLING" : "Anhua"
		},
		{
			"ID" : "430981",
			"PARENT_ID" : "430900",
			"DISTRICT_NAME" : "沅江市",
			"SPELLING" : "Yuanjiang"
		},
		{
			"ID" : "431000",
			"PARENT_ID" : "430000",
			"DISTRICT_NAME" : "郴州市",
			"SPELLING" : "Chenzhou"
		},
		{
			"ID" : "431002",
			"PARENT_ID" : "431000",
			"DISTRICT_NAME" : "北湖区",
			"SPELLING" : "Beihu"
		},
		{
			"ID" : "431003",
			"PARENT_ID" : "431000",
			"DISTRICT_NAME" : "苏仙区",
			"SPELLING" : "Suxian"
		},
		{
			"ID" : "431021",
			"PARENT_ID" : "431000",
			"DISTRICT_NAME" : "桂阳县",
			"SPELLING" : "Guiyang"
		},
		{
			"ID" : "431022",
			"PARENT_ID" : "431000",
			"DISTRICT_NAME" : "宜章县",
			"SPELLING" : "Yizhang"
		},
		{
			"ID" : "431023",
			"PARENT_ID" : "431000",
			"DISTRICT_NAME" : "永兴县",
			"SPELLING" : "Yongxing"
		},
		{
			"ID" : "431024",
			"PARENT_ID" : "431000",
			"DISTRICT_NAME" : "嘉禾县",
			"SPELLING" : "Jiahe"
		},
		{
			"ID" : "431025",
			"PARENT_ID" : "431000",
			"DISTRICT_NAME" : "临武县",
			"SPELLING" : "Linwu"
		},
		{
			"ID" : "431026",
			"PARENT_ID" : "431000",
			"DISTRICT_NAME" : "汝城县",
			"SPELLING" : "Rucheng"
		},
		{
			"ID" : "431027",
			"PARENT_ID" : "431000",
			"DISTRICT_NAME" : "桂东县",
			"SPELLING" : "Guidong"
		},
		{
			"ID" : "431028",
			"PARENT_ID" : "431000",
			"DISTRICT_NAME" : "安仁县",
			"SPELLING" : "Anren"
		},
		{
			"ID" : "431081",
			"PARENT_ID" : "431000",
			"DISTRICT_NAME" : "资兴市",
			"SPELLING" : "Zixing"
		},
		{
			"ID" : "431100",
			"PARENT_ID" : "430000",
			"DISTRICT_NAME" : "永州市",
			"SPELLING" : "Yongzhou"
		},
		{
			"ID" : "431102",
			"PARENT_ID" : "431100",
			"DISTRICT_NAME" : "零陵区",
			"SPELLING" : "Lingling"
		},
		{
			"ID" : "431103",
			"PARENT_ID" : "431100",
			"DISTRICT_NAME" : "冷水滩区",
			"SPELLING" : "Lengshuitan"
		},
		{
			"ID" : "431121",
			"PARENT_ID" : "431100",
			"DISTRICT_NAME" : "祁阳县",
			"SPELLING" : "Qiyang"
		},
		{
			"ID" : "431122",
			"PARENT_ID" : "431100",
			"DISTRICT_NAME" : "东安县",
			"SPELLING" : "Dongan"
		},
		{
			"ID" : "431123",
			"PARENT_ID" : "431100",
			"DISTRICT_NAME" : "双牌县",
			"SPELLING" : "Shuangpai"
		},
		{
			"ID" : "431124",
			"PARENT_ID" : "431100",
			"DISTRICT_NAME" : "道县",
			"SPELLING" : "Daoxian"
		},
		{
			"ID" : "431125",
			"PARENT_ID" : "431100",
			"DISTRICT_NAME" : "江永县",
			"SPELLING" : "Jiangyong"
		},
		{
			"ID" : "431126",
			"PARENT_ID" : "431100",
			"DISTRICT_NAME" : "宁远县",
			"SPELLING" : "Ningyuan"
		},
		{
			"ID" : "431127",
			"PARENT_ID" : "431100",
			"DISTRICT_NAME" : "蓝山县",
			"SPELLING" : "Lanshan"
		},
		{
			"ID" : "431128",
			"PARENT_ID" : "431100",
			"DISTRICT_NAME" : "新田县",
			"SPELLING" : "Xintian"
		},
		{
			"ID" : "431129",
			"PARENT_ID" : "431100",
			"DISTRICT_NAME" : "江华瑶族自治县",
			"SPELLING" : "Jianghua"
		},
		{
			"ID" : "431200",
			"PARENT_ID" : "430000",
			"DISTRICT_NAME" : "怀化市",
			"SPELLING" : "Huaihua"
		},
		{
			"ID" : "431202",
			"PARENT_ID" : "431200",
			"DISTRICT_NAME" : "鹤城区",
			"SPELLING" : "Hecheng"
		},
		{
			"ID" : "431221",
			"PARENT_ID" : "431200",
			"DISTRICT_NAME" : "中方县",
			"SPELLING" : "Zhongfang"
		},
		{
			"ID" : "431222",
			"PARENT_ID" : "431200",
			"DISTRICT_NAME" : "沅陵县",
			"SPELLING" : "Yuanling"
		},
		{
			"ID" : "431223",
			"PARENT_ID" : "431200",
			"DISTRICT_NAME" : "辰溪县",
			"SPELLING" : "Chenxi"
		},
		{
			"ID" : "431224",
			"PARENT_ID" : "431200",
			"DISTRICT_NAME" : "溆浦县",
			"SPELLING" : "Xupu"
		},
		{
			"ID" : "431225",
			"PARENT_ID" : "431200",
			"DISTRICT_NAME" : "会同县",
			"SPELLING" : "Huitong"
		},
		{
			"ID" : "431226",
			"PARENT_ID" : "431200",
			"DISTRICT_NAME" : "麻阳苗族自治县",
			"SPELLING" : "Mayang"
		},
		{
			"ID" : "431227",
			"PARENT_ID" : "431200",
			"DISTRICT_NAME" : "新晃侗族自治县",
			"SPELLING" : "Xinhuang"
		},
		{
			"ID" : "431228",
			"PARENT_ID" : "431200",
			"DISTRICT_NAME" : "芷江侗族自治县",
			"SPELLING" : "Zhijiang"
		},
		{
			"ID" : "431229",
			"PARENT_ID" : "431200",
			"DISTRICT_NAME" : "靖州苗族侗族自治县",
			"SPELLING" : "Jingzhou"
		},
		{
			"ID" : "431230",
			"PARENT_ID" : "431200",
			"DISTRICT_NAME" : "通道侗族自治县",
			"SPELLING" : "Tongdao"
		},
		{
			"ID" : "431281",
			"PARENT_ID" : "431200",
			"DISTRICT_NAME" : "洪江市",
			"SPELLING" : "Hongjiang"
		},
		{
			"ID" : "431300",
			"PARENT_ID" : "430000",
			"DISTRICT_NAME" : "娄底市",
			"SPELLING" : "Loudi"
		},
		{
			"ID" : "431302",
			"PARENT_ID" : "431300",
			"DISTRICT_NAME" : "娄星区",
			"SPELLING" : "Louxing"
		},
		{
			"ID" : "431321",
			"PARENT_ID" : "431300",
			"DISTRICT_NAME" : "双峰县",
			"SPELLING" : "Shuangfeng"
		},
		{
			"ID" : "431322",
			"PARENT_ID" : "431300",
			"DISTRICT_NAME" : "新化县",
			"SPELLING" : "Xinhua"
		},
		{
			"ID" : "431381",
			"PARENT_ID" : "431300",
			"DISTRICT_NAME" : "冷水江市",
			"SPELLING" : "Lengshuijiang"
		},
		{
			"ID" : "431382",
			"PARENT_ID" : "431300",
			"DISTRICT_NAME" : "涟源市",
			"SPELLING" : "Lianyuan"
		},
		{
			"ID" : "433100",
			"PARENT_ID" : "430000",
			"DISTRICT_NAME" : "湘西土家族苗族自治州",
			"SPELLING" : "Xiangxi"
		},
		{
			"ID" : "433101",
			"PARENT_ID" : "433100",
			"DISTRICT_NAME" : "吉首市",
			"SPELLING" : "Jishou"
		},
		{
			"ID" : "433122",
			"PARENT_ID" : "433100",
			"DISTRICT_NAME" : "泸溪县",
			"SPELLING" : "Luxi"
		},
		{
			"ID" : "433123",
			"PARENT_ID" : "433100",
			"DISTRICT_NAME" : "凤凰县",
			"SPELLING" : "Fenghuang"
		},
		{
			"ID" : "433124",
			"PARENT_ID" : "433100",
			"DISTRICT_NAME" : "花垣县",
			"SPELLING" : "Huayuan"
		},
		{
			"ID" : "433125",
			"PARENT_ID" : "433100",
			"DISTRICT_NAME" : "保靖县",
			"SPELLING" : "Baojing"
		},
		{
			"ID" : "433126",
			"PARENT_ID" : "433100",
			"DISTRICT_NAME" : "古丈县",
			"SPELLING" : "Guzhang"
		},
		{
			"ID" : "433127",
			"PARENT_ID" : "433100",
			"DISTRICT_NAME" : "永顺县",
			"SPELLING" : "Yongshun"
		},
		{
			"ID" : "433130",
			"PARENT_ID" : "433100",
			"DISTRICT_NAME" : "龙山县",
			"SPELLING" : "Longshan"
		},
		{
			"ID" : "440000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "广东省",
			"SPELLING" : "Guangdong"
		},
		{
			"ID" : "440100",
			"PARENT_ID" : "440000",
			"DISTRICT_NAME" : "广州市",
			"SPELLING" : "Guangzhou"
		},
		{
			"ID" : "440103",
			"PARENT_ID" : "440100",
			"DISTRICT_NAME" : "荔湾区",
			"SPELLING" : "Liwan"
		},
		{
			"ID" : "440104",
			"PARENT_ID" : "440100",
			"DISTRICT_NAME" : "越秀区",
			"SPELLING" : "Yuexiu"
		},
		{
			"ID" : "440105",
			"PARENT_ID" : "440100",
			"DISTRICT_NAME" : "海珠区",
			"SPELLING" : "Haizhu"
		},
		{
			"ID" : "440106",
			"PARENT_ID" : "440100",
			"DISTRICT_NAME" : "天河区",
			"SPELLING" : "Tianhe"
		},
		{
			"ID" : "440111",
			"PARENT_ID" : "440100",
			"DISTRICT_NAME" : "白云区",
			"SPELLING" : "Baiyun"
		},
		{
			"ID" : "440112",
			"PARENT_ID" : "440100",
			"DISTRICT_NAME" : "黄埔区",
			"SPELLING" : "Huangpu"
		},
		{
			"ID" : "440113",
			"PARENT_ID" : "440100",
			"DISTRICT_NAME" : "番禺区",
			"SPELLING" : "Panyu"
		},
		{
			"ID" : "440114",
			"PARENT_ID" : "440100",
			"DISTRICT_NAME" : "花都区",
			"SPELLING" : "Huadu"
		},
		{
			"ID" : "440115",
			"PARENT_ID" : "440100",
			"DISTRICT_NAME" : "南沙区",
			"SPELLING" : "Nansha"
		},
		{
			"ID" : "440117",
			"PARENT_ID" : "440100",
			"DISTRICT_NAME" : "从化区",
			"SPELLING" : "Conghua"
		},
		{
			"ID" : "440118",
			"PARENT_ID" : "440100",
			"DISTRICT_NAME" : "增城区",
			"SPELLING" : "Zengcheng"
		},
		{
			"ID" : "440200",
			"PARENT_ID" : "440000",
			"DISTRICT_NAME" : "韶关市",
			"SPELLING" : "Shaoguan"
		},
		{
			"ID" : "440203",
			"PARENT_ID" : "440200",
			"DISTRICT_NAME" : "武江区",
			"SPELLING" : "Wujiang"
		},
		{
			"ID" : "440204",
			"PARENT_ID" : "440200",
			"DISTRICT_NAME" : "浈江区",
			"SPELLING" : "Zhenjiang"
		},
		{
			"ID" : "440205",
			"PARENT_ID" : "440200",
			"DISTRICT_NAME" : "曲江区",
			"SPELLING" : "Qujiang"
		},
		{
			"ID" : "440222",
			"PARENT_ID" : "440200",
			"DISTRICT_NAME" : "始兴县",
			"SPELLING" : "Shixing"
		},
		{
			"ID" : "440224",
			"PARENT_ID" : "440200",
			"DISTRICT_NAME" : "仁化县",
			"SPELLING" : "Renhua"
		},
		{
			"ID" : "440229",
			"PARENT_ID" : "440200",
			"DISTRICT_NAME" : "翁源县",
			"SPELLING" : "Wengyuan"
		},
		{
			"ID" : "440232",
			"PARENT_ID" : "440200",
			"DISTRICT_NAME" : "乳源瑶族自治县",
			"SPELLING" : "Ruyuan"
		},
		{
			"ID" : "440233",
			"PARENT_ID" : "440200",
			"DISTRICT_NAME" : "新丰县",
			"SPELLING" : "Xinfeng"
		},
		{
			"ID" : "440281",
			"PARENT_ID" : "440200",
			"DISTRICT_NAME" : "乐昌市",
			"SPELLING" : "Lechang"
		},
		{
			"ID" : "440282",
			"PARENT_ID" : "440200",
			"DISTRICT_NAME" : "南雄市",
			"SPELLING" : "Nanxiong"
		},
		{
			"ID" : "440300",
			"PARENT_ID" : "440000",
			"DISTRICT_NAME" : "深圳市",
			"SPELLING" : "Shenzhen"
		},
		{
			"ID" : "440303",
			"PARENT_ID" : "440300",
			"DISTRICT_NAME" : "罗湖区",
			"SPELLING" : "Luohu"
		},
		{
			"ID" : "440304",
			"PARENT_ID" : "440300",
			"DISTRICT_NAME" : "福田区",
			"SPELLING" : "Futian"
		},
		{
			"ID" : "440305",
			"PARENT_ID" : "440300",
			"DISTRICT_NAME" : "南山区",
			"SPELLING" : "Nanshan"
		},
		{
			"ID" : "440306",
			"PARENT_ID" : "440300",
			"DISTRICT_NAME" : "宝安区",
			"SPELLING" : "Baoan"
		},
		{
			"ID" : "440307",
			"PARENT_ID" : "440300",
			"DISTRICT_NAME" : "龙岗区",
			"SPELLING" : "Longgang"
		},
		{
			"ID" : "440308",
			"PARENT_ID" : "440300",
			"DISTRICT_NAME" : "盐田区",
			"SPELLING" : "Yantian"
		},
		{
			"ID" : "440309",
			"PARENT_ID" : "440300",
			"DISTRICT_NAME" : "光明新区",
			"SPELLING" : "Guangmingxinqu"
		},
		{
			"ID" : "440310",
			"PARENT_ID" : "440300",
			"DISTRICT_NAME" : "坪山新区",
			"SPELLING" : "Pingshanxinqu"
		},
		{
			"ID" : "440311",
			"PARENT_ID" : "440300",
			"DISTRICT_NAME" : "大鹏新区",
			"SPELLING" : "Dapengxinqu"
		},
		{
			"ID" : "440312",
			"PARENT_ID" : "440300",
			"DISTRICT_NAME" : "龙华新区",
			"SPELLING" : "Longhuaxinqu"
		},
		{
			"ID" : "440400",
			"PARENT_ID" : "440000",
			"DISTRICT_NAME" : "珠海市",
			"SPELLING" : "Zhuhai"
		},
		{
			"ID" : "440402",
			"PARENT_ID" : "440400",
			"DISTRICT_NAME" : "香洲区",
			"SPELLING" : "Xiangzhou"
		},
		{
			"ID" : "440403",
			"PARENT_ID" : "440400",
			"DISTRICT_NAME" : "斗门区",
			"SPELLING" : "Doumen"
		},
		{
			"ID" : "440404",
			"PARENT_ID" : "440400",
			"DISTRICT_NAME" : "金湾区",
			"SPELLING" : "Jinwan"
		},
		{
			"ID" : "440500",
			"PARENT_ID" : "440000",
			"DISTRICT_NAME" : "汕头市",
			"SPELLING" : "Shantou"
		},
		{
			"ID" : "440507",
			"PARENT_ID" : "440500",
			"DISTRICT_NAME" : "龙湖区",
			"SPELLING" : "Longhu"
		},
		{
			"ID" : "440511",
			"PARENT_ID" : "440500",
			"DISTRICT_NAME" : "金平区",
			"SPELLING" : "Jinping"
		},
		{
			"ID" : "440512",
			"PARENT_ID" : "440500",
			"DISTRICT_NAME" : "濠江区",
			"SPELLING" : "Haojiang"
		},
		{
			"ID" : "440513",
			"PARENT_ID" : "440500",
			"DISTRICT_NAME" : "潮阳区",
			"SPELLING" : "Chaoyang"
		},
		{
			"ID" : "440514",
			"PARENT_ID" : "440500",
			"DISTRICT_NAME" : "潮南区",
			"SPELLING" : "Chaonan"
		},
		{
			"ID" : "440515",
			"PARENT_ID" : "440500",
			"DISTRICT_NAME" : "澄海区",
			"SPELLING" : "Chenghai"
		},
		{
			"ID" : "440523",
			"PARENT_ID" : "440500",
			"DISTRICT_NAME" : "南澳县",
			"SPELLING" : "Nanao"
		},
		{
			"ID" : "440600",
			"PARENT_ID" : "440000",
			"DISTRICT_NAME" : "佛山市",
			"SPELLING" : "Foshan"
		},
		{
			"ID" : "440604",
			"PARENT_ID" : "440600",
			"DISTRICT_NAME" : "禅城区",
			"SPELLING" : "Chancheng"
		},
		{
			"ID" : "440605",
			"PARENT_ID" : "440600",
			"DISTRICT_NAME" : "南海区",
			"SPELLING" : "Nanhai"
		},
		{
			"ID" : "440606",
			"PARENT_ID" : "440600",
			"DISTRICT_NAME" : "顺德区",
			"SPELLING" : "Shunde"
		},
		{
			"ID" : "440607",
			"PARENT_ID" : "440600",
			"DISTRICT_NAME" : "三水区",
			"SPELLING" : "Sanshui"
		},
		{
			"ID" : "440608",
			"PARENT_ID" : "440600",
			"DISTRICT_NAME" : "高明区",
			"SPELLING" : "Gaoming"
		},
		{
			"ID" : "440700",
			"PARENT_ID" : "440000",
			"DISTRICT_NAME" : "江门市",
			"SPELLING" : "Jiangmen"
		},
		{
			"ID" : "440703",
			"PARENT_ID" : "440700",
			"DISTRICT_NAME" : "蓬江区",
			"SPELLING" : "Pengjiang"
		},
		{
			"ID" : "440704",
			"PARENT_ID" : "440700",
			"DISTRICT_NAME" : "江海区",
			"SPELLING" : "Jianghai"
		},
		{
			"ID" : "440705",
			"PARENT_ID" : "440700",
			"DISTRICT_NAME" : "新会区",
			"SPELLING" : "Xinhui"
		},
		{
			"ID" : "440781",
			"PARENT_ID" : "440700",
			"DISTRICT_NAME" : "台山市",
			"SPELLING" : "Taishan"
		},
		{
			"ID" : "440783",
			"PARENT_ID" : "440700",
			"DISTRICT_NAME" : "开平市",
			"SPELLING" : "Kaiping"
		},
		{
			"ID" : "440784",
			"PARENT_ID" : "440700",
			"DISTRICT_NAME" : "鹤山市",
			"SPELLING" : "Heshan"
		},
		{
			"ID" : "440785",
			"PARENT_ID" : "440700",
			"DISTRICT_NAME" : "恩平市",
			"SPELLING" : "Enping"
		},
		{
			"ID" : "440800",
			"PARENT_ID" : "440000",
			"DISTRICT_NAME" : "湛江市",
			"SPELLING" : "Zhanjiang"
		},
		{
			"ID" : "440802",
			"PARENT_ID" : "440800",
			"DISTRICT_NAME" : "赤坎区",
			"SPELLING" : "Chikan"
		},
		{
			"ID" : "440803",
			"PARENT_ID" : "440800",
			"DISTRICT_NAME" : "霞山区",
			"SPELLING" : "Xiashan"
		},
		{
			"ID" : "440804",
			"PARENT_ID" : "440800",
			"DISTRICT_NAME" : "坡头区",
			"SPELLING" : "Potou"
		},
		{
			"ID" : "440811",
			"PARENT_ID" : "440800",
			"DISTRICT_NAME" : "麻章区",
			"SPELLING" : "Mazhang"
		},
		{
			"ID" : "440823",
			"PARENT_ID" : "440800",
			"DISTRICT_NAME" : "遂溪县",
			"SPELLING" : "Suixi"
		},
		{
			"ID" : "440825",
			"PARENT_ID" : "440800",
			"DISTRICT_NAME" : "徐闻县",
			"SPELLING" : "Xuwen"
		},
		{
			"ID" : "440881",
			"PARENT_ID" : "440800",
			"DISTRICT_NAME" : "廉江市",
			"SPELLING" : "Lianjiang"
		},
		{
			"ID" : "440882",
			"PARENT_ID" : "440800",
			"DISTRICT_NAME" : "雷州市",
			"SPELLING" : "Leizhou"
		},
		{
			"ID" : "440883",
			"PARENT_ID" : "440800",
			"DISTRICT_NAME" : "吴川市",
			"SPELLING" : "Wuchuan"
		},
		{
			"ID" : "440900",
			"PARENT_ID" : "440000",
			"DISTRICT_NAME" : "茂名市",
			"SPELLING" : "Maoming"
		},
		{
			"ID" : "440902",
			"PARENT_ID" : "440900",
			"DISTRICT_NAME" : "茂南区",
			"SPELLING" : "Maonan"
		},
		{
			"ID" : "440904",
			"PARENT_ID" : "440900",
			"DISTRICT_NAME" : "电白区",
			"SPELLING" : "Dianbai"
		},
		{
			"ID" : "440981",
			"PARENT_ID" : "440900",
			"DISTRICT_NAME" : "高州市",
			"SPELLING" : "Gaozhou"
		},
		{
			"ID" : "440982",
			"PARENT_ID" : "440900",
			"DISTRICT_NAME" : "化州市",
			"SPELLING" : "Huazhou"
		},
		{
			"ID" : "440983",
			"PARENT_ID" : "440900",
			"DISTRICT_NAME" : "信宜市",
			"SPELLING" : "Xinyi"
		},
		{
			"ID" : "441200",
			"PARENT_ID" : "440000",
			"DISTRICT_NAME" : "肇庆市",
			"SPELLING" : "Zhaoqing"
		},
		{
			"ID" : "441202",
			"PARENT_ID" : "441200",
			"DISTRICT_NAME" : "端州区",
			"SPELLING" : "Duanzhou"
		},
		{
			"ID" : "441203",
			"PARENT_ID" : "441200",
			"DISTRICT_NAME" : "鼎湖区",
			"SPELLING" : "Dinghu"
		},
		{
			"ID" : "441223",
			"PARENT_ID" : "441200",
			"DISTRICT_NAME" : "广宁县",
			"SPELLING" : "Guangning"
		},
		{
			"ID" : "441224",
			"PARENT_ID" : "441200",
			"DISTRICT_NAME" : "怀集县",
			"SPELLING" : "Huaiji"
		},
		{
			"ID" : "441225",
			"PARENT_ID" : "441200",
			"DISTRICT_NAME" : "封开县",
			"SPELLING" : "Fengkai"
		},
		{
			"ID" : "441226",
			"PARENT_ID" : "441200",
			"DISTRICT_NAME" : "德庆县",
			"SPELLING" : "Deqing"
		},
		{
			"ID" : "441283",
			"PARENT_ID" : "441200",
			"DISTRICT_NAME" : "高要市",
			"SPELLING" : "Gaoyao"
		},
		{
			"ID" : "441284",
			"PARENT_ID" : "441200",
			"DISTRICT_NAME" : "四会市",
			"SPELLING" : "Sihui"
		},
		{
			"ID" : "441300",
			"PARENT_ID" : "440000",
			"DISTRICT_NAME" : "惠州市",
			"SPELLING" : "Huizhou"
		},
		{
			"ID" : "441302",
			"PARENT_ID" : "441300",
			"DISTRICT_NAME" : "惠城区",
			"SPELLING" : "Huicheng"
		},
		{
			"ID" : "441303",
			"PARENT_ID" : "441300",
			"DISTRICT_NAME" : "惠阳区",
			"SPELLING" : "Huiyang"
		},
		{
			"ID" : "441322",
			"PARENT_ID" : "441300",
			"DISTRICT_NAME" : "博罗县",
			"SPELLING" : "Boluo"
		},
		{
			"ID" : "441323",
			"PARENT_ID" : "441300",
			"DISTRICT_NAME" : "惠东县",
			"SPELLING" : "Huidong"
		},
		{
			"ID" : "441324",
			"PARENT_ID" : "441300",
			"DISTRICT_NAME" : "龙门县",
			"SPELLING" : "Longmen"
		},
		{
			"ID" : "441400",
			"PARENT_ID" : "440000",
			"DISTRICT_NAME" : "梅州市",
			"SPELLING" : "Meizhou"
		},
		{
			"ID" : "441402",
			"PARENT_ID" : "441400",
			"DISTRICT_NAME" : "梅江区",
			"SPELLING" : "Meijiang"
		},
		{
			"ID" : "441403",
			"PARENT_ID" : "441400",
			"DISTRICT_NAME" : "梅县区",
			"SPELLING" : "Meixian"
		},
		{
			"ID" : "441422",
			"PARENT_ID" : "441400",
			"DISTRICT_NAME" : "大埔县",
			"SPELLING" : "Dabu"
		},
		{
			"ID" : "441423",
			"PARENT_ID" : "441400",
			"DISTRICT_NAME" : "丰顺县",
			"SPELLING" : "Fengshun"
		},
		{
			"ID" : "441424",
			"PARENT_ID" : "441400",
			"DISTRICT_NAME" : "五华县",
			"SPELLING" : "Wuhua"
		},
		{
			"ID" : "441426",
			"PARENT_ID" : "441400",
			"DISTRICT_NAME" : "平远县",
			"SPELLING" : "Pingyuan"
		},
		{
			"ID" : "441427",
			"PARENT_ID" : "441400",
			"DISTRICT_NAME" : "蕉岭县",
			"SPELLING" : "Jiaoling"
		},
		{
			"ID" : "441481",
			"PARENT_ID" : "441400",
			"DISTRICT_NAME" : "兴宁市",
			"SPELLING" : "Xingning"
		},
		{
			"ID" : "441500",
			"PARENT_ID" : "440000",
			"DISTRICT_NAME" : "汕尾市",
			"SPELLING" : "Shanwei"
		},
		{
			"ID" : "441502",
			"PARENT_ID" : "441500",
			"DISTRICT_NAME" : "城区",
			"SPELLING" : "Chengqu"
		},
		{
			"ID" : "441521",
			"PARENT_ID" : "441500",
			"DISTRICT_NAME" : "海丰县",
			"SPELLING" : "Haifeng"
		},
		{
			"ID" : "441523",
			"PARENT_ID" : "441500",
			"DISTRICT_NAME" : "陆河县",
			"SPELLING" : "Luhe"
		},
		{
			"ID" : "441581",
			"PARENT_ID" : "441500",
			"DISTRICT_NAME" : "陆丰市",
			"SPELLING" : "Lufeng"
		},
		{
			"ID" : "441600",
			"PARENT_ID" : "440000",
			"DISTRICT_NAME" : "河源市",
			"SPELLING" : "Heyuan"
		},
		{
			"ID" : "441602",
			"PARENT_ID" : "441600",
			"DISTRICT_NAME" : "源城区",
			"SPELLING" : "Yuancheng"
		},
		{
			"ID" : "441621",
			"PARENT_ID" : "441600",
			"DISTRICT_NAME" : "紫金县",
			"SPELLING" : "Zijin"
		},
		{
			"ID" : "441622",
			"PARENT_ID" : "441600",
			"DISTRICT_NAME" : "龙川县",
			"SPELLING" : "Longchuan"
		},
		{
			"ID" : "441623",
			"PARENT_ID" : "441600",
			"DISTRICT_NAME" : "连平县",
			"SPELLING" : "Lianping"
		},
		{
			"ID" : "441624",
			"PARENT_ID" : "441600",
			"DISTRICT_NAME" : "和平县",
			"SPELLING" : "Heping"
		},
		{
			"ID" : "441625",
			"PARENT_ID" : "441600",
			"DISTRICT_NAME" : "东源县",
			"SPELLING" : "Dongyuan"
		},
		{
			"ID" : "441700",
			"PARENT_ID" : "440000",
			"DISTRICT_NAME" : "阳江市",
			"SPELLING" : "Yangjiang"
		},
		{
			"ID" : "441702",
			"PARENT_ID" : "441700",
			"DISTRICT_NAME" : "江城区",
			"SPELLING" : "Jiangcheng"
		},
		{
			"ID" : "441704",
			"PARENT_ID" : "441700",
			"DISTRICT_NAME" : "阳东区",
			"SPELLING" : "Yangdong"
		},
		{
			"ID" : "441721",
			"PARENT_ID" : "441700",
			"DISTRICT_NAME" : "阳西县",
			"SPELLING" : "Yangxi"
		},
		{
			"ID" : "441781",
			"PARENT_ID" : "441700",
			"DISTRICT_NAME" : "阳春市",
			"SPELLING" : "Yangchun"
		},
		{
			"ID" : "441800",
			"PARENT_ID" : "440000",
			"DISTRICT_NAME" : "清远市",
			"SPELLING" : "Qingyuan"
		},
		{
			"ID" : "441802",
			"PARENT_ID" : "441800",
			"DISTRICT_NAME" : "清城区",
			"SPELLING" : "Qingcheng"
		},
		{
			"ID" : "441803",
			"PARENT_ID" : "441800",
			"DISTRICT_NAME" : "清新区",
			"SPELLING" : "Qingxin"
		},
		{
			"ID" : "441821",
			"PARENT_ID" : "441800",
			"DISTRICT_NAME" : "佛冈县",
			"SPELLING" : "Fogang"
		},
		{
			"ID" : "441823",
			"PARENT_ID" : "441800",
			"DISTRICT_NAME" : "阳山县",
			"SPELLING" : "Yangshan"
		},
		{
			"ID" : "441825",
			"PARENT_ID" : "441800",
			"DISTRICT_NAME" : "连山壮族瑶族自治县",
			"SPELLING" : "Lianshan"
		},
		{
			"ID" : "441826",
			"PARENT_ID" : "441800",
			"DISTRICT_NAME" : "连南瑶族自治县",
			"SPELLING" : "Liannan"
		},
		{
			"ID" : "441881",
			"PARENT_ID" : "441800",
			"DISTRICT_NAME" : "英德市",
			"SPELLING" : "Yingde"
		},
		{
			"ID" : "441882",
			"PARENT_ID" : "441800",
			"DISTRICT_NAME" : "连州市",
			"SPELLING" : "Lianzhou"
		},
		{
			"ID" : "441900",
			"PARENT_ID" : "440000",
			"DISTRICT_NAME" : "东莞市",
			"SPELLING" : "Dongguan"
		},
		{
			"ID" : "441901",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "莞城区",
			"SPELLING" : "Guancheng"
		},
		{
			"ID" : "441902",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "南城区",
			"SPELLING" : "Nancheng"
		},
		{
			"ID" : "441904",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "万江区",
			"SPELLING" : "Wanjiang"
		},
		{
			"ID" : "441905",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "石碣镇",
			"SPELLING" : "Shijie"
		},
		{
			"ID" : "441906",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "石龙镇",
			"SPELLING" : "Shilong"
		},
		{
			"ID" : "441907",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "茶山镇",
			"SPELLING" : "Chashan"
		},
		{
			"ID" : "441908",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "石排镇",
			"SPELLING" : "Shipai"
		},
		{
			"ID" : "441909",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "企石镇",
			"SPELLING" : "Qishi"
		},
		{
			"ID" : "441910",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "横沥镇",
			"SPELLING" : "Hengli"
		},
		{
			"ID" : "441911",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "桥头镇",
			"SPELLING" : "Qiaotou"
		},
		{
			"ID" : "441912",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "谢岗镇",
			"SPELLING" : "Xiegang"
		},
		{
			"ID" : "441913",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "东坑镇",
			"SPELLING" : "Dongkeng"
		},
		{
			"ID" : "441914",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "常平镇",
			"SPELLING" : "Changping"
		},
		{
			"ID" : "441915",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "寮步镇",
			"SPELLING" : "Liaobu"
		},
		{
			"ID" : "441916",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "大朗镇",
			"SPELLING" : "Dalang"
		},
		{
			"ID" : "441917",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "麻涌镇",
			"SPELLING" : "Machong"
		},
		{
			"ID" : "441918",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "中堂镇",
			"SPELLING" : "Zhongtang"
		},
		{
			"ID" : "441919",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "高埗镇",
			"SPELLING" : "Gaobu"
		},
		{
			"ID" : "441920",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "樟木头镇",
			"SPELLING" : "Zhangmutou"
		},
		{
			"ID" : "441921",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "大岭山镇",
			"SPELLING" : "Dalingshan"
		},
		{
			"ID" : "441922",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "望牛墩镇",
			"SPELLING" : "Wangniudun"
		},
		{
			"ID" : "441923",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "黄江镇",
			"SPELLING" : "Huangjiang"
		},
		{
			"ID" : "441924",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "洪梅镇",
			"SPELLING" : "Hongmei"
		},
		{
			"ID" : "441925",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "清溪镇",
			"SPELLING" : "Qingxi"
		},
		{
			"ID" : "441926",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "沙田镇",
			"SPELLING" : "Shatian"
		},
		{
			"ID" : "441927",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "道滘镇",
			"SPELLING" : "Daojiao"
		},
		{
			"ID" : "441928",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "塘厦镇",
			"SPELLING" : "Tangxia"
		},
		{
			"ID" : "441929",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "虎门镇",
			"SPELLING" : "Humen"
		},
		{
			"ID" : "441930",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "厚街镇",
			"SPELLING" : "Houjie"
		},
		{
			"ID" : "441931",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "凤岗镇",
			"SPELLING" : "Fenggang"
		},
		{
			"ID" : "441932",
			"PARENT_ID" : "441900",
			"DISTRICT_NAME" : "长安镇",
			"SPELLING" : "Changan"
		},
		{
			"ID" : "442000",
			"PARENT_ID" : "440000",
			"DISTRICT_NAME" : "中山市",
			"SPELLING" : "Zhongshan"
		},
		{
			"ID" : "442001",
			"PARENT_ID" : "442000",
			"DISTRICT_NAME" : "石岐区",
			"SPELLING" : "Shiqi"
		},
		{
			"ID" : "442004",
			"PARENT_ID" : "442000",
			"DISTRICT_NAME" : "南区",
			"SPELLING" : "Nanqu"
		},
		{
			"ID" : "442005",
			"PARENT_ID" : "442000",
			"DISTRICT_NAME" : "五桂山区",
			"SPELLING" : "Wuguishan"
		},
		{
			"ID" : "442006",
			"PARENT_ID" : "442000",
			"DISTRICT_NAME" : "火炬开发区",
			"SPELLING" : "Huoju"
		},
		{
			"ID" : "442007",
			"PARENT_ID" : "442000",
			"DISTRICT_NAME" : "黄圃镇",
			"SPELLING" : "Huangpu"
		},
		{
			"ID" : "442008",
			"PARENT_ID" : "442000",
			"DISTRICT_NAME" : "南头镇",
			"SPELLING" : "Nantou"
		},
		{
			"ID" : "442009",
			"PARENT_ID" : "442000",
			"DISTRICT_NAME" : "东凤镇",
			"SPELLING" : "Dongfeng"
		},
		{
			"ID" : "442010",
			"PARENT_ID" : "442000",
			"DISTRICT_NAME" : "阜沙镇",
			"SPELLING" : "Fusha"
		},
		{
			"ID" : "442011",
			"PARENT_ID" : "442000",
			"DISTRICT_NAME" : "小榄镇",
			"SPELLING" : "Xiaolan"
		},
		{
			"ID" : "442012",
			"PARENT_ID" : "442000",
			"DISTRICT_NAME" : "东升镇",
			"SPELLING" : "Dongsheng"
		},
		{
			"ID" : "442013",
			"PARENT_ID" : "442000",
			"DISTRICT_NAME" : "古镇镇",
			"SPELLING" : "Guzhen"
		},
		{
			"ID" : "442014",
			"PARENT_ID" : "442000",
			"DISTRICT_NAME" : "横栏镇",
			"SPELLING" : "Henglan"
		},
		{
			"ID" : "442015",
			"PARENT_ID" : "442000",
			"DISTRICT_NAME" : "三角镇",
			"SPELLING" : "Sanjiao"
		},
		{
			"ID" : "442016",
			"PARENT_ID" : "442000",
			"DISTRICT_NAME" : "民众镇",
			"SPELLING" : "Minzhong"
		},
		{
			"ID" : "442017",
			"PARENT_ID" : "442000",
			"DISTRICT_NAME" : "南朗镇",
			"SPELLING" : "Nanlang"
		},
		{
			"ID" : "442018",
			"PARENT_ID" : "442000",
			"DISTRICT_NAME" : "港口镇",
			"SPELLING" : "Gangkou"
		},
		{
			"ID" : "442019",
			"PARENT_ID" : "442000",
			"DISTRICT_NAME" : "大涌镇",
			"SPELLING" : "Dayong"
		},
		{
			"ID" : "442020",
			"PARENT_ID" : "442000",
			"DISTRICT_NAME" : "沙溪镇",
			"SPELLING" : "Shaxi"
		},
		{
			"ID" : "442021",
			"PARENT_ID" : "442000",
			"DISTRICT_NAME" : "三乡镇",
			"SPELLING" : "Sanxiang"
		},
		{
			"ID" : "442022",
			"PARENT_ID" : "442000",
			"DISTRICT_NAME" : "板芙镇",
			"SPELLING" : "Banfu"
		},
		{
			"ID" : "442023",
			"PARENT_ID" : "442000",
			"DISTRICT_NAME" : "神湾镇",
			"SPELLING" : "Shenwan"
		},
		{
			"ID" : "442024",
			"PARENT_ID" : "442000",
			"DISTRICT_NAME" : "坦洲镇",
			"SPELLING" : "Tanzhou"
		},
		{
			"ID" : "445100",
			"PARENT_ID" : "440000",
			"DISTRICT_NAME" : "潮州市",
			"SPELLING" : "Chaozhou"
		},
		{
			"ID" : "445102",
			"PARENT_ID" : "445100",
			"DISTRICT_NAME" : "湘桥区",
			"SPELLING" : "Xiangqiao"
		},
		{
			"ID" : "445103",
			"PARENT_ID" : "445100",
			"DISTRICT_NAME" : "潮安区",
			"SPELLING" : "Chaoan"
		},
		{
			"ID" : "445122",
			"PARENT_ID" : "445100",
			"DISTRICT_NAME" : "饶平县",
			"SPELLING" : "Raoping"
		},
		{
			"ID" : "445200",
			"PARENT_ID" : "440000",
			"DISTRICT_NAME" : "揭阳市",
			"SPELLING" : "Jieyang"
		},
		{
			"ID" : "445202",
			"PARENT_ID" : "445200",
			"DISTRICT_NAME" : "榕城区",
			"SPELLING" : "Rongcheng"
		},
		{
			"ID" : "445203",
			"PARENT_ID" : "445200",
			"DISTRICT_NAME" : "揭东区",
			"SPELLING" : "Jiedong"
		},
		{
			"ID" : "445222",
			"PARENT_ID" : "445200",
			"DISTRICT_NAME" : "揭西县",
			"SPELLING" : "Jiexi"
		},
		{
			"ID" : "445224",
			"PARENT_ID" : "445200",
			"DISTRICT_NAME" : "惠来县",
			"SPELLING" : "Huilai"
		},
		{
			"ID" : "445281",
			"PARENT_ID" : "445200",
			"DISTRICT_NAME" : "普宁市",
			"SPELLING" : "Puning"
		},
		{
			"ID" : "445300",
			"PARENT_ID" : "440000",
			"DISTRICT_NAME" : "云浮市",
			"SPELLING" : "Yunfu"
		},
		{
			"ID" : "445302",
			"PARENT_ID" : "445300",
			"DISTRICT_NAME" : "云城区",
			"SPELLING" : "Yuncheng"
		},
		{
			"ID" : "445303",
			"PARENT_ID" : "445300",
			"DISTRICT_NAME" : "云安区",
			"SPELLING" : "Yunan"
		},
		{
			"ID" : "445321",
			"PARENT_ID" : "445300",
			"DISTRICT_NAME" : "新兴县",
			"SPELLING" : "Xinxing"
		},
		{
			"ID" : "445322",
			"PARENT_ID" : "445300",
			"DISTRICT_NAME" : "郁南县",
			"SPELLING" : "Yunan"
		},
		{
			"ID" : "445381",
			"PARENT_ID" : "445300",
			"DISTRICT_NAME" : "罗定市",
			"SPELLING" : "Luoding"
		},
		{
			"ID" : "450000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "广西壮族自治区",
			"SPELLING" : "Guangxi"
		},
		{
			"ID" : "450100",
			"PARENT_ID" : "450000",
			"DISTRICT_NAME" : "南宁市",
			"SPELLING" : "Nanning"
		},
		{
			"ID" : "450102",
			"PARENT_ID" : "450100",
			"DISTRICT_NAME" : "兴宁区",
			"SPELLING" : "Xingning"
		},
		{
			"ID" : "450103",
			"PARENT_ID" : "450100",
			"DISTRICT_NAME" : "青秀区",
			"SPELLING" : "Qingxiu"
		},
		{
			"ID" : "450105",
			"PARENT_ID" : "450100",
			"DISTRICT_NAME" : "江南区",
			"SPELLING" : "Jiangnan"
		},
		{
			"ID" : "450107",
			"PARENT_ID" : "450100",
			"DISTRICT_NAME" : "西乡塘区",
			"SPELLING" : "Xixiangtang"
		},
		{
			"ID" : "450108",
			"PARENT_ID" : "450100",
			"DISTRICT_NAME" : "良庆区",
			"SPELLING" : "Liangqing"
		},
		{
			"ID" : "450109",
			"PARENT_ID" : "450100",
			"DISTRICT_NAME" : "邕宁区",
			"SPELLING" : "Yongning"
		},
		{
			"ID" : "450122",
			"PARENT_ID" : "450100",
			"DISTRICT_NAME" : "武鸣县",
			"SPELLING" : "Wuming"
		},
		{
			"ID" : "450123",
			"PARENT_ID" : "450100",
			"DISTRICT_NAME" : "隆安县",
			"SPELLING" : "Longan"
		},
		{
			"ID" : "450124",
			"PARENT_ID" : "450100",
			"DISTRICT_NAME" : "马山县",
			"SPELLING" : "Mashan"
		},
		{
			"ID" : "450125",
			"PARENT_ID" : "450100",
			"DISTRICT_NAME" : "上林县",
			"SPELLING" : "Shanglin"
		},
		{
			"ID" : "450126",
			"PARENT_ID" : "450100",
			"DISTRICT_NAME" : "宾阳县",
			"SPELLING" : "Binyang"
		},
		{
			"ID" : "450127",
			"PARENT_ID" : "450100",
			"DISTRICT_NAME" : "横县",
			"SPELLING" : "Hengxian"
		},
		{
			"ID" : "450128",
			"PARENT_ID" : "450100",
			"DISTRICT_NAME" : "埌东新区",
			"SPELLING" : "Langdong"
		},
		{
			"ID" : "450200",
			"PARENT_ID" : "450000",
			"DISTRICT_NAME" : "柳州市",
			"SPELLING" : "Liuzhou"
		},
		{
			"ID" : "450202",
			"PARENT_ID" : "450200",
			"DISTRICT_NAME" : "城中区",
			"SPELLING" : "Chengzhong"
		},
		{
			"ID" : "450203",
			"PARENT_ID" : "450200",
			"DISTRICT_NAME" : "鱼峰区",
			"SPELLING" : "Yufeng"
		},
		{
			"ID" : "450204",
			"PARENT_ID" : "450200",
			"DISTRICT_NAME" : "柳南区",
			"SPELLING" : "Liunan"
		},
		{
			"ID" : "450205",
			"PARENT_ID" : "450200",
			"DISTRICT_NAME" : "柳北区",
			"SPELLING" : "Liubei"
		},
		{
			"ID" : "450221",
			"PARENT_ID" : "450200",
			"DISTRICT_NAME" : "柳江县",
			"SPELLING" : "Liujiang"
		},
		{
			"ID" : "450222",
			"PARENT_ID" : "450200",
			"DISTRICT_NAME" : "柳城县",
			"SPELLING" : "Liucheng"
		},
		{
			"ID" : "450223",
			"PARENT_ID" : "450200",
			"DISTRICT_NAME" : "鹿寨县",
			"SPELLING" : "Luzhai"
		},
		{
			"ID" : "450224",
			"PARENT_ID" : "450200",
			"DISTRICT_NAME" : "融安县",
			"SPELLING" : "Rongan"
		},
		{
			"ID" : "450225",
			"PARENT_ID" : "450200",
			"DISTRICT_NAME" : "融水苗族自治县",
			"SPELLING" : "Rongshui"
		},
		{
			"ID" : "450226",
			"PARENT_ID" : "450200",
			"DISTRICT_NAME" : "三江侗族自治县",
			"SPELLING" : "Sanjiang"
		},
		{
			"ID" : "450227",
			"PARENT_ID" : "450200",
			"DISTRICT_NAME" : "柳东新区",
			"SPELLING" : "Liudong"
		},
		{
			"ID" : "450300",
			"PARENT_ID" : "450000",
			"DISTRICT_NAME" : "桂林市",
			"SPELLING" : "Guilin"
		},
		{
			"ID" : "450302",
			"PARENT_ID" : "450300",
			"DISTRICT_NAME" : "秀峰区",
			"SPELLING" : "Xiufeng"
		},
		{
			"ID" : "450303",
			"PARENT_ID" : "450300",
			"DISTRICT_NAME" : "叠彩区",
			"SPELLING" : "Diecai"
		},
		{
			"ID" : "450304",
			"PARENT_ID" : "450300",
			"DISTRICT_NAME" : "象山区",
			"SPELLING" : "Xiangshan"
		},
		{
			"ID" : "450305",
			"PARENT_ID" : "450300",
			"DISTRICT_NAME" : "七星区",
			"SPELLING" : "Qixing"
		},
		{
			"ID" : "450311",
			"PARENT_ID" : "450300",
			"DISTRICT_NAME" : "雁山区",
			"SPELLING" : "Yanshan"
		},
		{
			"ID" : "450312",
			"PARENT_ID" : "450300",
			"DISTRICT_NAME" : "临桂区",
			"SPELLING" : "Lingui"
		},
		{
			"ID" : "450321",
			"PARENT_ID" : "450300",
			"DISTRICT_NAME" : "阳朔县",
			"SPELLING" : "Yangshuo"
		},
		{
			"ID" : "450323",
			"PARENT_ID" : "450300",
			"DISTRICT_NAME" : "灵川县",
			"SPELLING" : "Lingchuan"
		},
		{
			"ID" : "450324",
			"PARENT_ID" : "450300",
			"DISTRICT_NAME" : "全州县",
			"SPELLING" : "Quanzhou"
		},
		{
			"ID" : "450325",
			"PARENT_ID" : "450300",
			"DISTRICT_NAME" : "兴安县",
			"SPELLING" : "Xingan"
		},
		{
			"ID" : "450326",
			"PARENT_ID" : "450300",
			"DISTRICT_NAME" : "永福县",
			"SPELLING" : "Yongfu"
		},
		{
			"ID" : "450327",
			"PARENT_ID" : "450300",
			"DISTRICT_NAME" : "灌阳县",
			"SPELLING" : "Guanyang"
		},
		{
			"ID" : "450328",
			"PARENT_ID" : "450300",
			"DISTRICT_NAME" : "龙胜各族自治县",
			"SPELLING" : "Longsheng"
		},
		{
			"ID" : "450329",
			"PARENT_ID" : "450300",
			"DISTRICT_NAME" : "资源县",
			"SPELLING" : "Ziyuan"
		},
		{
			"ID" : "450330",
			"PARENT_ID" : "450300",
			"DISTRICT_NAME" : "平乐县",
			"SPELLING" : "Pingle"
		},
		{
			"ID" : "450331",
			"PARENT_ID" : "450300",
			"DISTRICT_NAME" : "荔浦县",
			"SPELLING" : "Lipu"
		},
		{
			"ID" : "450332",
			"PARENT_ID" : "450300",
			"DISTRICT_NAME" : "恭城瑶族自治县",
			"SPELLING" : "Gongcheng"
		},
		{
			"ID" : "450400",
			"PARENT_ID" : "450000",
			"DISTRICT_NAME" : "梧州市",
			"SPELLING" : "Wuzhou"
		},
		{
			"ID" : "450403",
			"PARENT_ID" : "450400",
			"DISTRICT_NAME" : "万秀区",
			"SPELLING" : "Wanxiu"
		},
		{
			"ID" : "450405",
			"PARENT_ID" : "450400",
			"DISTRICT_NAME" : "长洲区",
			"SPELLING" : "Changzhou"
		},
		{
			"ID" : "450406",
			"PARENT_ID" : "450400",
			"DISTRICT_NAME" : "龙圩区",
			"SPELLING" : "Longxu"
		},
		{
			"ID" : "450421",
			"PARENT_ID" : "450400",
			"DISTRICT_NAME" : "苍梧县",
			"SPELLING" : "Cangwu"
		},
		{
			"ID" : "450422",
			"PARENT_ID" : "450400",
			"DISTRICT_NAME" : "藤县",
			"SPELLING" : "Tengxian"
		},
		{
			"ID" : "450423",
			"PARENT_ID" : "450400",
			"DISTRICT_NAME" : "蒙山县",
			"SPELLING" : "Mengshan"
		},
		{
			"ID" : "450481",
			"PARENT_ID" : "450400",
			"DISTRICT_NAME" : "岑溪市",
			"SPELLING" : "Cenxi"
		},
		{
			"ID" : "450500",
			"PARENT_ID" : "450000",
			"DISTRICT_NAME" : "北海市",
			"SPELLING" : "Beihai"
		},
		{
			"ID" : "450502",
			"PARENT_ID" : "450500",
			"DISTRICT_NAME" : "海城区",
			"SPELLING" : "Haicheng"
		},
		{
			"ID" : "450503",
			"PARENT_ID" : "450500",
			"DISTRICT_NAME" : "银海区",
			"SPELLING" : "Yinhai"
		},
		{
			"ID" : "450512",
			"PARENT_ID" : "450500",
			"DISTRICT_NAME" : "铁山港区",
			"SPELLING" : "Tieshangang"
		},
		{
			"ID" : "450521",
			"PARENT_ID" : "450500",
			"DISTRICT_NAME" : "合浦县",
			"SPELLING" : "Hepu"
		},
		{
			"ID" : "450600",
			"PARENT_ID" : "450000",
			"DISTRICT_NAME" : "防城港市",
			"SPELLING" : "Fangchenggang"
		},
		{
			"ID" : "450602",
			"PARENT_ID" : "450600",
			"DISTRICT_NAME" : "港口区",
			"SPELLING" : "Gangkou"
		},
		{
			"ID" : "450603",
			"PARENT_ID" : "450600",
			"DISTRICT_NAME" : "防城区",
			"SPELLING" : "Fangcheng"
		},
		{
			"ID" : "450621",
			"PARENT_ID" : "450600",
			"DISTRICT_NAME" : "上思县",
			"SPELLING" : "Shangsi"
		},
		{
			"ID" : "450681",
			"PARENT_ID" : "450600",
			"DISTRICT_NAME" : "东兴市",
			"SPELLING" : "Dongxing"
		},
		{
			"ID" : "450700",
			"PARENT_ID" : "450000",
			"DISTRICT_NAME" : "钦州市",
			"SPELLING" : "Qinzhou"
		},
		{
			"ID" : "450702",
			"PARENT_ID" : "450700",
			"DISTRICT_NAME" : "钦南区",
			"SPELLING" : "Qinnan"
		},
		{
			"ID" : "450703",
			"PARENT_ID" : "450700",
			"DISTRICT_NAME" : "钦北区",
			"SPELLING" : "Qinbei"
		},
		{
			"ID" : "450721",
			"PARENT_ID" : "450700",
			"DISTRICT_NAME" : "灵山县",
			"SPELLING" : "Lingshan"
		},
		{
			"ID" : "450722",
			"PARENT_ID" : "450700",
			"DISTRICT_NAME" : "浦北县",
			"SPELLING" : "Pubei"
		},
		{
			"ID" : "450800",
			"PARENT_ID" : "450000",
			"DISTRICT_NAME" : "贵港市",
			"SPELLING" : "Guigang"
		},
		{
			"ID" : "450802",
			"PARENT_ID" : "450800",
			"DISTRICT_NAME" : "港北区",
			"SPELLING" : "Gangbei"
		},
		{
			"ID" : "450803",
			"PARENT_ID" : "450800",
			"DISTRICT_NAME" : "港南区",
			"SPELLING" : "Gangnan"
		},
		{
			"ID" : "450804",
			"PARENT_ID" : "450800",
			"DISTRICT_NAME" : "覃塘区",
			"SPELLING" : "Qintang"
		},
		{
			"ID" : "450821",
			"PARENT_ID" : "450800",
			"DISTRICT_NAME" : "平南县",
			"SPELLING" : "Pingnan"
		},
		{
			"ID" : "450881",
			"PARENT_ID" : "450800",
			"DISTRICT_NAME" : "桂平市",
			"SPELLING" : "Guiping"
		},
		{
			"ID" : "450900",
			"PARENT_ID" : "450000",
			"DISTRICT_NAME" : "玉林市",
			"SPELLING" : "Yulin"
		},
		{
			"ID" : "450902",
			"PARENT_ID" : "450900",
			"DISTRICT_NAME" : "玉州区",
			"SPELLING" : "Yuzhou"
		},
		{
			"ID" : "450903",
			"PARENT_ID" : "450900",
			"DISTRICT_NAME" : "福绵区",
			"SPELLING" : "Fumian"
		},
		{
			"ID" : "450904",
			"PARENT_ID" : "450900",
			"DISTRICT_NAME" : "玉东新区",
			"SPELLING" : "Yudong"
		},
		{
			"ID" : "450921",
			"PARENT_ID" : "450900",
			"DISTRICT_NAME" : "容县",
			"SPELLING" : "Rongxian"
		},
		{
			"ID" : "450922",
			"PARENT_ID" : "450900",
			"DISTRICT_NAME" : "陆川县",
			"SPELLING" : "Luchuan"
		},
		{
			"ID" : "450923",
			"PARENT_ID" : "450900",
			"DISTRICT_NAME" : "博白县",
			"SPELLING" : "Bobai"
		},
		{
			"ID" : "450924",
			"PARENT_ID" : "450900",
			"DISTRICT_NAME" : "兴业县",
			"SPELLING" : "Xingye"
		},
		{
			"ID" : "450981",
			"PARENT_ID" : "450900",
			"DISTRICT_NAME" : "北流市",
			"SPELLING" : "Beiliu"
		},
		{
			"ID" : "451000",
			"PARENT_ID" : "450000",
			"DISTRICT_NAME" : "百色市",
			"SPELLING" : "Baise"
		},
		{
			"ID" : "451002",
			"PARENT_ID" : "451000",
			"DISTRICT_NAME" : "右江区",
			"SPELLING" : "Youjiang"
		},
		{
			"ID" : "451021",
			"PARENT_ID" : "451000",
			"DISTRICT_NAME" : "田阳县",
			"SPELLING" : "Tianyang"
		},
		{
			"ID" : "451022",
			"PARENT_ID" : "451000",
			"DISTRICT_NAME" : "田东县",
			"SPELLING" : "Tiandong"
		},
		{
			"ID" : "451023",
			"PARENT_ID" : "451000",
			"DISTRICT_NAME" : "平果县",
			"SPELLING" : "Pingguo"
		},
		{
			"ID" : "451024",
			"PARENT_ID" : "451000",
			"DISTRICT_NAME" : "德保县",
			"SPELLING" : "Debao"
		},
		{
			"ID" : "451025",
			"PARENT_ID" : "451000",
			"DISTRICT_NAME" : "靖西县",
			"SPELLING" : "Jingxi"
		},
		{
			"ID" : "451026",
			"PARENT_ID" : "451000",
			"DISTRICT_NAME" : "那坡县",
			"SPELLING" : "Napo"
		},
		{
			"ID" : "451027",
			"PARENT_ID" : "451000",
			"DISTRICT_NAME" : "凌云县",
			"SPELLING" : "Lingyun"
		},
		{
			"ID" : "451028",
			"PARENT_ID" : "451000",
			"DISTRICT_NAME" : "乐业县",
			"SPELLING" : "Leye"
		},
		{
			"ID" : "451029",
			"PARENT_ID" : "451000",
			"DISTRICT_NAME" : "田林县",
			"SPELLING" : "Tianlin"
		},
		{
			"ID" : "451030",
			"PARENT_ID" : "451000",
			"DISTRICT_NAME" : "西林县",
			"SPELLING" : "Xilin"
		},
		{
			"ID" : "451031",
			"PARENT_ID" : "451000",
			"DISTRICT_NAME" : "隆林各族自治县",
			"SPELLING" : "Longlin"
		},
		{
			"ID" : "451100",
			"PARENT_ID" : "450000",
			"DISTRICT_NAME" : "贺州市",
			"SPELLING" : "Hezhou"
		},
		{
			"ID" : "451102",
			"PARENT_ID" : "451100",
			"DISTRICT_NAME" : "八步区",
			"SPELLING" : "Babu"
		},
		{
			"ID" : "451121",
			"PARENT_ID" : "451100",
			"DISTRICT_NAME" : "昭平县",
			"SPELLING" : "Zhaoping"
		},
		{
			"ID" : "451122",
			"PARENT_ID" : "451100",
			"DISTRICT_NAME" : "钟山县",
			"SPELLING" : "Zhongshan"
		},
		{
			"ID" : "451123",
			"PARENT_ID" : "451100",
			"DISTRICT_NAME" : "富川瑶族自治县",
			"SPELLING" : "Fuchuan"
		},
		{
			"ID" : "451124",
			"PARENT_ID" : "451100",
			"DISTRICT_NAME" : "平桂管理区",
			"SPELLING" : "Pingui"
		},
		{
			"ID" : "451200",
			"PARENT_ID" : "450000",
			"DISTRICT_NAME" : "河池市",
			"SPELLING" : "Hechi"
		},
		{
			"ID" : "451202",
			"PARENT_ID" : "451200",
			"DISTRICT_NAME" : "金城江区",
			"SPELLING" : "Jinchengjiang"
		},
		{
			"ID" : "451221",
			"PARENT_ID" : "451200",
			"DISTRICT_NAME" : "南丹县",
			"SPELLING" : "Nandan"
		},
		{
			"ID" : "451222",
			"PARENT_ID" : "451200",
			"DISTRICT_NAME" : "天峨县",
			"SPELLING" : "Tiane"
		},
		{
			"ID" : "451223",
			"PARENT_ID" : "451200",
			"DISTRICT_NAME" : "凤山县",
			"SPELLING" : "Fengshan"
		},
		{
			"ID" : "451224",
			"PARENT_ID" : "451200",
			"DISTRICT_NAME" : "东兰县",
			"SPELLING" : "Donglan"
		},
		{
			"ID" : "451225",
			"PARENT_ID" : "451200",
			"DISTRICT_NAME" : "罗城仫佬族自治县",
			"SPELLING" : "Luocheng"
		},
		{
			"ID" : "451226",
			"PARENT_ID" : "451200",
			"DISTRICT_NAME" : "环江毛南族自治县",
			"SPELLING" : "Huanjiang"
		},
		{
			"ID" : "451227",
			"PARENT_ID" : "451200",
			"DISTRICT_NAME" : "巴马瑶族自治县",
			"SPELLING" : "Bama"
		},
		{
			"ID" : "451228",
			"PARENT_ID" : "451200",
			"DISTRICT_NAME" : "都安瑶族自治县",
			"SPELLING" : "Duan"
		},
		{
			"ID" : "451229",
			"PARENT_ID" : "451200",
			"DISTRICT_NAME" : "大化瑶族自治县",
			"SPELLING" : "Dahua"
		},
		{
			"ID" : "451281",
			"PARENT_ID" : "451200",
			"DISTRICT_NAME" : "宜州市",
			"SPELLING" : "Yizhou"
		},
		{
			"ID" : "451300",
			"PARENT_ID" : "450000",
			"DISTRICT_NAME" : "来宾市",
			"SPELLING" : "Laibin"
		},
		{
			"ID" : "451302",
			"PARENT_ID" : "451300",
			"DISTRICT_NAME" : "兴宾区",
			"SPELLING" : "Xingbin"
		},
		{
			"ID" : "451321",
			"PARENT_ID" : "451300",
			"DISTRICT_NAME" : "忻城县",
			"SPELLING" : "Xincheng"
		},
		{
			"ID" : "451322",
			"PARENT_ID" : "451300",
			"DISTRICT_NAME" : "象州县",
			"SPELLING" : "Xiangzhou"
		},
		{
			"ID" : "451323",
			"PARENT_ID" : "451300",
			"DISTRICT_NAME" : "武宣县",
			"SPELLING" : "Wuxuan"
		},
		{
			"ID" : "451324",
			"PARENT_ID" : "451300",
			"DISTRICT_NAME" : "金秀瑶族自治县",
			"SPELLING" : "Jinxiu"
		},
		{
			"ID" : "451381",
			"PARENT_ID" : "451300",
			"DISTRICT_NAME" : "合山市",
			"SPELLING" : "Heshan"
		},
		{
			"ID" : "451400",
			"PARENT_ID" : "450000",
			"DISTRICT_NAME" : "崇左市",
			"SPELLING" : "Chongzuo"
		},
		{
			"ID" : "451402",
			"PARENT_ID" : "451400",
			"DISTRICT_NAME" : "江州区",
			"SPELLING" : "Jiangzhou"
		},
		{
			"ID" : "451421",
			"PARENT_ID" : "451400",
			"DISTRICT_NAME" : "扶绥县",
			"SPELLING" : "Fusui"
		},
		{
			"ID" : "451422",
			"PARENT_ID" : "451400",
			"DISTRICT_NAME" : "宁明县",
			"SPELLING" : "Ningming"
		},
		{
			"ID" : "451423",
			"PARENT_ID" : "451400",
			"DISTRICT_NAME" : "龙州县",
			"SPELLING" : "Longzhou"
		},
		{
			"ID" : "451424",
			"PARENT_ID" : "451400",
			"DISTRICT_NAME" : "大新县",
			"SPELLING" : "Daxin"
		},
		{
			"ID" : "451425",
			"PARENT_ID" : "451400",
			"DISTRICT_NAME" : "天等县",
			"SPELLING" : "Tiandeng"
		},
		{
			"ID" : "451481",
			"PARENT_ID" : "451400",
			"DISTRICT_NAME" : "凭祥市",
			"SPELLING" : "Pingxiang"
		},
		{
			"ID" : "460000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "海南省",
			"SPELLING" : "Hainan"
		},
		{
			"ID" : "460100",
			"PARENT_ID" : "460000",
			"DISTRICT_NAME" : "海口市",
			"SPELLING" : "Haikou"
		},
		{
			"ID" : "460105",
			"PARENT_ID" : "460100",
			"DISTRICT_NAME" : "秀英区",
			"SPELLING" : "Xiuying"
		},
		{
			"ID" : "460106",
			"PARENT_ID" : "460100",
			"DISTRICT_NAME" : "龙华区",
			"SPELLING" : "Longhua"
		},
		{
			"ID" : "460107",
			"PARENT_ID" : "460100",
			"DISTRICT_NAME" : "琼山区",
			"SPELLING" : "Qiongshan"
		},
		{
			"ID" : "460108",
			"PARENT_ID" : "460100",
			"DISTRICT_NAME" : "美兰区",
			"SPELLING" : "Meilan"
		},
		{
			"ID" : "460200",
			"PARENT_ID" : "460000",
			"DISTRICT_NAME" : "三亚市",
			"SPELLING" : "Sanya"
		},
		{
			"ID" : "460202",
			"PARENT_ID" : "460200",
			"DISTRICT_NAME" : "海棠区",
			"SPELLING" : "Haitang"
		},
		{
			"ID" : "460203",
			"PARENT_ID" : "460200",
			"DISTRICT_NAME" : "吉阳区",
			"SPELLING" : "Jiyang"
		},
		{
			"ID" : "460204",
			"PARENT_ID" : "460200",
			"DISTRICT_NAME" : "天涯区",
			"SPELLING" : "Tianya"
		},
		{
			"ID" : "460205",
			"PARENT_ID" : "460200",
			"DISTRICT_NAME" : "崖州区",
			"SPELLING" : "Yazhou"
		},
		{
			"ID" : "460300",
			"PARENT_ID" : "460000",
			"DISTRICT_NAME" : "三沙市",
			"SPELLING" : "Sansha"
		},
		{
			"ID" : "460321",
			"PARENT_ID" : "460300",
			"DISTRICT_NAME" : "西沙群岛",
			"SPELLING" : "Xisha Islands"
		},
		{
			"ID" : "460322",
			"PARENT_ID" : "460300",
			"DISTRICT_NAME" : "南沙群岛",
			"SPELLING" : "Nansha Islands"
		},
		{
			"ID" : "460323",
			"PARENT_ID" : "460300",
			"DISTRICT_NAME" : "中沙群岛",
			"SPELLING" : "Zhongsha Islands"
		},
		{
			"ID" : "500000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "重庆",
			"SPELLING" : "Chongqing"
		},
		{
			"ID" : "500100",
			"PARENT_ID" : "500000",
			"DISTRICT_NAME" : "重庆市",
			"SPELLING" : "Chongqing"
		},
		{
			"ID" : "500101",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "万州区",
			"SPELLING" : "Wanzhou"
		},
		{
			"ID" : "500102",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "涪陵区",
			"SPELLING" : "Fuling"
		},
		{
			"ID" : "500103",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "渝中区",
			"SPELLING" : "Yuzhong"
		},
		{
			"ID" : "500104",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "大渡口区",
			"SPELLING" : "Dadukou"
		},
		{
			"ID" : "500105",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "江北区",
			"SPELLING" : "Jiangbei"
		},
		{
			"ID" : "500106",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "沙坪坝区",
			"SPELLING" : "Shapingba"
		},
		{
			"ID" : "500107",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "九龙坡区",
			"SPELLING" : "Jiulongpo"
		},
		{
			"ID" : "500108",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "南岸区",
			"SPELLING" : "Nanan"
		},
		{
			"ID" : "500109",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "北碚区",
			"SPELLING" : "Beibei"
		},
		{
			"ID" : "500110",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "綦江区",
			"SPELLING" : "Qijiang"
		},
		{
			"ID" : "500111",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "大足区",
			"SPELLING" : "Dazu"
		},
		{
			"ID" : "500112",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "渝北区",
			"SPELLING" : "Yubei"
		},
		{
			"ID" : "500113",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "巴南区",
			"SPELLING" : "Banan"
		},
		{
			"ID" : "500114",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "黔江区",
			"SPELLING" : "Qianjiang"
		},
		{
			"ID" : "500115",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "长寿区",
			"SPELLING" : "Changshou"
		},
		{
			"ID" : "500116",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "江津区",
			"SPELLING" : "Jiangjin"
		},
		{
			"ID" : "500117",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "合川区",
			"SPELLING" : "Hechuan"
		},
		{
			"ID" : "500118",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "永川区",
			"SPELLING" : "Yongchuan"
		},
		{
			"ID" : "500119",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "南川区",
			"SPELLING" : "Nanchuan"
		},
		{
			"ID" : "500120",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "璧山区",
			"SPELLING" : "Bishan"
		},
		{
			"ID" : "500151",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "铜梁区",
			"SPELLING" : "Tongliang"
		},
		{
			"ID" : "500223",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "潼南县",
			"SPELLING" : "Tongnan"
		},
		{
			"ID" : "500226",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "荣昌县",
			"SPELLING" : "Rongchang"
		},
		{
			"ID" : "500228",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "梁平县",
			"SPELLING" : "Liangping"
		},
		{
			"ID" : "500229",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "城口县",
			"SPELLING" : "Chengkou"
		},
		{
			"ID" : "500230",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "丰都县",
			"SPELLING" : "Fengdu"
		},
		{
			"ID" : "500231",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "垫江县",
			"SPELLING" : "Dianjiang"
		},
		{
			"ID" : "500232",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "武隆县",
			"SPELLING" : "Wulong"
		},
		{
			"ID" : "500233",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "忠县",
			"SPELLING" : "Zhongxian"
		},
		{
			"ID" : "500234",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "开县",
			"SPELLING" : "Kaixian"
		},
		{
			"ID" : "500235",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "云阳县",
			"SPELLING" : "Yunyang"
		},
		{
			"ID" : "500236",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "奉节县",
			"SPELLING" : "Fengjie"
		},
		{
			"ID" : "500237",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "巫山县",
			"SPELLING" : "Wushan"
		},
		{
			"ID" : "500238",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "巫溪县",
			"SPELLING" : "Wuxi"
		},
		{
			"ID" : "500240",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "石柱土家族自治县",
			"SPELLING" : "Shizhu"
		},
		{
			"ID" : "500241",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "秀山土家族苗族自治县",
			"SPELLING" : "Xiushan"
		},
		{
			"ID" : "500242",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "酉阳土家族苗族自治县",
			"SPELLING" : "Youyang"
		},
		{
			"ID" : "500243",
			"PARENT_ID" : "500100",
			"DISTRICT_NAME" : "彭水苗族土家族自治县",
			"SPELLING" : "Pengshui"
		},
		{
			"ID" : "500300",
			"PARENT_ID" : "500000",
			"DISTRICT_NAME" : "两江新区",
			"SPELLING" : "Liangjiangxinqu"
		},
		{
			"ID" : "500301",
			"PARENT_ID" : "500300",
			"DISTRICT_NAME" : "北部新区",
			"SPELLING" : "Beibuxinqu"
		},
		{
			"ID" : "500302",
			"PARENT_ID" : "500300",
			"DISTRICT_NAME" : "保税港区",
			"SPELLING" : "Baoshuigangqu"
		},
		{
			"ID" : "500303",
			"PARENT_ID" : "500300",
			"DISTRICT_NAME" : "工业园区",
			"SPELLING" : "Gongyeyuanqu"
		},
		{
			"ID" : "510000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "四川省",
			"SPELLING" : "Sichuan"
		},
		{
			"ID" : "510100",
			"PARENT_ID" : "510000",
			"DISTRICT_NAME" : "成都市",
			"SPELLING" : "Chengdu"
		},
		{
			"ID" : "510104",
			"PARENT_ID" : "510100",
			"DISTRICT_NAME" : "锦江区",
			"SPELLING" : "Jinjiang"
		},
		{
			"ID" : "510105",
			"PARENT_ID" : "510100",
			"DISTRICT_NAME" : "青羊区",
			"SPELLING" : "Qingyang"
		},
		{
			"ID" : "510106",
			"PARENT_ID" : "510100",
			"DISTRICT_NAME" : "金牛区",
			"SPELLING" : "Jinniu"
		},
		{
			"ID" : "510107",
			"PARENT_ID" : "510100",
			"DISTRICT_NAME" : "武侯区",
			"SPELLING" : "Wuhou"
		},
		{
			"ID" : "510108",
			"PARENT_ID" : "510100",
			"DISTRICT_NAME" : "成华区",
			"SPELLING" : "Chenghua"
		},
		{
			"ID" : "510112",
			"PARENT_ID" : "510100",
			"DISTRICT_NAME" : "龙泉驿区",
			"SPELLING" : "Longquanyi"
		},
		{
			"ID" : "510113",
			"PARENT_ID" : "510100",
			"DISTRICT_NAME" : "青白江区",
			"SPELLING" : "Qingbaijiang"
		},
		{
			"ID" : "510114",
			"PARENT_ID" : "510100",
			"DISTRICT_NAME" : "新都区",
			"SPELLING" : "Xindu"
		},
		{
			"ID" : "510115",
			"PARENT_ID" : "510100",
			"DISTRICT_NAME" : "温江区",
			"SPELLING" : "Wenjiang"
		},
		{
			"ID" : "510121",
			"PARENT_ID" : "510100",
			"DISTRICT_NAME" : "金堂县",
			"SPELLING" : "Jintang"
		},
		{
			"ID" : "510122",
			"PARENT_ID" : "510100",
			"DISTRICT_NAME" : "双流县",
			"SPELLING" : "Shuangliu"
		},
		{
			"ID" : "510124",
			"PARENT_ID" : "510100",
			"DISTRICT_NAME" : "郫县",
			"SPELLING" : "Pixian"
		},
		{
			"ID" : "510129",
			"PARENT_ID" : "510100",
			"DISTRICT_NAME" : "大邑县",
			"SPELLING" : "Dayi"
		},
		{
			"ID" : "510131",
			"PARENT_ID" : "510100",
			"DISTRICT_NAME" : "蒲江县",
			"SPELLING" : "Pujiang"
		},
		{
			"ID" : "510132",
			"PARENT_ID" : "510100",
			"DISTRICT_NAME" : "新津县",
			"SPELLING" : "Xinjin"
		},
		{
			"ID" : "510181",
			"PARENT_ID" : "510100",
			"DISTRICT_NAME" : "都江堰市",
			"SPELLING" : "Dujiangyan"
		},
		{
			"ID" : "510182",
			"PARENT_ID" : "510100",
			"DISTRICT_NAME" : "彭州市",
			"SPELLING" : "Pengzhou"
		},
		{
			"ID" : "510183",
			"PARENT_ID" : "510100",
			"DISTRICT_NAME" : "邛崃市",
			"SPELLING" : "Qionglai"
		},
		{
			"ID" : "510184",
			"PARENT_ID" : "510100",
			"DISTRICT_NAME" : "崇州市",
			"SPELLING" : "Chongzhou"
		},
		{
			"ID" : "510300",
			"PARENT_ID" : "510000",
			"DISTRICT_NAME" : "自贡市",
			"SPELLING" : "Zigong"
		},
		{
			"ID" : "510302",
			"PARENT_ID" : "510300",
			"DISTRICT_NAME" : "自流井区",
			"SPELLING" : "Ziliujing"
		},
		{
			"ID" : "510303",
			"PARENT_ID" : "510300",
			"DISTRICT_NAME" : "贡井区",
			"SPELLING" : "Gongjing"
		},
		{
			"ID" : "510304",
			"PARENT_ID" : "510300",
			"DISTRICT_NAME" : "大安区",
			"SPELLING" : "Daan"
		},
		{
			"ID" : "510311",
			"PARENT_ID" : "510300",
			"DISTRICT_NAME" : "沿滩区",
			"SPELLING" : "Yantan"
		},
		{
			"ID" : "510321",
			"PARENT_ID" : "510300",
			"DISTRICT_NAME" : "荣县",
			"SPELLING" : "Rongxian"
		},
		{
			"ID" : "510322",
			"PARENT_ID" : "510300",
			"DISTRICT_NAME" : "富顺县",
			"SPELLING" : "Fushun"
		},
		{
			"ID" : "510400",
			"PARENT_ID" : "510000",
			"DISTRICT_NAME" : "攀枝花市",
			"SPELLING" : "Panzhihua"
		},
		{
			"ID" : "510402",
			"PARENT_ID" : "510400",
			"DISTRICT_NAME" : "东区",
			"SPELLING" : "Dongqu"
		},
		{
			"ID" : "510403",
			"PARENT_ID" : "510400",
			"DISTRICT_NAME" : "西区",
			"SPELLING" : "Xiqu"
		},
		{
			"ID" : "510411",
			"PARENT_ID" : "510400",
			"DISTRICT_NAME" : "仁和区",
			"SPELLING" : "Renhe"
		},
		{
			"ID" : "510421",
			"PARENT_ID" : "510400",
			"DISTRICT_NAME" : "米易县",
			"SPELLING" : "Miyi"
		},
		{
			"ID" : "510422",
			"PARENT_ID" : "510400",
			"DISTRICT_NAME" : "盐边县",
			"SPELLING" : "Yanbian"
		},
		{
			"ID" : "510500",
			"PARENT_ID" : "510000",
			"DISTRICT_NAME" : "泸州市",
			"SPELLING" : "Luzhou"
		},
		{
			"ID" : "510502",
			"PARENT_ID" : "510500",
			"DISTRICT_NAME" : "江阳区",
			"SPELLING" : "Jiangyang"
		},
		{
			"ID" : "510503",
			"PARENT_ID" : "510500",
			"DISTRICT_NAME" : "纳溪区",
			"SPELLING" : "Naxi"
		},
		{
			"ID" : "510504",
			"PARENT_ID" : "510500",
			"DISTRICT_NAME" : "龙马潭区",
			"SPELLING" : "Longmatan"
		},
		{
			"ID" : "510521",
			"PARENT_ID" : "510500",
			"DISTRICT_NAME" : "泸县",
			"SPELLING" : "Luxian"
		},
		{
			"ID" : "510522",
			"PARENT_ID" : "510500",
			"DISTRICT_NAME" : "合江县",
			"SPELLING" : "Hejiang"
		},
		{
			"ID" : "510524",
			"PARENT_ID" : "510500",
			"DISTRICT_NAME" : "叙永县",
			"SPELLING" : "Xuyong"
		},
		{
			"ID" : "510525",
			"PARENT_ID" : "510500",
			"DISTRICT_NAME" : "古蔺县",
			"SPELLING" : "Gulin"
		},
		{
			"ID" : "510600",
			"PARENT_ID" : "510000",
			"DISTRICT_NAME" : "德阳市",
			"SPELLING" : "Deyang"
		},
		{
			"ID" : "510603",
			"PARENT_ID" : "510600",
			"DISTRICT_NAME" : "旌阳区",
			"SPELLING" : "Jingyang"
		},
		{
			"ID" : "510623",
			"PARENT_ID" : "510600",
			"DISTRICT_NAME" : "中江县",
			"SPELLING" : "Zhongjiang"
		},
		{
			"ID" : "510626",
			"PARENT_ID" : "510600",
			"DISTRICT_NAME" : "罗江县",
			"SPELLING" : "Luojiang"
		},
		{
			"ID" : "510681",
			"PARENT_ID" : "510600",
			"DISTRICT_NAME" : "广汉市",
			"SPELLING" : "Guanghan"
		},
		{
			"ID" : "510682",
			"PARENT_ID" : "510600",
			"DISTRICT_NAME" : "什邡市",
			"SPELLING" : "Shifang"
		},
		{
			"ID" : "510683",
			"PARENT_ID" : "510600",
			"DISTRICT_NAME" : "绵竹市",
			"SPELLING" : "Mianzhu"
		},
		{
			"ID" : "510700",
			"PARENT_ID" : "510000",
			"DISTRICT_NAME" : "绵阳市",
			"SPELLING" : "Mianyang"
		},
		{
			"ID" : "510703",
			"PARENT_ID" : "510700",
			"DISTRICT_NAME" : "涪城区",
			"SPELLING" : "Fucheng"
		},
		{
			"ID" : "510704",
			"PARENT_ID" : "510700",
			"DISTRICT_NAME" : "游仙区",
			"SPELLING" : "Youxian"
		},
		{
			"ID" : "510722",
			"PARENT_ID" : "510700",
			"DISTRICT_NAME" : "三台县",
			"SPELLING" : "Santai"
		},
		{
			"ID" : "510723",
			"PARENT_ID" : "510700",
			"DISTRICT_NAME" : "盐亭县",
			"SPELLING" : "Yanting"
		},
		{
			"ID" : "510724",
			"PARENT_ID" : "510700",
			"DISTRICT_NAME" : "安县",
			"SPELLING" : "Anxian"
		},
		{
			"ID" : "510725",
			"PARENT_ID" : "510700",
			"DISTRICT_NAME" : "梓潼县",
			"SPELLING" : "Zitong"
		},
		{
			"ID" : "510726",
			"PARENT_ID" : "510700",
			"DISTRICT_NAME" : "北川羌族自治县",
			"SPELLING" : "Beichuan"
		},
		{
			"ID" : "510727",
			"PARENT_ID" : "510700",
			"DISTRICT_NAME" : "平武县",
			"SPELLING" : "Pingwu"
		},
		{
			"ID" : "510781",
			"PARENT_ID" : "510700",
			"DISTRICT_NAME" : "江油市",
			"SPELLING" : "Jiangyou"
		},
		{
			"ID" : "510800",
			"PARENT_ID" : "510000",
			"DISTRICT_NAME" : "广元市",
			"SPELLING" : "Guangyuan"
		},
		{
			"ID" : "510802",
			"PARENT_ID" : "510800",
			"DISTRICT_NAME" : "利州区",
			"SPELLING" : "Lizhou"
		},
		{
			"ID" : "510811",
			"PARENT_ID" : "510800",
			"DISTRICT_NAME" : "昭化区",
			"SPELLING" : "Zhaohua"
		},
		{
			"ID" : "510812",
			"PARENT_ID" : "510800",
			"DISTRICT_NAME" : "朝天区",
			"SPELLING" : "Chaotian"
		},
		{
			"ID" : "510821",
			"PARENT_ID" : "510800",
			"DISTRICT_NAME" : "旺苍县",
			"SPELLING" : "Wangcang"
		},
		{
			"ID" : "510822",
			"PARENT_ID" : "510800",
			"DISTRICT_NAME" : "青川县",
			"SPELLING" : "Qingchuan"
		},
		{
			"ID" : "510823",
			"PARENT_ID" : "510800",
			"DISTRICT_NAME" : "剑阁县",
			"SPELLING" : "Jiange"
		},
		{
			"ID" : "510824",
			"PARENT_ID" : "510800",
			"DISTRICT_NAME" : "苍溪县",
			"SPELLING" : "Cangxi"
		},
		{
			"ID" : "510900",
			"PARENT_ID" : "510000",
			"DISTRICT_NAME" : "遂宁市",
			"SPELLING" : "Suining"
		},
		{
			"ID" : "510903",
			"PARENT_ID" : "510900",
			"DISTRICT_NAME" : "船山区",
			"SPELLING" : "Chuanshan"
		},
		{
			"ID" : "510904",
			"PARENT_ID" : "510900",
			"DISTRICT_NAME" : "安居区",
			"SPELLING" : "Anju"
		},
		{
			"ID" : "510921",
			"PARENT_ID" : "510900",
			"DISTRICT_NAME" : "蓬溪县",
			"SPELLING" : "Pengxi"
		},
		{
			"ID" : "510922",
			"PARENT_ID" : "510900",
			"DISTRICT_NAME" : "射洪县",
			"SPELLING" : "Shehong"
		},
		{
			"ID" : "510923",
			"PARENT_ID" : "510900",
			"DISTRICT_NAME" : "大英县",
			"SPELLING" : "Daying"
		},
		{
			"ID" : "511000",
			"PARENT_ID" : "510000",
			"DISTRICT_NAME" : "内江市",
			"SPELLING" : "Neijiang"
		},
		{
			"ID" : "511002",
			"PARENT_ID" : "511000",
			"DISTRICT_NAME" : "市中区",
			"SPELLING" : "Shizhongqu"
		},
		{
			"ID" : "511011",
			"PARENT_ID" : "511000",
			"DISTRICT_NAME" : "东兴区",
			"SPELLING" : "Dongxing"
		},
		{
			"ID" : "511024",
			"PARENT_ID" : "511000",
			"DISTRICT_NAME" : "威远县",
			"SPELLING" : "Weiyuan"
		},
		{
			"ID" : "511025",
			"PARENT_ID" : "511000",
			"DISTRICT_NAME" : "资中县",
			"SPELLING" : "Zizhong"
		},
		{
			"ID" : "511028",
			"PARENT_ID" : "511000",
			"DISTRICT_NAME" : "隆昌县",
			"SPELLING" : "Longchang"
		},
		{
			"ID" : "511100",
			"PARENT_ID" : "510000",
			"DISTRICT_NAME" : "乐山市",
			"SPELLING" : "Leshan"
		},
		{
			"ID" : "511102",
			"PARENT_ID" : "511100",
			"DISTRICT_NAME" : "市中区",
			"SPELLING" : "Shizhongqu"
		},
		{
			"ID" : "511111",
			"PARENT_ID" : "511100",
			"DISTRICT_NAME" : "沙湾区",
			"SPELLING" : "Shawan"
		},
		{
			"ID" : "511112",
			"PARENT_ID" : "511100",
			"DISTRICT_NAME" : "五通桥区",
			"SPELLING" : "Wutongqiao"
		},
		{
			"ID" : "511113",
			"PARENT_ID" : "511100",
			"DISTRICT_NAME" : "金口河区",
			"SPELLING" : "Jinkouhe"
		},
		{
			"ID" : "511123",
			"PARENT_ID" : "511100",
			"DISTRICT_NAME" : "犍为县",
			"SPELLING" : "Qianwei"
		},
		{
			"ID" : "511124",
			"PARENT_ID" : "511100",
			"DISTRICT_NAME" : "井研县",
			"SPELLING" : "Jingyan"
		},
		{
			"ID" : "511126",
			"PARENT_ID" : "511100",
			"DISTRICT_NAME" : "夹江县",
			"SPELLING" : "Jiajiang"
		},
		{
			"ID" : "511129",
			"PARENT_ID" : "511100",
			"DISTRICT_NAME" : "沐川县",
			"SPELLING" : "Muchuan"
		},
		{
			"ID" : "511132",
			"PARENT_ID" : "511100",
			"DISTRICT_NAME" : "峨边彝族自治县",
			"SPELLING" : "Ebian"
		},
		{
			"ID" : "511133",
			"PARENT_ID" : "511100",
			"DISTRICT_NAME" : "马边彝族自治县",
			"SPELLING" : "Mabian"
		},
		{
			"ID" : "511181",
			"PARENT_ID" : "511100",
			"DISTRICT_NAME" : "峨眉山市",
			"SPELLING" : "Emeishan"
		},
		{
			"ID" : "511300",
			"PARENT_ID" : "510000",
			"DISTRICT_NAME" : "南充市",
			"SPELLING" : "Nanchong"
		},
		{
			"ID" : "511302",
			"PARENT_ID" : "511300",
			"DISTRICT_NAME" : "顺庆区",
			"SPELLING" : "Shunqing"
		},
		{
			"ID" : "511303",
			"PARENT_ID" : "511300",
			"DISTRICT_NAME" : "高坪区",
			"SPELLING" : "Gaoping"
		},
		{
			"ID" : "511304",
			"PARENT_ID" : "511300",
			"DISTRICT_NAME" : "嘉陵区",
			"SPELLING" : "Jialing"
		},
		{
			"ID" : "511321",
			"PARENT_ID" : "511300",
			"DISTRICT_NAME" : "南部县",
			"SPELLING" : "Nanbu"
		},
		{
			"ID" : "511322",
			"PARENT_ID" : "511300",
			"DISTRICT_NAME" : "营山县",
			"SPELLING" : "Yingshan"
		},
		{
			"ID" : "511323",
			"PARENT_ID" : "511300",
			"DISTRICT_NAME" : "蓬安县",
			"SPELLING" : "Pengan"
		},
		{
			"ID" : "511324",
			"PARENT_ID" : "511300",
			"DISTRICT_NAME" : "仪陇县",
			"SPELLING" : "Yilong"
		},
		{
			"ID" : "511325",
			"PARENT_ID" : "511300",
			"DISTRICT_NAME" : "西充县",
			"SPELLING" : "Xichong"
		},
		{
			"ID" : "511381",
			"PARENT_ID" : "511300",
			"DISTRICT_NAME" : "阆中市",
			"SPELLING" : "Langzhong"
		},
		{
			"ID" : "511400",
			"PARENT_ID" : "510000",
			"DISTRICT_NAME" : "眉山市",
			"SPELLING" : "Meishan"
		},
		{
			"ID" : "511402",
			"PARENT_ID" : "511400",
			"DISTRICT_NAME" : "东坡区",
			"SPELLING" : "Dongpo"
		},
		{
			"ID" : "511403",
			"PARENT_ID" : "511400",
			"DISTRICT_NAME" : "彭山区",
			"SPELLING" : "Pengshan"
		},
		{
			"ID" : "511421",
			"PARENT_ID" : "511400",
			"DISTRICT_NAME" : "仁寿县",
			"SPELLING" : "Renshou"
		},
		{
			"ID" : "511423",
			"PARENT_ID" : "511400",
			"DISTRICT_NAME" : "洪雅县",
			"SPELLING" : "Hongya"
		},
		{
			"ID" : "511424",
			"PARENT_ID" : "511400",
			"DISTRICT_NAME" : "丹棱县",
			"SPELLING" : "Danling"
		},
		{
			"ID" : "511425",
			"PARENT_ID" : "511400",
			"DISTRICT_NAME" : "青神县",
			"SPELLING" : "Qingshen"
		},
		{
			"ID" : "511500",
			"PARENT_ID" : "510000",
			"DISTRICT_NAME" : "宜宾市",
			"SPELLING" : "Yibin"
		},
		{
			"ID" : "511502",
			"PARENT_ID" : "511500",
			"DISTRICT_NAME" : "翠屏区",
			"SPELLING" : "Cuiping"
		},
		{
			"ID" : "511503",
			"PARENT_ID" : "511500",
			"DISTRICT_NAME" : "南溪区",
			"SPELLING" : "Nanxi"
		},
		{
			"ID" : "511521",
			"PARENT_ID" : "511500",
			"DISTRICT_NAME" : "宜宾县",
			"SPELLING" : "Yibin"
		},
		{
			"ID" : "511523",
			"PARENT_ID" : "511500",
			"DISTRICT_NAME" : "江安县",
			"SPELLING" : "Jiangan"
		},
		{
			"ID" : "511524",
			"PARENT_ID" : "511500",
			"DISTRICT_NAME" : "长宁县",
			"SPELLING" : "Changning"
		},
		{
			"ID" : "511525",
			"PARENT_ID" : "511500",
			"DISTRICT_NAME" : "高县",
			"SPELLING" : "Gaoxian"
		},
		{
			"ID" : "511526",
			"PARENT_ID" : "511500",
			"DISTRICT_NAME" : "珙县",
			"SPELLING" : "Gongxian"
		},
		{
			"ID" : "511527",
			"PARENT_ID" : "511500",
			"DISTRICT_NAME" : "筠连县",
			"SPELLING" : "Junlian"
		},
		{
			"ID" : "511528",
			"PARENT_ID" : "511500",
			"DISTRICT_NAME" : "兴文县",
			"SPELLING" : "Xingwen"
		},
		{
			"ID" : "511529",
			"PARENT_ID" : "511500",
			"DISTRICT_NAME" : "屏山县",
			"SPELLING" : "Pingshan"
		},
		{
			"ID" : "511600",
			"PARENT_ID" : "510000",
			"DISTRICT_NAME" : "广安市",
			"SPELLING" : "Guangan"
		},
		{
			"ID" : "511602",
			"PARENT_ID" : "511600",
			"DISTRICT_NAME" : "广安区",
			"SPELLING" : "Guangan"
		},
		{
			"ID" : "511603",
			"PARENT_ID" : "511600",
			"DISTRICT_NAME" : "前锋区",
			"SPELLING" : "Qianfeng"
		},
		{
			"ID" : "511621",
			"PARENT_ID" : "511600",
			"DISTRICT_NAME" : "岳池县",
			"SPELLING" : "Yuechi"
		},
		{
			"ID" : "511622",
			"PARENT_ID" : "511600",
			"DISTRICT_NAME" : "武胜县",
			"SPELLING" : "Wusheng"
		},
		{
			"ID" : "511623",
			"PARENT_ID" : "511600",
			"DISTRICT_NAME" : "邻水县",
			"SPELLING" : "Linshui"
		},
		{
			"ID" : "511681",
			"PARENT_ID" : "511600",
			"DISTRICT_NAME" : "华蓥市",
			"SPELLING" : "Huaying"
		},
		{
			"ID" : "511700",
			"PARENT_ID" : "510000",
			"DISTRICT_NAME" : "达州市",
			"SPELLING" : "Dazhou"
		},
		{
			"ID" : "511702",
			"PARENT_ID" : "511700",
			"DISTRICT_NAME" : "通川区",
			"SPELLING" : "Tongchuan"
		},
		{
			"ID" : "511703",
			"PARENT_ID" : "511700",
			"DISTRICT_NAME" : "达川区",
			"SPELLING" : "Dachuan"
		},
		{
			"ID" : "511722",
			"PARENT_ID" : "511700",
			"DISTRICT_NAME" : "宣汉县",
			"SPELLING" : "Xuanhan"
		},
		{
			"ID" : "511723",
			"PARENT_ID" : "511700",
			"DISTRICT_NAME" : "开江县",
			"SPELLING" : "Kaijiang"
		},
		{
			"ID" : "511724",
			"PARENT_ID" : "511700",
			"DISTRICT_NAME" : "大竹县",
			"SPELLING" : "Dazhu"
		},
		{
			"ID" : "511725",
			"PARENT_ID" : "511700",
			"DISTRICT_NAME" : "渠县",
			"SPELLING" : "Quxian"
		},
		{
			"ID" : "511781",
			"PARENT_ID" : "511700",
			"DISTRICT_NAME" : "万源市",
			"SPELLING" : "Wanyuan"
		},
		{
			"ID" : "511800",
			"PARENT_ID" : "510000",
			"DISTRICT_NAME" : "雅安市",
			"SPELLING" : "Yaan"
		},
		{
			"ID" : "511802",
			"PARENT_ID" : "511800",
			"DISTRICT_NAME" : "雨城区",
			"SPELLING" : "Yucheng"
		},
		{
			"ID" : "511803",
			"PARENT_ID" : "511800",
			"DISTRICT_NAME" : "名山区",
			"SPELLING" : "Mingshan"
		},
		{
			"ID" : "511822",
			"PARENT_ID" : "511800",
			"DISTRICT_NAME" : "荥经县",
			"SPELLING" : "Yingjing"
		},
		{
			"ID" : "511823",
			"PARENT_ID" : "511800",
			"DISTRICT_NAME" : "汉源县",
			"SPELLING" : "Hanyuan"
		},
		{
			"ID" : "511824",
			"PARENT_ID" : "511800",
			"DISTRICT_NAME" : "石棉县",
			"SPELLING" : "Shimian"
		},
		{
			"ID" : "511825",
			"PARENT_ID" : "511800",
			"DISTRICT_NAME" : "天全县",
			"SPELLING" : "Tianquan"
		},
		{
			"ID" : "511826",
			"PARENT_ID" : "511800",
			"DISTRICT_NAME" : "芦山县",
			"SPELLING" : "Lushan"
		},
		{
			"ID" : "511827",
			"PARENT_ID" : "511800",
			"DISTRICT_NAME" : "宝兴县",
			"SPELLING" : "Baoxing"
		},
		{
			"ID" : "511900",
			"PARENT_ID" : "510000",
			"DISTRICT_NAME" : "巴中市",
			"SPELLING" : "Bazhong"
		},
		{
			"ID" : "511902",
			"PARENT_ID" : "511900",
			"DISTRICT_NAME" : "巴州区",
			"SPELLING" : "Bazhou"
		},
		{
			"ID" : "511903",
			"PARENT_ID" : "511900",
			"DISTRICT_NAME" : "恩阳区",
			"SPELLING" : "Enyang"
		},
		{
			"ID" : "511921",
			"PARENT_ID" : "511900",
			"DISTRICT_NAME" : "通江县",
			"SPELLING" : "Tongjiang"
		},
		{
			"ID" : "511922",
			"PARENT_ID" : "511900",
			"DISTRICT_NAME" : "南江县",
			"SPELLING" : "Nanjiang"
		},
		{
			"ID" : "511923",
			"PARENT_ID" : "511900",
			"DISTRICT_NAME" : "平昌县",
			"SPELLING" : "Pingchang"
		},
		{
			"ID" : "512000",
			"PARENT_ID" : "510000",
			"DISTRICT_NAME" : "资阳市",
			"SPELLING" : "Ziyang"
		},
		{
			"ID" : "512002",
			"PARENT_ID" : "512000",
			"DISTRICT_NAME" : "雁江区",
			"SPELLING" : "Yanjiang"
		},
		{
			"ID" : "512021",
			"PARENT_ID" : "512000",
			"DISTRICT_NAME" : "安岳县",
			"SPELLING" : "Anyue"
		},
		{
			"ID" : "512022",
			"PARENT_ID" : "512000",
			"DISTRICT_NAME" : "乐至县",
			"SPELLING" : "Lezhi"
		},
		{
			"ID" : "512081",
			"PARENT_ID" : "512000",
			"DISTRICT_NAME" : "简阳市",
			"SPELLING" : "Jianyang"
		},
		{
			"ID" : "513200",
			"PARENT_ID" : "510000",
			"DISTRICT_NAME" : "阿坝藏族羌族自治州",
			"SPELLING" : "Aba"
		},
		{
			"ID" : "513221",
			"PARENT_ID" : "513200",
			"DISTRICT_NAME" : "汶川县",
			"SPELLING" : "Wenchuan"
		},
		{
			"ID" : "513222",
			"PARENT_ID" : "513200",
			"DISTRICT_NAME" : "理县",
			"SPELLING" : "Lixian"
		},
		{
			"ID" : "513223",
			"PARENT_ID" : "513200",
			"DISTRICT_NAME" : "茂县",
			"SPELLING" : "Maoxian"
		},
		{
			"ID" : "513224",
			"PARENT_ID" : "513200",
			"DISTRICT_NAME" : "松潘县",
			"SPELLING" : "Songpan"
		},
		{
			"ID" : "513225",
			"PARENT_ID" : "513200",
			"DISTRICT_NAME" : "九寨沟县",
			"SPELLING" : "Jiuzhaigou"
		},
		{
			"ID" : "513226",
			"PARENT_ID" : "513200",
			"DISTRICT_NAME" : "金川县",
			"SPELLING" : "Jinchuan"
		},
		{
			"ID" : "513227",
			"PARENT_ID" : "513200",
			"DISTRICT_NAME" : "小金县",
			"SPELLING" : "Xiaojin"
		},
		{
			"ID" : "513228",
			"PARENT_ID" : "513200",
			"DISTRICT_NAME" : "黑水县",
			"SPELLING" : "Heishui"
		},
		{
			"ID" : "513229",
			"PARENT_ID" : "513200",
			"DISTRICT_NAME" : "马尔康县",
			"SPELLING" : "Maerkang"
		},
		{
			"ID" : "513230",
			"PARENT_ID" : "513200",
			"DISTRICT_NAME" : "壤塘县",
			"SPELLING" : "Rangtang"
		},
		{
			"ID" : "513231",
			"PARENT_ID" : "513200",
			"DISTRICT_NAME" : "阿坝县",
			"SPELLING" : "Aba"
		},
		{
			"ID" : "513232",
			"PARENT_ID" : "513200",
			"DISTRICT_NAME" : "若尔盖县",
			"SPELLING" : "Ruoergai"
		},
		{
			"ID" : "513233",
			"PARENT_ID" : "513200",
			"DISTRICT_NAME" : "红原县",
			"SPELLING" : "Hongyuan"
		},
		{
			"ID" : "513300",
			"PARENT_ID" : "510000",
			"DISTRICT_NAME" : "甘孜藏族自治州",
			"SPELLING" : "Garze"
		},
		{
			"ID" : "513321",
			"PARENT_ID" : "513300",
			"DISTRICT_NAME" : "康定县",
			"SPELLING" : "Kangding"
		},
		{
			"ID" : "513322",
			"PARENT_ID" : "513300",
			"DISTRICT_NAME" : "泸定县",
			"SPELLING" : "Luding"
		},
		{
			"ID" : "513323",
			"PARENT_ID" : "513300",
			"DISTRICT_NAME" : "丹巴县",
			"SPELLING" : "Danba"
		},
		{
			"ID" : "513324",
			"PARENT_ID" : "513300",
			"DISTRICT_NAME" : "九龙县",
			"SPELLING" : "Jiulong"
		},
		{
			"ID" : "513325",
			"PARENT_ID" : "513300",
			"DISTRICT_NAME" : "雅江县",
			"SPELLING" : "Yajiang"
		},
		{
			"ID" : "513326",
			"PARENT_ID" : "513300",
			"DISTRICT_NAME" : "道孚县",
			"SPELLING" : "Daofu"
		},
		{
			"ID" : "513327",
			"PARENT_ID" : "513300",
			"DISTRICT_NAME" : "炉霍县",
			"SPELLING" : "Luhuo"
		},
		{
			"ID" : "513328",
			"PARENT_ID" : "513300",
			"DISTRICT_NAME" : "甘孜县",
			"SPELLING" : "Ganzi"
		},
		{
			"ID" : "513329",
			"PARENT_ID" : "513300",
			"DISTRICT_NAME" : "新龙县",
			"SPELLING" : "Xinlong"
		},
		{
			"ID" : "513330",
			"PARENT_ID" : "513300",
			"DISTRICT_NAME" : "德格县",
			"SPELLING" : "Dege"
		},
		{
			"ID" : "513331",
			"PARENT_ID" : "513300",
			"DISTRICT_NAME" : "白玉县",
			"SPELLING" : "Baiyu"
		},
		{
			"ID" : "513332",
			"PARENT_ID" : "513300",
			"DISTRICT_NAME" : "石渠县",
			"SPELLING" : "Shiqu"
		},
		{
			"ID" : "513333",
			"PARENT_ID" : "513300",
			"DISTRICT_NAME" : "色达县",
			"SPELLING" : "Seda"
		},
		{
			"ID" : "513334",
			"PARENT_ID" : "513300",
			"DISTRICT_NAME" : "理塘县",
			"SPELLING" : "Litang"
		},
		{
			"ID" : "513335",
			"PARENT_ID" : "513300",
			"DISTRICT_NAME" : "巴塘县",
			"SPELLING" : "Batang"
		},
		{
			"ID" : "513336",
			"PARENT_ID" : "513300",
			"DISTRICT_NAME" : "乡城县",
			"SPELLING" : "Xiangcheng"
		},
		{
			"ID" : "513337",
			"PARENT_ID" : "513300",
			"DISTRICT_NAME" : "稻城县",
			"SPELLING" : "Daocheng"
		},
		{
			"ID" : "513338",
			"PARENT_ID" : "513300",
			"DISTRICT_NAME" : "得荣县",
			"SPELLING" : "Derong"
		},
		{
			"ID" : "513400",
			"PARENT_ID" : "510000",
			"DISTRICT_NAME" : "凉山彝族自治州",
			"SPELLING" : "Liangshan"
		},
		{
			"ID" : "513401",
			"PARENT_ID" : "513400",
			"DISTRICT_NAME" : "西昌市",
			"SPELLING" : "Xichang"
		},
		{
			"ID" : "513422",
			"PARENT_ID" : "513400",
			"DISTRICT_NAME" : "木里藏族自治县",
			"SPELLING" : "Muli"
		},
		{
			"ID" : "513423",
			"PARENT_ID" : "513400",
			"DISTRICT_NAME" : "盐源县",
			"SPELLING" : "Yanyuan"
		},
		{
			"ID" : "513424",
			"PARENT_ID" : "513400",
			"DISTRICT_NAME" : "德昌县",
			"SPELLING" : "Dechang"
		},
		{
			"ID" : "513425",
			"PARENT_ID" : "513400",
			"DISTRICT_NAME" : "会理县",
			"SPELLING" : "Huili"
		},
		{
			"ID" : "513426",
			"PARENT_ID" : "513400",
			"DISTRICT_NAME" : "会东县",
			"SPELLING" : "Huidong"
		},
		{
			"ID" : "513427",
			"PARENT_ID" : "513400",
			"DISTRICT_NAME" : "宁南县",
			"SPELLING" : "Ningnan"
		},
		{
			"ID" : "513428",
			"PARENT_ID" : "513400",
			"DISTRICT_NAME" : "普格县",
			"SPELLING" : "Puge"
		},
		{
			"ID" : "513429",
			"PARENT_ID" : "513400",
			"DISTRICT_NAME" : "布拖县",
			"SPELLING" : "Butuo"
		},
		{
			"ID" : "513430",
			"PARENT_ID" : "513400",
			"DISTRICT_NAME" : "金阳县",
			"SPELLING" : "Jinyang"
		},
		{
			"ID" : "513431",
			"PARENT_ID" : "513400",
			"DISTRICT_NAME" : "昭觉县",
			"SPELLING" : "Zhaojue"
		},
		{
			"ID" : "513432",
			"PARENT_ID" : "513400",
			"DISTRICT_NAME" : "喜德县",
			"SPELLING" : "Xide"
		},
		{
			"ID" : "513433",
			"PARENT_ID" : "513400",
			"DISTRICT_NAME" : "冕宁县",
			"SPELLING" : "Mianning"
		},
		{
			"ID" : "513434",
			"PARENT_ID" : "513400",
			"DISTRICT_NAME" : "越西县",
			"SPELLING" : "Yuexi"
		},
		{
			"ID" : "513435",
			"PARENT_ID" : "513400",
			"DISTRICT_NAME" : "甘洛县",
			"SPELLING" : "Ganluo"
		},
		{
			"ID" : "513436",
			"PARENT_ID" : "513400",
			"DISTRICT_NAME" : "美姑县",
			"SPELLING" : "Meigu"
		},
		{
			"ID" : "513437",
			"PARENT_ID" : "513400",
			"DISTRICT_NAME" : "雷波县",
			"SPELLING" : "Leibo"
		},
		{
			"ID" : "520000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "贵州省",
			"SPELLING" : "Guizhou"
		},
		{
			"ID" : "520100",
			"PARENT_ID" : "520000",
			"DISTRICT_NAME" : "贵阳市",
			"SPELLING" : "Guiyang"
		},
		{
			"ID" : "520102",
			"PARENT_ID" : "520100",
			"DISTRICT_NAME" : "南明区",
			"SPELLING" : "Nanming"
		},
		{
			"ID" : "520103",
			"PARENT_ID" : "520100",
			"DISTRICT_NAME" : "云岩区",
			"SPELLING" : "Yunyan"
		},
		{
			"ID" : "520111",
			"PARENT_ID" : "520100",
			"DISTRICT_NAME" : "花溪区",
			"SPELLING" : "Huaxi"
		},
		{
			"ID" : "520112",
			"PARENT_ID" : "520100",
			"DISTRICT_NAME" : "乌当区",
			"SPELLING" : "Wudang"
		},
		{
			"ID" : "520113",
			"PARENT_ID" : "520100",
			"DISTRICT_NAME" : "白云区",
			"SPELLING" : "Baiyun"
		},
		{
			"ID" : "520115",
			"PARENT_ID" : "520100",
			"DISTRICT_NAME" : "观山湖区",
			"SPELLING" : "Guanshanhu"
		},
		{
			"ID" : "520121",
			"PARENT_ID" : "520100",
			"DISTRICT_NAME" : "开阳县",
			"SPELLING" : "Kaiyang"
		},
		{
			"ID" : "520122",
			"PARENT_ID" : "520100",
			"DISTRICT_NAME" : "息烽县",
			"SPELLING" : "Xifeng"
		},
		{
			"ID" : "520123",
			"PARENT_ID" : "520100",
			"DISTRICT_NAME" : "修文县",
			"SPELLING" : "Xiuwen"
		},
		{
			"ID" : "520181",
			"PARENT_ID" : "520100",
			"DISTRICT_NAME" : "清镇市",
			"SPELLING" : "Qingzhen"
		},
		{
			"ID" : "520200",
			"PARENT_ID" : "520000",
			"DISTRICT_NAME" : "六盘水市",
			"SPELLING" : "Liupanshui"
		},
		{
			"ID" : "520201",
			"PARENT_ID" : "520200",
			"DISTRICT_NAME" : "钟山区",
			"SPELLING" : "Zhongshan"
		},
		{
			"ID" : "520203",
			"PARENT_ID" : "520200",
			"DISTRICT_NAME" : "六枝特区",
			"SPELLING" : "Liuzhi"
		},
		{
			"ID" : "520221",
			"PARENT_ID" : "520200",
			"DISTRICT_NAME" : "水城县",
			"SPELLING" : "Shuicheng"
		},
		{
			"ID" : "520222",
			"PARENT_ID" : "520200",
			"DISTRICT_NAME" : "盘县",
			"SPELLING" : "Panxian"
		},
		{
			"ID" : "520300",
			"PARENT_ID" : "520000",
			"DISTRICT_NAME" : "遵义市",
			"SPELLING" : "Zunyi"
		},
		{
			"ID" : "520302",
			"PARENT_ID" : "520300",
			"DISTRICT_NAME" : "红花岗区",
			"SPELLING" : "Honghuagang"
		},
		{
			"ID" : "520303",
			"PARENT_ID" : "520300",
			"DISTRICT_NAME" : "汇川区",
			"SPELLING" : "Huichuan"
		},
		{
			"ID" : "520321",
			"PARENT_ID" : "520300",
			"DISTRICT_NAME" : "遵义县",
			"SPELLING" : "Zunyi"
		},
		{
			"ID" : "520322",
			"PARENT_ID" : "520300",
			"DISTRICT_NAME" : "桐梓县",
			"SPELLING" : "Tongzi"
		},
		{
			"ID" : "520323",
			"PARENT_ID" : "520300",
			"DISTRICT_NAME" : "绥阳县",
			"SPELLING" : "Suiyang"
		},
		{
			"ID" : "520324",
			"PARENT_ID" : "520300",
			"DISTRICT_NAME" : "正安县",
			"SPELLING" : "Zhengan"
		},
		{
			"ID" : "520325",
			"PARENT_ID" : "520300",
			"DISTRICT_NAME" : "道真仡佬族苗族自治县",
			"SPELLING" : "Daozhen"
		},
		{
			"ID" : "520326",
			"PARENT_ID" : "520300",
			"DISTRICT_NAME" : "务川仡佬族苗族自治县",
			"SPELLING" : "Wuchuan"
		},
		{
			"ID" : "520327",
			"PARENT_ID" : "520300",
			"DISTRICT_NAME" : "凤冈县",
			"SPELLING" : "Fenggang"
		},
		{
			"ID" : "520328",
			"PARENT_ID" : "520300",
			"DISTRICT_NAME" : "湄潭县",
			"SPELLING" : "Meitan"
		},
		{
			"ID" : "520329",
			"PARENT_ID" : "520300",
			"DISTRICT_NAME" : "余庆县",
			"SPELLING" : "Yuqing"
		},
		{
			"ID" : "520330",
			"PARENT_ID" : "520300",
			"DISTRICT_NAME" : "习水县",
			"SPELLING" : "Xishui"
		},
		{
			"ID" : "520381",
			"PARENT_ID" : "520300",
			"DISTRICT_NAME" : "赤水市",
			"SPELLING" : "Chishui"
		},
		{
			"ID" : "520382",
			"PARENT_ID" : "520300",
			"DISTRICT_NAME" : "仁怀市",
			"SPELLING" : "Renhuai"
		},
		{
			"ID" : "520400",
			"PARENT_ID" : "520000",
			"DISTRICT_NAME" : "安顺市",
			"SPELLING" : "Anshun"
		},
		{
			"ID" : "520402",
			"PARENT_ID" : "520400",
			"DISTRICT_NAME" : "西秀区",
			"SPELLING" : "Xixiu"
		},
		{
			"ID" : "520421",
			"PARENT_ID" : "520400",
			"DISTRICT_NAME" : "平坝区",
			"SPELLING" : "Pingba"
		},
		{
			"ID" : "520422",
			"PARENT_ID" : "520400",
			"DISTRICT_NAME" : "普定县",
			"SPELLING" : "Puding"
		},
		{
			"ID" : "520423",
			"PARENT_ID" : "520400",
			"DISTRICT_NAME" : "镇宁布依族苗族自治县",
			"SPELLING" : "Zhenning"
		},
		{
			"ID" : "520424",
			"PARENT_ID" : "520400",
			"DISTRICT_NAME" : "关岭布依族苗族自治县",
			"SPELLING" : "Guanling"
		},
		{
			"ID" : "520425",
			"PARENT_ID" : "520400",
			"DISTRICT_NAME" : "紫云苗族布依族自治县",
			"SPELLING" : "Ziyun"
		},
		{
			"ID" : "520500",
			"PARENT_ID" : "520000",
			"DISTRICT_NAME" : "毕节市",
			"SPELLING" : "Bijie"
		},
		{
			"ID" : "520502",
			"PARENT_ID" : "520500",
			"DISTRICT_NAME" : "七星关区",
			"SPELLING" : "Qixingguan"
		},
		{
			"ID" : "520521",
			"PARENT_ID" : "520500",
			"DISTRICT_NAME" : "大方县",
			"SPELLING" : "Dafang"
		},
		{
			"ID" : "520522",
			"PARENT_ID" : "520500",
			"DISTRICT_NAME" : "黔西县",
			"SPELLING" : "Qianxi"
		},
		{
			"ID" : "520523",
			"PARENT_ID" : "520500",
			"DISTRICT_NAME" : "金沙县",
			"SPELLING" : "Jinsha"
		},
		{
			"ID" : "520524",
			"PARENT_ID" : "520500",
			"DISTRICT_NAME" : "织金县",
			"SPELLING" : "Zhijin"
		},
		{
			"ID" : "520525",
			"PARENT_ID" : "520500",
			"DISTRICT_NAME" : "纳雍县",
			"SPELLING" : "Nayong"
		},
		{
			"ID" : "520526",
			"PARENT_ID" : "520500",
			"DISTRICT_NAME" : "威宁彝族回族苗族自治县",
			"SPELLING" : "Weining"
		},
		{
			"ID" : "520527",
			"PARENT_ID" : "520500",
			"DISTRICT_NAME" : "赫章县",
			"SPELLING" : "Hezhang"
		},
		{
			"ID" : "520600",
			"PARENT_ID" : "520000",
			"DISTRICT_NAME" : "铜仁市",
			"SPELLING" : "Tongren"
		},
		{
			"ID" : "520602",
			"PARENT_ID" : "520600",
			"DISTRICT_NAME" : "碧江区",
			"SPELLING" : "Bijiang"
		},
		{
			"ID" : "520603",
			"PARENT_ID" : "520600",
			"DISTRICT_NAME" : "万山区",
			"SPELLING" : "Wanshan"
		},
		{
			"ID" : "520621",
			"PARENT_ID" : "520600",
			"DISTRICT_NAME" : "江口县",
			"SPELLING" : "Jiangkou"
		},
		{
			"ID" : "520622",
			"PARENT_ID" : "520600",
			"DISTRICT_NAME" : "玉屏侗族自治县",
			"SPELLING" : "Yuping"
		},
		{
			"ID" : "520623",
			"PARENT_ID" : "520600",
			"DISTRICT_NAME" : "石阡县",
			"SPELLING" : "Shiqian"
		},
		{
			"ID" : "520624",
			"PARENT_ID" : "520600",
			"DISTRICT_NAME" : "思南县",
			"SPELLING" : "Sinan"
		},
		{
			"ID" : "520625",
			"PARENT_ID" : "520600",
			"DISTRICT_NAME" : "印江土家族苗族自治县",
			"SPELLING" : "Yinjiang"
		},
		{
			"ID" : "520626",
			"PARENT_ID" : "520600",
			"DISTRICT_NAME" : "德江县",
			"SPELLING" : "Dejiang"
		},
		{
			"ID" : "520627",
			"PARENT_ID" : "520600",
			"DISTRICT_NAME" : "沿河土家族自治县",
			"SPELLING" : "Yuanhe"
		},
		{
			"ID" : "520628",
			"PARENT_ID" : "520600",
			"DISTRICT_NAME" : "松桃苗族自治县",
			"SPELLING" : "Songtao"
		},
		{
			"ID" : "522300",
			"PARENT_ID" : "520000",
			"DISTRICT_NAME" : "黔西南布依族苗族自治州",
			"SPELLING" : "Qianxinan"
		},
		{
			"ID" : "522301",
			"PARENT_ID" : "522300",
			"DISTRICT_NAME" : "兴义市 ",
			"SPELLING" : "Xingyi"
		},
		{
			"ID" : "522322",
			"PARENT_ID" : "522300",
			"DISTRICT_NAME" : "兴仁县",
			"SPELLING" : "Xingren"
		},
		{
			"ID" : "522323",
			"PARENT_ID" : "522300",
			"DISTRICT_NAME" : "普安县",
			"SPELLING" : "Puan"
		},
		{
			"ID" : "522324",
			"PARENT_ID" : "522300",
			"DISTRICT_NAME" : "晴隆县",
			"SPELLING" : "Qinglong"
		},
		{
			"ID" : "522325",
			"PARENT_ID" : "522300",
			"DISTRICT_NAME" : "贞丰县",
			"SPELLING" : "Zhenfeng"
		},
		{
			"ID" : "522326",
			"PARENT_ID" : "522300",
			"DISTRICT_NAME" : "望谟县",
			"SPELLING" : "Wangmo"
		},
		{
			"ID" : "522327",
			"PARENT_ID" : "522300",
			"DISTRICT_NAME" : "册亨县",
			"SPELLING" : "Ceheng"
		},
		{
			"ID" : "522328",
			"PARENT_ID" : "522300",
			"DISTRICT_NAME" : "安龙县",
			"SPELLING" : "Anlong"
		},
		{
			"ID" : "522600",
			"PARENT_ID" : "520000",
			"DISTRICT_NAME" : "黔东南苗族侗族自治州",
			"SPELLING" : "Qiandongnan"
		},
		{
			"ID" : "522601",
			"PARENT_ID" : "522600",
			"DISTRICT_NAME" : "凯里市",
			"SPELLING" : "Kaili"
		},
		{
			"ID" : "522622",
			"PARENT_ID" : "522600",
			"DISTRICT_NAME" : "黄平县",
			"SPELLING" : "Huangping"
		},
		{
			"ID" : "522623",
			"PARENT_ID" : "522600",
			"DISTRICT_NAME" : "施秉县",
			"SPELLING" : "Shibing"
		},
		{
			"ID" : "522624",
			"PARENT_ID" : "522600",
			"DISTRICT_NAME" : "三穗县",
			"SPELLING" : "Sansui"
		},
		{
			"ID" : "522625",
			"PARENT_ID" : "522600",
			"DISTRICT_NAME" : "镇远县",
			"SPELLING" : "Zhenyuan"
		},
		{
			"ID" : "522626",
			"PARENT_ID" : "522600",
			"DISTRICT_NAME" : "岑巩县",
			"SPELLING" : "Cengong"
		},
		{
			"ID" : "522627",
			"PARENT_ID" : "522600",
			"DISTRICT_NAME" : "天柱县",
			"SPELLING" : "Tianzhu"
		},
		{
			"ID" : "522628",
			"PARENT_ID" : "522600",
			"DISTRICT_NAME" : "锦屏县",
			"SPELLING" : "Jinping"
		},
		{
			"ID" : "522629",
			"PARENT_ID" : "522600",
			"DISTRICT_NAME" : "剑河县",
			"SPELLING" : "Jianhe"
		},
		{
			"ID" : "522630",
			"PARENT_ID" : "522600",
			"DISTRICT_NAME" : "台江县",
			"SPELLING" : "Taijiang"
		},
		{
			"ID" : "522631",
			"PARENT_ID" : "522600",
			"DISTRICT_NAME" : "黎平县",
			"SPELLING" : "Liping"
		},
		{
			"ID" : "522632",
			"PARENT_ID" : "522600",
			"DISTRICT_NAME" : "榕江县",
			"SPELLING" : "Rongjiang"
		},
		{
			"ID" : "522633",
			"PARENT_ID" : "522600",
			"DISTRICT_NAME" : "从江县",
			"SPELLING" : "Congjiang"
		},
		{
			"ID" : "522634",
			"PARENT_ID" : "522600",
			"DISTRICT_NAME" : "雷山县",
			"SPELLING" : "Leishan"
		},
		{
			"ID" : "522635",
			"PARENT_ID" : "522600",
			"DISTRICT_NAME" : "麻江县",
			"SPELLING" : "Majiang"
		},
		{
			"ID" : "522636",
			"PARENT_ID" : "522600",
			"DISTRICT_NAME" : "丹寨县",
			"SPELLING" : "Danzhai"
		},
		{
			"ID" : "522700",
			"PARENT_ID" : "520000",
			"DISTRICT_NAME" : "黔南布依族苗族自治州",
			"SPELLING" : "Qiannan"
		},
		{
			"ID" : "522701",
			"PARENT_ID" : "522700",
			"DISTRICT_NAME" : "都匀市",
			"SPELLING" : "Duyun"
		},
		{
			"ID" : "522702",
			"PARENT_ID" : "522700",
			"DISTRICT_NAME" : "福泉市",
			"SPELLING" : "Fuquan"
		},
		{
			"ID" : "522722",
			"PARENT_ID" : "522700",
			"DISTRICT_NAME" : "荔波县",
			"SPELLING" : "Libo"
		},
		{
			"ID" : "522723",
			"PARENT_ID" : "522700",
			"DISTRICT_NAME" : "贵定县",
			"SPELLING" : "Guiding"
		},
		{
			"ID" : "522725",
			"PARENT_ID" : "522700",
			"DISTRICT_NAME" : "瓮安县",
			"SPELLING" : "Wengan"
		},
		{
			"ID" : "522726",
			"PARENT_ID" : "522700",
			"DISTRICT_NAME" : "独山县",
			"SPELLING" : "Dushan"
		},
		{
			"ID" : "522727",
			"PARENT_ID" : "522700",
			"DISTRICT_NAME" : "平塘县",
			"SPELLING" : "Pingtang"
		},
		{
			"ID" : "522728",
			"PARENT_ID" : "522700",
			"DISTRICT_NAME" : "罗甸县",
			"SPELLING" : "Luodian"
		},
		{
			"ID" : "522729",
			"PARENT_ID" : "522700",
			"DISTRICT_NAME" : "长顺县",
			"SPELLING" : "Changshun"
		},
		{
			"ID" : "522730",
			"PARENT_ID" : "522700",
			"DISTRICT_NAME" : "龙里县",
			"SPELLING" : "Longli"
		},
		{
			"ID" : "522731",
			"PARENT_ID" : "522700",
			"DISTRICT_NAME" : "惠水县",
			"SPELLING" : "Huishui"
		},
		{
			"ID" : "522732",
			"PARENT_ID" : "522700",
			"DISTRICT_NAME" : "三都水族自治县",
			"SPELLING" : "Sandu"
		},
		{
			"ID" : "530000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "云南省",
			"SPELLING" : "Yunnan"
		},
		{
			"ID" : "530100",
			"PARENT_ID" : "530000",
			"DISTRICT_NAME" : "昆明市",
			"SPELLING" : "Kunming"
		},
		{
			"ID" : "530102",
			"PARENT_ID" : "530100",
			"DISTRICT_NAME" : "五华区",
			"SPELLING" : "Wuhua"
		},
		{
			"ID" : "530103",
			"PARENT_ID" : "530100",
			"DISTRICT_NAME" : "盘龙区",
			"SPELLING" : "Panlong"
		},
		{
			"ID" : "530111",
			"PARENT_ID" : "530100",
			"DISTRICT_NAME" : "官渡区",
			"SPELLING" : "Guandu"
		},
		{
			"ID" : "530112",
			"PARENT_ID" : "530100",
			"DISTRICT_NAME" : "西山区",
			"SPELLING" : "Xishan"
		},
		{
			"ID" : "530113",
			"PARENT_ID" : "530100",
			"DISTRICT_NAME" : "东川区",
			"SPELLING" : "Dongchuan"
		},
		{
			"ID" : "530114",
			"PARENT_ID" : "530100",
			"DISTRICT_NAME" : "呈贡区",
			"SPELLING" : "Chenggong"
		},
		{
			"ID" : "530122",
			"PARENT_ID" : "530100",
			"DISTRICT_NAME" : "晋宁县",
			"SPELLING" : "Jinning"
		},
		{
			"ID" : "530124",
			"PARENT_ID" : "530100",
			"DISTRICT_NAME" : "富民县",
			"SPELLING" : "Fumin"
		},
		{
			"ID" : "530125",
			"PARENT_ID" : "530100",
			"DISTRICT_NAME" : "宜良县",
			"SPELLING" : "Yiliang"
		},
		{
			"ID" : "530126",
			"PARENT_ID" : "530100",
			"DISTRICT_NAME" : "石林彝族自治县",
			"SPELLING" : "Shilin"
		},
		{
			"ID" : "530127",
			"PARENT_ID" : "530100",
			"DISTRICT_NAME" : "嵩明县",
			"SPELLING" : "Songming"
		},
		{
			"ID" : "530128",
			"PARENT_ID" : "530100",
			"DISTRICT_NAME" : "禄劝彝族苗族自治县",
			"SPELLING" : "Luquan"
		},
		{
			"ID" : "530129",
			"PARENT_ID" : "530100",
			"DISTRICT_NAME" : "寻甸回族彝族自治县 ",
			"SPELLING" : "Xundian"
		},
		{
			"ID" : "530181",
			"PARENT_ID" : "530100",
			"DISTRICT_NAME" : "安宁市",
			"SPELLING" : "Anning"
		},
		{
			"ID" : "530300",
			"PARENT_ID" : "530000",
			"DISTRICT_NAME" : "曲靖市",
			"SPELLING" : "Qujing"
		},
		{
			"ID" : "530302",
			"PARENT_ID" : "530300",
			"DISTRICT_NAME" : "麒麟区",
			"SPELLING" : "Qilin"
		},
		{
			"ID" : "530321",
			"PARENT_ID" : "530300",
			"DISTRICT_NAME" : "马龙县",
			"SPELLING" : "Malong"
		},
		{
			"ID" : "530322",
			"PARENT_ID" : "530300",
			"DISTRICT_NAME" : "陆良县",
			"SPELLING" : "Luliang"
		},
		{
			"ID" : "530323",
			"PARENT_ID" : "530300",
			"DISTRICT_NAME" : "师宗县",
			"SPELLING" : "Shizong"
		},
		{
			"ID" : "530324",
			"PARENT_ID" : "530300",
			"DISTRICT_NAME" : "罗平县",
			"SPELLING" : "Luoping"
		},
		{
			"ID" : "530325",
			"PARENT_ID" : "530300",
			"DISTRICT_NAME" : "富源县",
			"SPELLING" : "Fuyuan"
		},
		{
			"ID" : "530326",
			"PARENT_ID" : "530300",
			"DISTRICT_NAME" : "会泽县",
			"SPELLING" : "Huize"
		},
		{
			"ID" : "530328",
			"PARENT_ID" : "530300",
			"DISTRICT_NAME" : "沾益县",
			"SPELLING" : "Zhanyi"
		},
		{
			"ID" : "530381",
			"PARENT_ID" : "530300",
			"DISTRICT_NAME" : "宣威市",
			"SPELLING" : "Xuanwei"
		},
		{
			"ID" : "530400",
			"PARENT_ID" : "530000",
			"DISTRICT_NAME" : "玉溪市",
			"SPELLING" : "Yuxi"
		},
		{
			"ID" : "530402",
			"PARENT_ID" : "530400",
			"DISTRICT_NAME" : "红塔区",
			"SPELLING" : "Hongta"
		},
		{
			"ID" : "530421",
			"PARENT_ID" : "530400",
			"DISTRICT_NAME" : "江川县",
			"SPELLING" : "Jiangchuan"
		},
		{
			"ID" : "530422",
			"PARENT_ID" : "530400",
			"DISTRICT_NAME" : "澄江县",
			"SPELLING" : "Chengjiang"
		},
		{
			"ID" : "530423",
			"PARENT_ID" : "530400",
			"DISTRICT_NAME" : "通海县",
			"SPELLING" : "Tonghai"
		},
		{
			"ID" : "530424",
			"PARENT_ID" : "530400",
			"DISTRICT_NAME" : "华宁县",
			"SPELLING" : "Huaning"
		},
		{
			"ID" : "530425",
			"PARENT_ID" : "530400",
			"DISTRICT_NAME" : "易门县",
			"SPELLING" : "Yimen"
		},
		{
			"ID" : "530426",
			"PARENT_ID" : "530400",
			"DISTRICT_NAME" : "峨山彝族自治县",
			"SPELLING" : "Eshan"
		},
		{
			"ID" : "530427",
			"PARENT_ID" : "530400",
			"DISTRICT_NAME" : "新平彝族傣族自治县",
			"SPELLING" : "Xinping"
		},
		{
			"ID" : "530428",
			"PARENT_ID" : "530400",
			"DISTRICT_NAME" : "元江哈尼族彝族傣族自治县",
			"SPELLING" : "Yuanjiang"
		},
		{
			"ID" : "530500",
			"PARENT_ID" : "530000",
			"DISTRICT_NAME" : "保山市",
			"SPELLING" : "Baoshan"
		},
		{
			"ID" : "530502",
			"PARENT_ID" : "530500",
			"DISTRICT_NAME" : "隆阳区",
			"SPELLING" : "Longyang"
		},
		{
			"ID" : "530521",
			"PARENT_ID" : "530500",
			"DISTRICT_NAME" : "施甸县",
			"SPELLING" : "Shidian"
		},
		{
			"ID" : "530522",
			"PARENT_ID" : "530500",
			"DISTRICT_NAME" : "腾冲县",
			"SPELLING" : "Tengchong"
		},
		{
			"ID" : "530523",
			"PARENT_ID" : "530500",
			"DISTRICT_NAME" : "龙陵县",
			"SPELLING" : "Longling"
		},
		{
			"ID" : "530524",
			"PARENT_ID" : "530500",
			"DISTRICT_NAME" : "昌宁县",
			"SPELLING" : "Changning"
		},
		{
			"ID" : "530600",
			"PARENT_ID" : "530000",
			"DISTRICT_NAME" : "昭通市",
			"SPELLING" : "Zhaotong"
		},
		{
			"ID" : "530602",
			"PARENT_ID" : "530600",
			"DISTRICT_NAME" : "昭阳区",
			"SPELLING" : "Zhaoyang"
		},
		{
			"ID" : "530621",
			"PARENT_ID" : "530600",
			"DISTRICT_NAME" : "鲁甸县",
			"SPELLING" : "Ludian"
		},
		{
			"ID" : "530622",
			"PARENT_ID" : "530600",
			"DISTRICT_NAME" : "巧家县",
			"SPELLING" : "Qiaojia"
		},
		{
			"ID" : "530623",
			"PARENT_ID" : "530600",
			"DISTRICT_NAME" : "盐津县",
			"SPELLING" : "Yanjin"
		},
		{
			"ID" : "530624",
			"PARENT_ID" : "530600",
			"DISTRICT_NAME" : "大关县",
			"SPELLING" : "Daguan"
		},
		{
			"ID" : "530625",
			"PARENT_ID" : "530600",
			"DISTRICT_NAME" : "永善县",
			"SPELLING" : "Yongshan"
		},
		{
			"ID" : "530626",
			"PARENT_ID" : "530600",
			"DISTRICT_NAME" : "绥江县",
			"SPELLING" : "Suijiang"
		},
		{
			"ID" : "530627",
			"PARENT_ID" : "530600",
			"DISTRICT_NAME" : "镇雄县",
			"SPELLING" : "Zhenxiong"
		},
		{
			"ID" : "530628",
			"PARENT_ID" : "530600",
			"DISTRICT_NAME" : "彝良县",
			"SPELLING" : "Yiliang"
		},
		{
			"ID" : "530629",
			"PARENT_ID" : "530600",
			"DISTRICT_NAME" : "威信县",
			"SPELLING" : "Weixin"
		},
		{
			"ID" : "530630",
			"PARENT_ID" : "530600",
			"DISTRICT_NAME" : "水富县",
			"SPELLING" : "Shuifu"
		},
		{
			"ID" : "530700",
			"PARENT_ID" : "530000",
			"DISTRICT_NAME" : "丽江市",
			"SPELLING" : "Lijiang"
		},
		{
			"ID" : "530702",
			"PARENT_ID" : "530700",
			"DISTRICT_NAME" : "古城区",
			"SPELLING" : "Gucheng"
		},
		{
			"ID" : "530721",
			"PARENT_ID" : "530700",
			"DISTRICT_NAME" : "玉龙纳西族自治县",
			"SPELLING" : "Yulong"
		},
		{
			"ID" : "530722",
			"PARENT_ID" : "530700",
			"DISTRICT_NAME" : "永胜县",
			"SPELLING" : "Yongsheng"
		},
		{
			"ID" : "530723",
			"PARENT_ID" : "530700",
			"DISTRICT_NAME" : "华坪县",
			"SPELLING" : "Huaping"
		},
		{
			"ID" : "530724",
			"PARENT_ID" : "530700",
			"DISTRICT_NAME" : "宁蒗彝族自治县",
			"SPELLING" : "Ninglang"
		},
		{
			"ID" : "530800",
			"PARENT_ID" : "530000",
			"DISTRICT_NAME" : "普洱市",
			"SPELLING" : "Puer"
		},
		{
			"ID" : "530802",
			"PARENT_ID" : "530800",
			"DISTRICT_NAME" : "思茅区",
			"SPELLING" : "Simao"
		},
		{
			"ID" : "530821",
			"PARENT_ID" : "530800",
			"DISTRICT_NAME" : "宁洱哈尼族彝族自治县",
			"SPELLING" : "Ninger"
		},
		{
			"ID" : "530822",
			"PARENT_ID" : "530800",
			"DISTRICT_NAME" : "墨江哈尼族自治县",
			"SPELLING" : "Mojiang"
		},
		{
			"ID" : "530823",
			"PARENT_ID" : "530800",
			"DISTRICT_NAME" : "景东彝族自治县",
			"SPELLING" : "Jingdong"
		},
		{
			"ID" : "530824",
			"PARENT_ID" : "530800",
			"DISTRICT_NAME" : "景谷傣族彝族自治县",
			"SPELLING" : "Jinggu"
		},
		{
			"ID" : "530825",
			"PARENT_ID" : "530800",
			"DISTRICT_NAME" : "镇沅彝族哈尼族拉祜族自治县",
			"SPELLING" : "Zhenyuan"
		},
		{
			"ID" : "530826",
			"PARENT_ID" : "530800",
			"DISTRICT_NAME" : "江城哈尼族彝族自治县",
			"SPELLING" : "Jiangcheng"
		},
		{
			"ID" : "530827",
			"PARENT_ID" : "530800",
			"DISTRICT_NAME" : "孟连傣族拉祜族佤族自治县",
			"SPELLING" : "Menglian"
		},
		{
			"ID" : "530828",
			"PARENT_ID" : "530800",
			"DISTRICT_NAME" : "澜沧拉祜族自治县",
			"SPELLING" : "Lancang"
		},
		{
			"ID" : "530829",
			"PARENT_ID" : "530800",
			"DISTRICT_NAME" : "西盟佤族自治县",
			"SPELLING" : "Ximeng"
		},
		{
			"ID" : "530900",
			"PARENT_ID" : "530000",
			"DISTRICT_NAME" : "临沧市",
			"SPELLING" : "Lincang"
		},
		{
			"ID" : "530902",
			"PARENT_ID" : "530900",
			"DISTRICT_NAME" : "临翔区",
			"SPELLING" : "Linxiang"
		},
		{
			"ID" : "530921",
			"PARENT_ID" : "530900",
			"DISTRICT_NAME" : "凤庆县",
			"SPELLING" : "Fengqing"
		},
		{
			"ID" : "530922",
			"PARENT_ID" : "530900",
			"DISTRICT_NAME" : "云县",
			"SPELLING" : "Yunxian"
		},
		{
			"ID" : "530923",
			"PARENT_ID" : "530900",
			"DISTRICT_NAME" : "永德县",
			"SPELLING" : "Yongde"
		},
		{
			"ID" : "530924",
			"PARENT_ID" : "530900",
			"DISTRICT_NAME" : "镇康县",
			"SPELLING" : "Zhenkang"
		},
		{
			"ID" : "530925",
			"PARENT_ID" : "530900",
			"DISTRICT_NAME" : "双江拉祜族佤族布朗族傣族自治县",
			"SPELLING" : "Shuangjiang"
		},
		{
			"ID" : "530926",
			"PARENT_ID" : "530900",
			"DISTRICT_NAME" : "耿马傣族佤族自治县",
			"SPELLING" : "Gengma"
		},
		{
			"ID" : "530927",
			"PARENT_ID" : "530900",
			"DISTRICT_NAME" : "沧源佤族自治县",
			"SPELLING" : "Cangyuan"
		},
		{
			"ID" : "532300",
			"PARENT_ID" : "530000",
			"DISTRICT_NAME" : "楚雄彝族自治州",
			"SPELLING" : "Chuxiong"
		},
		{
			"ID" : "532301",
			"PARENT_ID" : "532300",
			"DISTRICT_NAME" : "楚雄市",
			"SPELLING" : "Chuxiong"
		},
		{
			"ID" : "532322",
			"PARENT_ID" : "532300",
			"DISTRICT_NAME" : "双柏县",
			"SPELLING" : "Shuangbai"
		},
		{
			"ID" : "532323",
			"PARENT_ID" : "532300",
			"DISTRICT_NAME" : "牟定县",
			"SPELLING" : "Mouding"
		},
		{
			"ID" : "532324",
			"PARENT_ID" : "532300",
			"DISTRICT_NAME" : "南华县",
			"SPELLING" : "Nanhua"
		},
		{
			"ID" : "532325",
			"PARENT_ID" : "532300",
			"DISTRICT_NAME" : "姚安县",
			"SPELLING" : "Yaoan"
		},
		{
			"ID" : "532326",
			"PARENT_ID" : "532300",
			"DISTRICT_NAME" : "大姚县",
			"SPELLING" : "Dayao"
		},
		{
			"ID" : "532327",
			"PARENT_ID" : "532300",
			"DISTRICT_NAME" : "永仁县",
			"SPELLING" : "Yongren"
		},
		{
			"ID" : "532328",
			"PARENT_ID" : "532300",
			"DISTRICT_NAME" : "元谋县",
			"SPELLING" : "Yuanmou"
		},
		{
			"ID" : "532329",
			"PARENT_ID" : "532300",
			"DISTRICT_NAME" : "武定县",
			"SPELLING" : "Wuding"
		},
		{
			"ID" : "532331",
			"PARENT_ID" : "532300",
			"DISTRICT_NAME" : "禄丰县",
			"SPELLING" : "Lufeng"
		},
		{
			"ID" : "532500",
			"PARENT_ID" : "530000",
			"DISTRICT_NAME" : "红河哈尼族彝族自治州",
			"SPELLING" : "Honghe"
		},
		{
			"ID" : "532501",
			"PARENT_ID" : "532500",
			"DISTRICT_NAME" : "个旧市",
			"SPELLING" : "Gejiu"
		},
		{
			"ID" : "532502",
			"PARENT_ID" : "532500",
			"DISTRICT_NAME" : "开远市",
			"SPELLING" : "Kaiyuan"
		},
		{
			"ID" : "532503",
			"PARENT_ID" : "532500",
			"DISTRICT_NAME" : "蒙自市",
			"SPELLING" : "Mengzi"
		},
		{
			"ID" : "532504",
			"PARENT_ID" : "532500",
			"DISTRICT_NAME" : "弥勒市",
			"SPELLING" : "Mile "
		},
		{
			"ID" : "532523",
			"PARENT_ID" : "532500",
			"DISTRICT_NAME" : "屏边苗族自治县",
			"SPELLING" : "Pingbian"
		},
		{
			"ID" : "532524",
			"PARENT_ID" : "532500",
			"DISTRICT_NAME" : "建水县",
			"SPELLING" : "Jianshui"
		},
		{
			"ID" : "532525",
			"PARENT_ID" : "532500",
			"DISTRICT_NAME" : "石屏县",
			"SPELLING" : "Shiping"
		},
		{
			"ID" : "532527",
			"PARENT_ID" : "532500",
			"DISTRICT_NAME" : "泸西县",
			"SPELLING" : "Luxi"
		},
		{
			"ID" : "532528",
			"PARENT_ID" : "532500",
			"DISTRICT_NAME" : "元阳县",
			"SPELLING" : "Yuanyang"
		},
		{
			"ID" : "532529",
			"PARENT_ID" : "532500",
			"DISTRICT_NAME" : "红河县",
			"SPELLING" : "Honghexian"
		},
		{
			"ID" : "532530",
			"PARENT_ID" : "532500",
			"DISTRICT_NAME" : "金平苗族瑶族傣族自治县",
			"SPELLING" : "Jinping"
		},
		{
			"ID" : "532531",
			"PARENT_ID" : "532500",
			"DISTRICT_NAME" : "绿春县",
			"SPELLING" : "Lvchun"
		},
		{
			"ID" : "532532",
			"PARENT_ID" : "532500",
			"DISTRICT_NAME" : "河口瑶族自治县",
			"SPELLING" : "Hekou"
		},
		{
			"ID" : "532600",
			"PARENT_ID" : "530000",
			"DISTRICT_NAME" : "文山壮族苗族自治州",
			"SPELLING" : "Wenshan"
		},
		{
			"ID" : "532601",
			"PARENT_ID" : "532600",
			"DISTRICT_NAME" : "文山市",
			"SPELLING" : "Wenshan"
		},
		{
			"ID" : "532622",
			"PARENT_ID" : "532600",
			"DISTRICT_NAME" : "砚山县",
			"SPELLING" : "Yanshan"
		},
		{
			"ID" : "532623",
			"PARENT_ID" : "532600",
			"DISTRICT_NAME" : "西畴县",
			"SPELLING" : "Xichou"
		},
		{
			"ID" : "532624",
			"PARENT_ID" : "532600",
			"DISTRICT_NAME" : "麻栗坡县",
			"SPELLING" : "Malipo"
		},
		{
			"ID" : "532625",
			"PARENT_ID" : "532600",
			"DISTRICT_NAME" : "马关县",
			"SPELLING" : "Maguan"
		},
		{
			"ID" : "532626",
			"PARENT_ID" : "532600",
			"DISTRICT_NAME" : "丘北县",
			"SPELLING" : "Qiubei"
		},
		{
			"ID" : "532627",
			"PARENT_ID" : "532600",
			"DISTRICT_NAME" : "广南县",
			"SPELLING" : "Guangnan"
		},
		{
			"ID" : "532628",
			"PARENT_ID" : "532600",
			"DISTRICT_NAME" : "富宁县",
			"SPELLING" : "Funing"
		},
		{
			"ID" : "532800",
			"PARENT_ID" : "530000",
			"DISTRICT_NAME" : "西双版纳傣族自治州",
			"SPELLING" : "Xishuangbanna"
		},
		{
			"ID" : "532801",
			"PARENT_ID" : "532800",
			"DISTRICT_NAME" : "景洪市",
			"SPELLING" : "Jinghong"
		},
		{
			"ID" : "532822",
			"PARENT_ID" : "532800",
			"DISTRICT_NAME" : "勐海县",
			"SPELLING" : "Menghai"
		},
		{
			"ID" : "532823",
			"PARENT_ID" : "532800",
			"DISTRICT_NAME" : "勐腊县",
			"SPELLING" : "Mengla"
		},
		{
			"ID" : "532900",
			"PARENT_ID" : "530000",
			"DISTRICT_NAME" : "大理白族自治州",
			"SPELLING" : "Dali"
		},
		{
			"ID" : "532901",
			"PARENT_ID" : "532900",
			"DISTRICT_NAME" : "大理市",
			"SPELLING" : "Dali"
		},
		{
			"ID" : "532922",
			"PARENT_ID" : "532900",
			"DISTRICT_NAME" : "漾濞彝族自治县",
			"SPELLING" : "Yangbi"
		},
		{
			"ID" : "532923",
			"PARENT_ID" : "532900",
			"DISTRICT_NAME" : "祥云县",
			"SPELLING" : "Xiangyun"
		},
		{
			"ID" : "532924",
			"PARENT_ID" : "532900",
			"DISTRICT_NAME" : "宾川县",
			"SPELLING" : "Binchuan"
		},
		{
			"ID" : "532925",
			"PARENT_ID" : "532900",
			"DISTRICT_NAME" : "弥渡县",
			"SPELLING" : "Midu"
		},
		{
			"ID" : "532926",
			"PARENT_ID" : "532900",
			"DISTRICT_NAME" : "南涧彝族自治县",
			"SPELLING" : "Nanjian"
		},
		{
			"ID" : "532927",
			"PARENT_ID" : "532900",
			"DISTRICT_NAME" : "巍山彝族回族自治县",
			"SPELLING" : "Weishan"
		},
		{
			"ID" : "532928",
			"PARENT_ID" : "532900",
			"DISTRICT_NAME" : "永平县",
			"SPELLING" : "Yongping"
		},
		{
			"ID" : "532929",
			"PARENT_ID" : "532900",
			"DISTRICT_NAME" : "云龙县",
			"SPELLING" : "Yunlong"
		},
		{
			"ID" : "532930",
			"PARENT_ID" : "532900",
			"DISTRICT_NAME" : "洱源县",
			"SPELLING" : "Eryuan"
		},
		{
			"ID" : "532931",
			"PARENT_ID" : "532900",
			"DISTRICT_NAME" : "剑川县",
			"SPELLING" : "Jianchuan"
		},
		{
			"ID" : "532932",
			"PARENT_ID" : "532900",
			"DISTRICT_NAME" : "鹤庆县",
			"SPELLING" : "Heqing"
		},
		{
			"ID" : "533100",
			"PARENT_ID" : "530000",
			"DISTRICT_NAME" : "德宏傣族景颇族自治州",
			"SPELLING" : "Dehong"
		},
		{
			"ID" : "533102",
			"PARENT_ID" : "533100",
			"DISTRICT_NAME" : "瑞丽市",
			"SPELLING" : "Ruili"
		},
		{
			"ID" : "533103",
			"PARENT_ID" : "533100",
			"DISTRICT_NAME" : "芒市",
			"SPELLING" : "Mangshi"
		},
		{
			"ID" : "533122",
			"PARENT_ID" : "533100",
			"DISTRICT_NAME" : "梁河县",
			"SPELLING" : "Lianghe"
		},
		{
			"ID" : "533123",
			"PARENT_ID" : "533100",
			"DISTRICT_NAME" : "盈江县",
			"SPELLING" : "Yingjiang"
		},
		{
			"ID" : "533124",
			"PARENT_ID" : "533100",
			"DISTRICT_NAME" : "陇川县",
			"SPELLING" : "Longchuan"
		},
		{
			"ID" : "533300",
			"PARENT_ID" : "530000",
			"DISTRICT_NAME" : "怒江傈僳族自治州",
			"SPELLING" : "Nujiang"
		},
		{
			"ID" : "533321",
			"PARENT_ID" : "533300",
			"DISTRICT_NAME" : "泸水县",
			"SPELLING" : "Lushui"
		},
		{
			"ID" : "533323",
			"PARENT_ID" : "533300",
			"DISTRICT_NAME" : "福贡县",
			"SPELLING" : "Fugong"
		},
		{
			"ID" : "533324",
			"PARENT_ID" : "533300",
			"DISTRICT_NAME" : "贡山独龙族怒族自治县",
			"SPELLING" : "Gongshan"
		},
		{
			"ID" : "533325",
			"PARENT_ID" : "533300",
			"DISTRICT_NAME" : "兰坪白族普米族自治县",
			"SPELLING" : "Lanping"
		},
		{
			"ID" : "533400",
			"PARENT_ID" : "530000",
			"DISTRICT_NAME" : "迪庆藏族自治州",
			"SPELLING" : "Deqen"
		},
		{
			"ID" : "533421",
			"PARENT_ID" : "533400",
			"DISTRICT_NAME" : "香格里拉市",
			"SPELLING" : "Xianggelila"
		},
		{
			"ID" : "533422",
			"PARENT_ID" : "533400",
			"DISTRICT_NAME" : "德钦县",
			"SPELLING" : "Deqin"
		},
		{
			"ID" : "533423",
			"PARENT_ID" : "533400",
			"DISTRICT_NAME" : "维西傈僳族自治县",
			"SPELLING" : "Weixi"
		},
		{
			"ID" : "540000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "西藏自治区",
			"SPELLING" : "Tibet"
		},
		{
			"ID" : "540100",
			"PARENT_ID" : "540000",
			"DISTRICT_NAME" : "拉萨市",
			"SPELLING" : "Lhasa"
		},
		{
			"ID" : "540102",
			"PARENT_ID" : "540100",
			"DISTRICT_NAME" : "城关区",
			"SPELLING" : "Chengguan"
		},
		{
			"ID" : "540121",
			"PARENT_ID" : "540100",
			"DISTRICT_NAME" : "林周县",
			"SPELLING" : "Linzhou"
		},
		{
			"ID" : "540122",
			"PARENT_ID" : "540100",
			"DISTRICT_NAME" : "当雄县",
			"SPELLING" : "Dangxiong"
		},
		{
			"ID" : "540123",
			"PARENT_ID" : "540100",
			"DISTRICT_NAME" : "尼木县",
			"SPELLING" : "Nimu"
		},
		{
			"ID" : "540124",
			"PARENT_ID" : "540100",
			"DISTRICT_NAME" : "曲水县",
			"SPELLING" : "Qushui"
		},
		{
			"ID" : "540125",
			"PARENT_ID" : "540100",
			"DISTRICT_NAME" : "堆龙德庆县",
			"SPELLING" : "Duilongdeqing"
		},
		{
			"ID" : "540126",
			"PARENT_ID" : "540100",
			"DISTRICT_NAME" : "达孜县",
			"SPELLING" : "Dazi"
		},
		{
			"ID" : "540127",
			"PARENT_ID" : "540100",
			"DISTRICT_NAME" : "墨竹工卡县",
			"SPELLING" : "Mozhugongka"
		},
		{
			"ID" : "540200",
			"PARENT_ID" : "540000",
			"DISTRICT_NAME" : "日喀则市",
			"SPELLING" : "Rikaze"
		},
		{
			"ID" : "540202",
			"PARENT_ID" : "540200",
			"DISTRICT_NAME" : "桑珠孜区",
			"SPELLING" : "Sangzhuzi"
		},
		{
			"ID" : "540221",
			"PARENT_ID" : "540200",
			"DISTRICT_NAME" : "南木林县",
			"SPELLING" : "Nanmulin"
		},
		{
			"ID" : "540222",
			"PARENT_ID" : "540200",
			"DISTRICT_NAME" : "江孜县",
			"SPELLING" : "Jiangzi"
		},
		{
			"ID" : "540223",
			"PARENT_ID" : "540200",
			"DISTRICT_NAME" : "定日县",
			"SPELLING" : "Dingri"
		},
		{
			"ID" : "540224",
			"PARENT_ID" : "540200",
			"DISTRICT_NAME" : "萨迦县",
			"SPELLING" : "Sajia"
		},
		{
			"ID" : "540225",
			"PARENT_ID" : "540200",
			"DISTRICT_NAME" : "拉孜县",
			"SPELLING" : "Lazi"
		},
		{
			"ID" : "540226",
			"PARENT_ID" : "540200",
			"DISTRICT_NAME" : "昂仁县",
			"SPELLING" : "Angren"
		},
		{
			"ID" : "540227",
			"PARENT_ID" : "540200",
			"DISTRICT_NAME" : "谢通门县",
			"SPELLING" : "Xietongmen"
		},
		{
			"ID" : "540228",
			"PARENT_ID" : "540200",
			"DISTRICT_NAME" : "白朗县",
			"SPELLING" : "Bailang"
		},
		{
			"ID" : "540229",
			"PARENT_ID" : "540200",
			"DISTRICT_NAME" : "仁布县",
			"SPELLING" : "Renbu"
		},
		{
			"ID" : "540230",
			"PARENT_ID" : "540200",
			"DISTRICT_NAME" : "康马县",
			"SPELLING" : "Kangma"
		},
		{
			"ID" : "540231",
			"PARENT_ID" : "540200",
			"DISTRICT_NAME" : "定结县",
			"SPELLING" : "Dingjie"
		},
		{
			"ID" : "540232",
			"PARENT_ID" : "540200",
			"DISTRICT_NAME" : "仲巴县",
			"SPELLING" : "Zhongba"
		},
		{
			"ID" : "540233",
			"PARENT_ID" : "540200",
			"DISTRICT_NAME" : "亚东县",
			"SPELLING" : "Yadong"
		},
		{
			"ID" : "540234",
			"PARENT_ID" : "540200",
			"DISTRICT_NAME" : "吉隆县",
			"SPELLING" : "Jilong"
		},
		{
			"ID" : "540235",
			"PARENT_ID" : "540200",
			"DISTRICT_NAME" : "聂拉木县",
			"SPELLING" : "Nielamu"
		},
		{
			"ID" : "540236",
			"PARENT_ID" : "540200",
			"DISTRICT_NAME" : "萨嘎县",
			"SPELLING" : "Saga"
		},
		{
			"ID" : "540237",
			"PARENT_ID" : "540200",
			"DISTRICT_NAME" : "岗巴县",
			"SPELLING" : "Gangba"
		},
		{
			"ID" : "540300",
			"PARENT_ID" : "540000",
			"DISTRICT_NAME" : "昌都市",
			"SPELLING" : "Qamdo"
		},
		{
			"ID" : "540302",
			"PARENT_ID" : "540300",
			"DISTRICT_NAME" : "卡若区",
			"SPELLING" : "Karuo"
		},
		{
			"ID" : "540321",
			"PARENT_ID" : "540300",
			"DISTRICT_NAME" : "江达县",
			"SPELLING" : "Jiangda"
		},
		{
			"ID" : "540322",
			"PARENT_ID" : "540300",
			"DISTRICT_NAME" : "贡觉县",
			"SPELLING" : "Gongjue"
		},
		{
			"ID" : "540323",
			"PARENT_ID" : "540300",
			"DISTRICT_NAME" : "类乌齐县",
			"SPELLING" : "Leiwuqi"
		},
		{
			"ID" : "540324",
			"PARENT_ID" : "540300",
			"DISTRICT_NAME" : "丁青县",
			"SPELLING" : "Dingqing"
		},
		{
			"ID" : "540325",
			"PARENT_ID" : "540300",
			"DISTRICT_NAME" : "察雅县",
			"SPELLING" : "Chaya"
		},
		{
			"ID" : "540326",
			"PARENT_ID" : "540300",
			"DISTRICT_NAME" : "八宿县",
			"SPELLING" : "Basu"
		},
		{
			"ID" : "540327",
			"PARENT_ID" : "540300",
			"DISTRICT_NAME" : "左贡县",
			"SPELLING" : "Zuogong"
		},
		{
			"ID" : "540328",
			"PARENT_ID" : "540300",
			"DISTRICT_NAME" : "芒康县",
			"SPELLING" : "Mangkang"
		},
		{
			"ID" : "540329",
			"PARENT_ID" : "540300",
			"DISTRICT_NAME" : "洛隆县",
			"SPELLING" : "Luolong"
		},
		{
			"ID" : "540330",
			"PARENT_ID" : "540300",
			"DISTRICT_NAME" : "边坝县",
			"SPELLING" : "Bianba"
		},
		{
			"ID" : "542200",
			"PARENT_ID" : "540000",
			"DISTRICT_NAME" : "山南地区",
			"SPELLING" : "Shannan"
		},
		{
			"ID" : "542221",
			"PARENT_ID" : "542200",
			"DISTRICT_NAME" : "乃东县",
			"SPELLING" : "Naidong"
		},
		{
			"ID" : "542222",
			"PARENT_ID" : "542200",
			"DISTRICT_NAME" : "扎囊县",
			"SPELLING" : "Zhanang"
		},
		{
			"ID" : "542223",
			"PARENT_ID" : "542200",
			"DISTRICT_NAME" : "贡嘎县",
			"SPELLING" : "Gongga"
		},
		{
			"ID" : "542224",
			"PARENT_ID" : "542200",
			"DISTRICT_NAME" : "桑日县",
			"SPELLING" : "Sangri"
		},
		{
			"ID" : "542225",
			"PARENT_ID" : "542200",
			"DISTRICT_NAME" : "琼结县",
			"SPELLING" : "Qiongjie"
		},
		{
			"ID" : "542226",
			"PARENT_ID" : "542200",
			"DISTRICT_NAME" : "曲松县",
			"SPELLING" : "Qusong"
		},
		{
			"ID" : "542227",
			"PARENT_ID" : "542200",
			"DISTRICT_NAME" : "措美县",
			"SPELLING" : "Cuomei"
		},
		{
			"ID" : "542228",
			"PARENT_ID" : "542200",
			"DISTRICT_NAME" : "洛扎县",
			"SPELLING" : "Luozha"
		},
		{
			"ID" : "542229",
			"PARENT_ID" : "542200",
			"DISTRICT_NAME" : "加查县",
			"SPELLING" : "Jiacha"
		},
		{
			"ID" : "542231",
			"PARENT_ID" : "542200",
			"DISTRICT_NAME" : "隆子县",
			"SPELLING" : "Longzi"
		},
		{
			"ID" : "542232",
			"PARENT_ID" : "542200",
			"DISTRICT_NAME" : "错那县",
			"SPELLING" : "Cuona"
		},
		{
			"ID" : "542233",
			"PARENT_ID" : "542200",
			"DISTRICT_NAME" : "浪卡子县",
			"SPELLING" : "Langkazi"
		},
		{
			"ID" : "542400",
			"PARENT_ID" : "540000",
			"DISTRICT_NAME" : "那曲地区",
			"SPELLING" : "Nagqu"
		},
		{
			"ID" : "542421",
			"PARENT_ID" : "542400",
			"DISTRICT_NAME" : "那曲县",
			"SPELLING" : "Naqu"
		},
		{
			"ID" : "542422",
			"PARENT_ID" : "542400",
			"DISTRICT_NAME" : "嘉黎县",
			"SPELLING" : "Jiali"
		},
		{
			"ID" : "542423",
			"PARENT_ID" : "542400",
			"DISTRICT_NAME" : "比如县",
			"SPELLING" : "Biru"
		},
		{
			"ID" : "542424",
			"PARENT_ID" : "542400",
			"DISTRICT_NAME" : "聂荣县",
			"SPELLING" : "Nierong"
		},
		{
			"ID" : "542425",
			"PARENT_ID" : "542400",
			"DISTRICT_NAME" : "安多县",
			"SPELLING" : "Anduo"
		},
		{
			"ID" : "542426",
			"PARENT_ID" : "542400",
			"DISTRICT_NAME" : "申扎县",
			"SPELLING" : "Shenzha"
		},
		{
			"ID" : "542427",
			"PARENT_ID" : "542400",
			"DISTRICT_NAME" : "索县",
			"SPELLING" : "Suoxian"
		},
		{
			"ID" : "542428",
			"PARENT_ID" : "542400",
			"DISTRICT_NAME" : "班戈县",
			"SPELLING" : "Bange"
		},
		{
			"ID" : "542429",
			"PARENT_ID" : "542400",
			"DISTRICT_NAME" : "巴青县",
			"SPELLING" : "Baqing"
		},
		{
			"ID" : "542430",
			"PARENT_ID" : "542400",
			"DISTRICT_NAME" : "尼玛县",
			"SPELLING" : "Nima"
		},
		{
			"ID" : "542431",
			"PARENT_ID" : "542400",
			"DISTRICT_NAME" : "双湖县",
			"SPELLING" : "Shuanghu"
		},
		{
			"ID" : "542500",
			"PARENT_ID" : "540000",
			"DISTRICT_NAME" : "阿里地区",
			"SPELLING" : "Ngari"
		},
		{
			"ID" : "542521",
			"PARENT_ID" : "542500",
			"DISTRICT_NAME" : "普兰县",
			"SPELLING" : "Pulan"
		},
		{
			"ID" : "542522",
			"PARENT_ID" : "542500",
			"DISTRICT_NAME" : "札达县",
			"SPELLING" : "Zhada"
		},
		{
			"ID" : "542523",
			"PARENT_ID" : "542500",
			"DISTRICT_NAME" : "噶尔县",
			"SPELLING" : "Gaer"
		},
		{
			"ID" : "542524",
			"PARENT_ID" : "542500",
			"DISTRICT_NAME" : "日土县",
			"SPELLING" : "Ritu"
		},
		{
			"ID" : "542525",
			"PARENT_ID" : "542500",
			"DISTRICT_NAME" : "革吉县",
			"SPELLING" : "Geji"
		},
		{
			"ID" : "542526",
			"PARENT_ID" : "542500",
			"DISTRICT_NAME" : "改则县",
			"SPELLING" : "Gaize"
		},
		{
			"ID" : "542527",
			"PARENT_ID" : "542500",
			"DISTRICT_NAME" : "措勤县",
			"SPELLING" : "Cuoqin"
		},
		{
			"ID" : "542600",
			"PARENT_ID" : "540000",
			"DISTRICT_NAME" : "林芝地区",
			"SPELLING" : "Nyingchi"
		},
		{
			"ID" : "542621",
			"PARENT_ID" : "542600",
			"DISTRICT_NAME" : "林芝县",
			"SPELLING" : "Linzhi"
		},
		{
			"ID" : "542622",
			"PARENT_ID" : "542600",
			"DISTRICT_NAME" : "工布江达县",
			"SPELLING" : "Gongbujiangda"
		},
		{
			"ID" : "542623",
			"PARENT_ID" : "542600",
			"DISTRICT_NAME" : "米林县",
			"SPELLING" : "Milin"
		},
		{
			"ID" : "542624",
			"PARENT_ID" : "542600",
			"DISTRICT_NAME" : "墨脱县",
			"SPELLING" : "Motuo"
		},
		{
			"ID" : "542625",
			"PARENT_ID" : "542600",
			"DISTRICT_NAME" : "波密县",
			"SPELLING" : "Bomi"
		},
		{
			"ID" : "542626",
			"PARENT_ID" : "542600",
			"DISTRICT_NAME" : "察隅县",
			"SPELLING" : "Chayu"
		},
		{
			"ID" : "542627",
			"PARENT_ID" : "542600",
			"DISTRICT_NAME" : "朗县",
			"SPELLING" : "Langxian"
		},
		{
			"ID" : "610000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "陕西省",
			"SPELLING" : "Shaanxi"
		},
		{
			"ID" : "610100",
			"PARENT_ID" : "610000",
			"DISTRICT_NAME" : "西安市",
			"SPELLING" : "Xian"
		},
		{
			"ID" : "610102",
			"PARENT_ID" : "610100",
			"DISTRICT_NAME" : "新城区",
			"SPELLING" : "Xincheng"
		},
		{
			"ID" : "610103",
			"PARENT_ID" : "610100",
			"DISTRICT_NAME" : "碑林区",
			"SPELLING" : "Beilin"
		},
		{
			"ID" : "610104",
			"PARENT_ID" : "610100",
			"DISTRICT_NAME" : "莲湖区",
			"SPELLING" : "Lianhu"
		},
		{
			"ID" : "610111",
			"PARENT_ID" : "610100",
			"DISTRICT_NAME" : "灞桥区",
			"SPELLING" : "Baqiao"
		},
		{
			"ID" : "610112",
			"PARENT_ID" : "610100",
			"DISTRICT_NAME" : "未央区",
			"SPELLING" : "Weiyang"
		},
		{
			"ID" : "610113",
			"PARENT_ID" : "610100",
			"DISTRICT_NAME" : "雁塔区",
			"SPELLING" : "Yanta"
		},
		{
			"ID" : "610114",
			"PARENT_ID" : "610100",
			"DISTRICT_NAME" : "阎良区",
			"SPELLING" : "Yanliang"
		},
		{
			"ID" : "610115",
			"PARENT_ID" : "610100",
			"DISTRICT_NAME" : "临潼区",
			"SPELLING" : "Lintong"
		},
		{
			"ID" : "610116",
			"PARENT_ID" : "610100",
			"DISTRICT_NAME" : "长安区",
			"SPELLING" : "Changan"
		},
		{
			"ID" : "610122",
			"PARENT_ID" : "610100",
			"DISTRICT_NAME" : "蓝田县",
			"SPELLING" : "Lantian"
		},
		{
			"ID" : "610124",
			"PARENT_ID" : "610100",
			"DISTRICT_NAME" : "周至县",
			"SPELLING" : "Zhouzhi"
		},
		{
			"ID" : "610125",
			"PARENT_ID" : "610100",
			"DISTRICT_NAME" : "户县",
			"SPELLING" : "Huxian"
		},
		{
			"ID" : "610126",
			"PARENT_ID" : "610100",
			"DISTRICT_NAME" : "高陵区",
			"SPELLING" : "Gaoling"
		},
		{
			"ID" : "610200",
			"PARENT_ID" : "610000",
			"DISTRICT_NAME" : "铜川市",
			"SPELLING" : "Tongchuan"
		},
		{
			"ID" : "610202",
			"PARENT_ID" : "610200",
			"DISTRICT_NAME" : "王益区",
			"SPELLING" : "Wangyi"
		},
		{
			"ID" : "610203",
			"PARENT_ID" : "610200",
			"DISTRICT_NAME" : "印台区",
			"SPELLING" : "Yintai"
		},
		{
			"ID" : "610204",
			"PARENT_ID" : "610200",
			"DISTRICT_NAME" : "耀州区",
			"SPELLING" : "Yaozhou"
		},
		{
			"ID" : "610222",
			"PARENT_ID" : "610200",
			"DISTRICT_NAME" : "宜君县",
			"SPELLING" : "Yijun"
		},
		{
			"ID" : "610300",
			"PARENT_ID" : "610000",
			"DISTRICT_NAME" : "宝鸡市",
			"SPELLING" : "Baoji"
		},
		{
			"ID" : "610302",
			"PARENT_ID" : "610300",
			"DISTRICT_NAME" : "渭滨区",
			"SPELLING" : "Weibin"
		},
		{
			"ID" : "610303",
			"PARENT_ID" : "610300",
			"DISTRICT_NAME" : "金台区",
			"SPELLING" : "Jintai"
		},
		{
			"ID" : "610304",
			"PARENT_ID" : "610300",
			"DISTRICT_NAME" : "陈仓区",
			"SPELLING" : "Chencang"
		},
		{
			"ID" : "610322",
			"PARENT_ID" : "610300",
			"DISTRICT_NAME" : "凤翔县",
			"SPELLING" : "Fengxiang"
		},
		{
			"ID" : "610323",
			"PARENT_ID" : "610300",
			"DISTRICT_NAME" : "岐山县",
			"SPELLING" : "Qishan"
		},
		{
			"ID" : "610324",
			"PARENT_ID" : "610300",
			"DISTRICT_NAME" : "扶风县",
			"SPELLING" : "Fufeng"
		},
		{
			"ID" : "610326",
			"PARENT_ID" : "610300",
			"DISTRICT_NAME" : "眉县",
			"SPELLING" : "Meixian"
		},
		{
			"ID" : "610327",
			"PARENT_ID" : "610300",
			"DISTRICT_NAME" : "陇县",
			"SPELLING" : "Longxian"
		},
		{
			"ID" : "610328",
			"PARENT_ID" : "610300",
			"DISTRICT_NAME" : "千阳县",
			"SPELLING" : "Qianyang"
		},
		{
			"ID" : "610329",
			"PARENT_ID" : "610300",
			"DISTRICT_NAME" : "麟游县",
			"SPELLING" : "Linyou"
		},
		{
			"ID" : "610330",
			"PARENT_ID" : "610300",
			"DISTRICT_NAME" : "凤县",
			"SPELLING" : "Fengxian"
		},
		{
			"ID" : "610331",
			"PARENT_ID" : "610300",
			"DISTRICT_NAME" : "太白县",
			"SPELLING" : "Taibai"
		},
		{
			"ID" : "610400",
			"PARENT_ID" : "610000",
			"DISTRICT_NAME" : "咸阳市",
			"SPELLING" : "Xianyang"
		},
		{
			"ID" : "610402",
			"PARENT_ID" : "610400",
			"DISTRICT_NAME" : "秦都区",
			"SPELLING" : "Qindu"
		},
		{
			"ID" : "610403",
			"PARENT_ID" : "610400",
			"DISTRICT_NAME" : "杨陵区",
			"SPELLING" : "Yangling"
		},
		{
			"ID" : "610404",
			"PARENT_ID" : "610400",
			"DISTRICT_NAME" : "渭城区",
			"SPELLING" : "Weicheng"
		},
		{
			"ID" : "610422",
			"PARENT_ID" : "610400",
			"DISTRICT_NAME" : "三原县",
			"SPELLING" : "Sanyuan"
		},
		{
			"ID" : "610423",
			"PARENT_ID" : "610400",
			"DISTRICT_NAME" : "泾阳县",
			"SPELLING" : "Jingyang"
		},
		{
			"ID" : "610424",
			"PARENT_ID" : "610400",
			"DISTRICT_NAME" : "乾县",
			"SPELLING" : "Qianxian"
		},
		{
			"ID" : "610425",
			"PARENT_ID" : "610400",
			"DISTRICT_NAME" : "礼泉县",
			"SPELLING" : "Liquan"
		},
		{
			"ID" : "610426",
			"PARENT_ID" : "610400",
			"DISTRICT_NAME" : "永寿县",
			"SPELLING" : "Yongshou"
		},
		{
			"ID" : "610427",
			"PARENT_ID" : "610400",
			"DISTRICT_NAME" : "彬县",
			"SPELLING" : "Binxian"
		},
		{
			"ID" : "610428",
			"PARENT_ID" : "610400",
			"DISTRICT_NAME" : "长武县",
			"SPELLING" : "Changwu"
		},
		{
			"ID" : "610429",
			"PARENT_ID" : "610400",
			"DISTRICT_NAME" : "旬邑县",
			"SPELLING" : "Xunyi"
		},
		{
			"ID" : "610430",
			"PARENT_ID" : "610400",
			"DISTRICT_NAME" : "淳化县",
			"SPELLING" : "Chunhua"
		},
		{
			"ID" : "610431",
			"PARENT_ID" : "610400",
			"DISTRICT_NAME" : "武功县",
			"SPELLING" : "Wugong"
		},
		{
			"ID" : "610481",
			"PARENT_ID" : "610400",
			"DISTRICT_NAME" : "兴平市",
			"SPELLING" : "Xingping"
		},
		{
			"ID" : "610500",
			"PARENT_ID" : "610000",
			"DISTRICT_NAME" : "渭南市",
			"SPELLING" : "Weinan"
		},
		{
			"ID" : "610502",
			"PARENT_ID" : "610500",
			"DISTRICT_NAME" : "临渭区",
			"SPELLING" : "Linwei"
		},
		{
			"ID" : "610521",
			"PARENT_ID" : "610500",
			"DISTRICT_NAME" : "华县",
			"SPELLING" : "Huaxian"
		},
		{
			"ID" : "610522",
			"PARENT_ID" : "610500",
			"DISTRICT_NAME" : "潼关县",
			"SPELLING" : "Tongguan"
		},
		{
			"ID" : "610523",
			"PARENT_ID" : "610500",
			"DISTRICT_NAME" : "大荔县",
			"SPELLING" : "Dali"
		},
		{
			"ID" : "610524",
			"PARENT_ID" : "610500",
			"DISTRICT_NAME" : "合阳县",
			"SPELLING" : "Heyang"
		},
		{
			"ID" : "610525",
			"PARENT_ID" : "610500",
			"DISTRICT_NAME" : "澄城县",
			"SPELLING" : "Chengcheng"
		},
		{
			"ID" : "610526",
			"PARENT_ID" : "610500",
			"DISTRICT_NAME" : "蒲城县",
			"SPELLING" : "Pucheng"
		},
		{
			"ID" : "610527",
			"PARENT_ID" : "610500",
			"DISTRICT_NAME" : "白水县",
			"SPELLING" : "Baishui"
		},
		{
			"ID" : "610528",
			"PARENT_ID" : "610500",
			"DISTRICT_NAME" : "富平县",
			"SPELLING" : "Fuping"
		},
		{
			"ID" : "610581",
			"PARENT_ID" : "610500",
			"DISTRICT_NAME" : "韩城市",
			"SPELLING" : "Hancheng"
		},
		{
			"ID" : "610582",
			"PARENT_ID" : "610500",
			"DISTRICT_NAME" : "华阴市",
			"SPELLING" : "Huayin"
		},
		{
			"ID" : "610600",
			"PARENT_ID" : "610000",
			"DISTRICT_NAME" : "延安市",
			"SPELLING" : "Yanan"
		},
		{
			"ID" : "610602",
			"PARENT_ID" : "610600",
			"DISTRICT_NAME" : "宝塔区",
			"SPELLING" : "Baota"
		},
		{
			"ID" : "610621",
			"PARENT_ID" : "610600",
			"DISTRICT_NAME" : "延长县",
			"SPELLING" : "Yanchang"
		},
		{
			"ID" : "610622",
			"PARENT_ID" : "610600",
			"DISTRICT_NAME" : "延川县",
			"SPELLING" : "Yanchuan"
		},
		{
			"ID" : "610623",
			"PARENT_ID" : "610600",
			"DISTRICT_NAME" : "子长县",
			"SPELLING" : "Zichang"
		},
		{
			"ID" : "610624",
			"PARENT_ID" : "610600",
			"DISTRICT_NAME" : "安塞县",
			"SPELLING" : "Ansai"
		},
		{
			"ID" : "610625",
			"PARENT_ID" : "610600",
			"DISTRICT_NAME" : "志丹县",
			"SPELLING" : "Zhidan"
		},
		{
			"ID" : "610626",
			"PARENT_ID" : "610600",
			"DISTRICT_NAME" : "吴起县",
			"SPELLING" : "Wuqi"
		},
		{
			"ID" : "610627",
			"PARENT_ID" : "610600",
			"DISTRICT_NAME" : "甘泉县",
			"SPELLING" : "Ganquan"
		},
		{
			"ID" : "610628",
			"PARENT_ID" : "610600",
			"DISTRICT_NAME" : "富县",
			"SPELLING" : "Fuxian"
		},
		{
			"ID" : "610629",
			"PARENT_ID" : "610600",
			"DISTRICT_NAME" : "洛川县",
			"SPELLING" : "Luochuan"
		},
		{
			"ID" : "610630",
			"PARENT_ID" : "610600",
			"DISTRICT_NAME" : "宜川县",
			"SPELLING" : "Yichuan"
		},
		{
			"ID" : "610631",
			"PARENT_ID" : "610600",
			"DISTRICT_NAME" : "黄龙县",
			"SPELLING" : "Huanglong"
		},
		{
			"ID" : "610632",
			"PARENT_ID" : "610600",
			"DISTRICT_NAME" : "黄陵县",
			"SPELLING" : "Huangling"
		},
		{
			"ID" : "610700",
			"PARENT_ID" : "610000",
			"DISTRICT_NAME" : "汉中市",
			"SPELLING" : "Hanzhong"
		},
		{
			"ID" : "610702",
			"PARENT_ID" : "610700",
			"DISTRICT_NAME" : "汉台区",
			"SPELLING" : "Hantai"
		},
		{
			"ID" : "610721",
			"PARENT_ID" : "610700",
			"DISTRICT_NAME" : "南郑县",
			"SPELLING" : "Nanzheng"
		},
		{
			"ID" : "610722",
			"PARENT_ID" : "610700",
			"DISTRICT_NAME" : "城固县",
			"SPELLING" : "Chenggu"
		},
		{
			"ID" : "610723",
			"PARENT_ID" : "610700",
			"DISTRICT_NAME" : "洋县",
			"SPELLING" : "Yangxian"
		},
		{
			"ID" : "610724",
			"PARENT_ID" : "610700",
			"DISTRICT_NAME" : "西乡县",
			"SPELLING" : "Xixiang"
		},
		{
			"ID" : "610725",
			"PARENT_ID" : "610700",
			"DISTRICT_NAME" : "勉县",
			"SPELLING" : "Mianxian"
		},
		{
			"ID" : "610726",
			"PARENT_ID" : "610700",
			"DISTRICT_NAME" : "宁强县",
			"SPELLING" : "Ningqiang"
		},
		{
			"ID" : "610727",
			"PARENT_ID" : "610700",
			"DISTRICT_NAME" : "略阳县",
			"SPELLING" : "Lueyang"
		},
		{
			"ID" : "610728",
			"PARENT_ID" : "610700",
			"DISTRICT_NAME" : "镇巴县",
			"SPELLING" : "Zhenba"
		},
		{
			"ID" : "610729",
			"PARENT_ID" : "610700",
			"DISTRICT_NAME" : "留坝县",
			"SPELLING" : "Liuba"
		},
		{
			"ID" : "610730",
			"PARENT_ID" : "610700",
			"DISTRICT_NAME" : "佛坪县",
			"SPELLING" : "Foping"
		},
		{
			"ID" : "610800",
			"PARENT_ID" : "610000",
			"DISTRICT_NAME" : "榆林市",
			"SPELLING" : "Yulin"
		},
		{
			"ID" : "610802",
			"PARENT_ID" : "610800",
			"DISTRICT_NAME" : "榆阳区",
			"SPELLING" : "Yuyang"
		},
		{
			"ID" : "610821",
			"PARENT_ID" : "610800",
			"DISTRICT_NAME" : "神木县",
			"SPELLING" : "Shenmu"
		},
		{
			"ID" : "610822",
			"PARENT_ID" : "610800",
			"DISTRICT_NAME" : "府谷县",
			"SPELLING" : "Fugu"
		},
		{
			"ID" : "610823",
			"PARENT_ID" : "610800",
			"DISTRICT_NAME" : "横山县",
			"SPELLING" : "Hengshan"
		},
		{
			"ID" : "610824",
			"PARENT_ID" : "610800",
			"DISTRICT_NAME" : "靖边县",
			"SPELLING" : "Jingbian"
		},
		{
			"ID" : "610825",
			"PARENT_ID" : "610800",
			"DISTRICT_NAME" : "定边县",
			"SPELLING" : "Dingbian"
		},
		{
			"ID" : "610826",
			"PARENT_ID" : "610800",
			"DISTRICT_NAME" : "绥德县",
			"SPELLING" : "Suide"
		},
		{
			"ID" : "610827",
			"PARENT_ID" : "610800",
			"DISTRICT_NAME" : "米脂县",
			"SPELLING" : "Mizhi"
		},
		{
			"ID" : "610828",
			"PARENT_ID" : "610800",
			"DISTRICT_NAME" : "佳县",
			"SPELLING" : "Jiaxian"
		},
		{
			"ID" : "610829",
			"PARENT_ID" : "610800",
			"DISTRICT_NAME" : "吴堡县",
			"SPELLING" : "Wubu"
		},
		{
			"ID" : "610830",
			"PARENT_ID" : "610800",
			"DISTRICT_NAME" : "清涧县",
			"SPELLING" : "Qingjian"
		},
		{
			"ID" : "610831",
			"PARENT_ID" : "610800",
			"DISTRICT_NAME" : "子洲县",
			"SPELLING" : "Zizhou"
		},
		{
			"ID" : "610900",
			"PARENT_ID" : "610000",
			"DISTRICT_NAME" : "安康市",
			"SPELLING" : "Ankang"
		},
		{
			"ID" : "610902",
			"PARENT_ID" : "610900",
			"DISTRICT_NAME" : "汉滨区",
			"SPELLING" : "Hanbin"
		},
		{
			"ID" : "610921",
			"PARENT_ID" : "610900",
			"DISTRICT_NAME" : "汉阴县",
			"SPELLING" : "Hanyin"
		},
		{
			"ID" : "610922",
			"PARENT_ID" : "610900",
			"DISTRICT_NAME" : "石泉县",
			"SPELLING" : "Shiquan"
		},
		{
			"ID" : "610923",
			"PARENT_ID" : "610900",
			"DISTRICT_NAME" : "宁陕县",
			"SPELLING" : "Ningshan"
		},
		{
			"ID" : "610924",
			"PARENT_ID" : "610900",
			"DISTRICT_NAME" : "紫阳县",
			"SPELLING" : "Ziyang"
		},
		{
			"ID" : "610925",
			"PARENT_ID" : "610900",
			"DISTRICT_NAME" : "岚皋县",
			"SPELLING" : "Langao"
		},
		{
			"ID" : "610926",
			"PARENT_ID" : "610900",
			"DISTRICT_NAME" : "平利县",
			"SPELLING" : "Pingli"
		},
		{
			"ID" : "610927",
			"PARENT_ID" : "610900",
			"DISTRICT_NAME" : "镇坪县",
			"SPELLING" : "Zhenping"
		},
		{
			"ID" : "610928",
			"PARENT_ID" : "610900",
			"DISTRICT_NAME" : "旬阳县",
			"SPELLING" : "Xunyang"
		},
		{
			"ID" : "610929",
			"PARENT_ID" : "610900",
			"DISTRICT_NAME" : "白河县",
			"SPELLING" : "Baihe"
		},
		{
			"ID" : "611000",
			"PARENT_ID" : "610000",
			"DISTRICT_NAME" : "商洛市",
			"SPELLING" : "Shangluo"
		},
		{
			"ID" : "611002",
			"PARENT_ID" : "611000",
			"DISTRICT_NAME" : "商州区",
			"SPELLING" : "Shangzhou"
		},
		{
			"ID" : "611021",
			"PARENT_ID" : "611000",
			"DISTRICT_NAME" : "洛南县",
			"SPELLING" : "Luonan"
		},
		{
			"ID" : "611022",
			"PARENT_ID" : "611000",
			"DISTRICT_NAME" : "丹凤县",
			"SPELLING" : "Danfeng"
		},
		{
			"ID" : "611023",
			"PARENT_ID" : "611000",
			"DISTRICT_NAME" : "商南县",
			"SPELLING" : "Shangnan"
		},
		{
			"ID" : "611024",
			"PARENT_ID" : "611000",
			"DISTRICT_NAME" : "山阳县",
			"SPELLING" : "Shanyang"
		},
		{
			"ID" : "611025",
			"PARENT_ID" : "611000",
			"DISTRICT_NAME" : "镇安县",
			"SPELLING" : "Zhenan"
		},
		{
			"ID" : "611026",
			"PARENT_ID" : "611000",
			"DISTRICT_NAME" : "柞水县",
			"SPELLING" : "Zhashui"
		},
		{
			"ID" : "611100",
			"PARENT_ID" : "610000",
			"DISTRICT_NAME" : "西咸新区",
			"SPELLING" : "Xixian"
		},
		{
			"ID" : "611101",
			"PARENT_ID" : "611100",
			"DISTRICT_NAME" : "空港新城",
			"SPELLING" : "Konggang"
		},
		{
			"ID" : "611102",
			"PARENT_ID" : "611100",
			"DISTRICT_NAME" : "沣东新城",
			"SPELLING" : "Fengdong"
		},
		{
			"ID" : "611103",
			"PARENT_ID" : "611100",
			"DISTRICT_NAME" : "秦汉新城",
			"SPELLING" : "Qinhan"
		},
		{
			"ID" : "611104",
			"PARENT_ID" : "611100",
			"DISTRICT_NAME" : "沣西新城",
			"SPELLING" : "Fengxi"
		},
		{
			"ID" : "611105",
			"PARENT_ID" : "611100",
			"DISTRICT_NAME" : "泾河新城",
			"SPELLING" : "Jinghe"
		},
		{
			"ID" : "620000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "甘肃省",
			"SPELLING" : "Gansu"
		},
		{
			"ID" : "620100",
			"PARENT_ID" : "620000",
			"DISTRICT_NAME" : "兰州市",
			"SPELLING" : "Lanzhou"
		},
		{
			"ID" : "620102",
			"PARENT_ID" : "620100",
			"DISTRICT_NAME" : "城关区",
			"SPELLING" : "Chengguan"
		},
		{
			"ID" : "620103",
			"PARENT_ID" : "620100",
			"DISTRICT_NAME" : "七里河区",
			"SPELLING" : "Qilihe"
		},
		{
			"ID" : "620104",
			"PARENT_ID" : "620100",
			"DISTRICT_NAME" : "西固区",
			"SPELLING" : "Xigu"
		},
		{
			"ID" : "620105",
			"PARENT_ID" : "620100",
			"DISTRICT_NAME" : "安宁区",
			"SPELLING" : "Anning"
		},
		{
			"ID" : "620111",
			"PARENT_ID" : "620100",
			"DISTRICT_NAME" : "红古区",
			"SPELLING" : "Honggu"
		},
		{
			"ID" : "620121",
			"PARENT_ID" : "620100",
			"DISTRICT_NAME" : "永登县",
			"SPELLING" : "Yongdeng"
		},
		{
			"ID" : "620122",
			"PARENT_ID" : "620100",
			"DISTRICT_NAME" : "皋兰县",
			"SPELLING" : "Gaolan"
		},
		{
			"ID" : "620123",
			"PARENT_ID" : "620100",
			"DISTRICT_NAME" : "榆中县",
			"SPELLING" : "Yuzhong"
		},
		{
			"ID" : "620200",
			"PARENT_ID" : "620000",
			"DISTRICT_NAME" : "嘉峪关市",
			"SPELLING" : "Jiayuguan"
		},
		{
			"ID" : "620201",
			"PARENT_ID" : "620200",
			"DISTRICT_NAME" : "雄关区",
			"SPELLING" : "Xiongguan"
		},
		{
			"ID" : "620202",
			"PARENT_ID" : "620200",
			"DISTRICT_NAME" : "长城区",
			"SPELLING" : "Changcheng"
		},
		{
			"ID" : "620203",
			"PARENT_ID" : "620200",
			"DISTRICT_NAME" : "镜铁区",
			"SPELLING" : "Jingtie"
		},
		{
			"ID" : "620300",
			"PARENT_ID" : "620000",
			"DISTRICT_NAME" : "金昌市",
			"SPELLING" : "Jinchang"
		},
		{
			"ID" : "620302",
			"PARENT_ID" : "620300",
			"DISTRICT_NAME" : "金川区",
			"SPELLING" : "Jinchuan"
		},
		{
			"ID" : "620321",
			"PARENT_ID" : "620300",
			"DISTRICT_NAME" : "永昌县",
			"SPELLING" : "Yongchang"
		},
		{
			"ID" : "620400",
			"PARENT_ID" : "620000",
			"DISTRICT_NAME" : "白银市",
			"SPELLING" : "Baiyin"
		},
		{
			"ID" : "620402",
			"PARENT_ID" : "620400",
			"DISTRICT_NAME" : "白银区",
			"SPELLING" : "Baiyin"
		},
		{
			"ID" : "620403",
			"PARENT_ID" : "620400",
			"DISTRICT_NAME" : "平川区",
			"SPELLING" : "Pingchuan"
		},
		{
			"ID" : "620421",
			"PARENT_ID" : "620400",
			"DISTRICT_NAME" : "靖远县",
			"SPELLING" : "Jingyuan"
		},
		{
			"ID" : "620422",
			"PARENT_ID" : "620400",
			"DISTRICT_NAME" : "会宁县",
			"SPELLING" : "Huining"
		},
		{
			"ID" : "620423",
			"PARENT_ID" : "620400",
			"DISTRICT_NAME" : "景泰县",
			"SPELLING" : "Jingtai"
		},
		{
			"ID" : "620500",
			"PARENT_ID" : "620000",
			"DISTRICT_NAME" : "天水市",
			"SPELLING" : "Tianshui"
		},
		{
			"ID" : "620502",
			"PARENT_ID" : "620500",
			"DISTRICT_NAME" : "秦州区",
			"SPELLING" : "Qinzhou"
		},
		{
			"ID" : "620503",
			"PARENT_ID" : "620500",
			"DISTRICT_NAME" : "麦积区",
			"SPELLING" : "Maiji"
		},
		{
			"ID" : "620521",
			"PARENT_ID" : "620500",
			"DISTRICT_NAME" : "清水县",
			"SPELLING" : "Qingshui"
		},
		{
			"ID" : "620522",
			"PARENT_ID" : "620500",
			"DISTRICT_NAME" : "秦安县",
			"SPELLING" : "Qinan"
		},
		{
			"ID" : "620523",
			"PARENT_ID" : "620500",
			"DISTRICT_NAME" : "甘谷县",
			"SPELLING" : "Gangu"
		},
		{
			"ID" : "620524",
			"PARENT_ID" : "620500",
			"DISTRICT_NAME" : "武山县",
			"SPELLING" : "Wushan"
		},
		{
			"ID" : "620525",
			"PARENT_ID" : "620500",
			"DISTRICT_NAME" : "张家川回族自治县",
			"SPELLING" : "Zhangjiachuan"
		},
		{
			"ID" : "620600",
			"PARENT_ID" : "620000",
			"DISTRICT_NAME" : "武威市",
			"SPELLING" : "Wuwei"
		},
		{
			"ID" : "620602",
			"PARENT_ID" : "620600",
			"DISTRICT_NAME" : "凉州区",
			"SPELLING" : "Liangzhou"
		},
		{
			"ID" : "620621",
			"PARENT_ID" : "620600",
			"DISTRICT_NAME" : "民勤县",
			"SPELLING" : "Minqin"
		},
		{
			"ID" : "620622",
			"PARENT_ID" : "620600",
			"DISTRICT_NAME" : "古浪县",
			"SPELLING" : "Gulang"
		},
		{
			"ID" : "620623",
			"PARENT_ID" : "620600",
			"DISTRICT_NAME" : "天祝藏族自治县",
			"SPELLING" : "Tianzhu"
		},
		{
			"ID" : "620700",
			"PARENT_ID" : "620000",
			"DISTRICT_NAME" : "张掖市",
			"SPELLING" : "Zhangye"
		},
		{
			"ID" : "620702",
			"PARENT_ID" : "620700",
			"DISTRICT_NAME" : "甘州区",
			"SPELLING" : "Ganzhou"
		},
		{
			"ID" : "620721",
			"PARENT_ID" : "620700",
			"DISTRICT_NAME" : "肃南裕固族自治县",
			"SPELLING" : "Sunan"
		},
		{
			"ID" : "620722",
			"PARENT_ID" : "620700",
			"DISTRICT_NAME" : "民乐县",
			"SPELLING" : "Minle"
		},
		{
			"ID" : "620723",
			"PARENT_ID" : "620700",
			"DISTRICT_NAME" : "临泽县",
			"SPELLING" : "Linze"
		},
		{
			"ID" : "620724",
			"PARENT_ID" : "620700",
			"DISTRICT_NAME" : "高台县",
			"SPELLING" : "Gaotai"
		},
		{
			"ID" : "620725",
			"PARENT_ID" : "620700",
			"DISTRICT_NAME" : "山丹县",
			"SPELLING" : "Shandan"
		},
		{
			"ID" : "620800",
			"PARENT_ID" : "620000",
			"DISTRICT_NAME" : "平凉市",
			"SPELLING" : "Pingliang"
		},
		{
			"ID" : "620802",
			"PARENT_ID" : "620800",
			"DISTRICT_NAME" : "崆峒区",
			"SPELLING" : "Kongtong"
		},
		{
			"ID" : "620821",
			"PARENT_ID" : "620800",
			"DISTRICT_NAME" : "泾川县",
			"SPELLING" : "Jingchuan"
		},
		{
			"ID" : "620822",
			"PARENT_ID" : "620800",
			"DISTRICT_NAME" : "灵台县",
			"SPELLING" : "Lingtai"
		},
		{
			"ID" : "620823",
			"PARENT_ID" : "620800",
			"DISTRICT_NAME" : "崇信县",
			"SPELLING" : "Chongxin"
		},
		{
			"ID" : "620824",
			"PARENT_ID" : "620800",
			"DISTRICT_NAME" : "华亭县",
			"SPELLING" : "Huating"
		},
		{
			"ID" : "620825",
			"PARENT_ID" : "620800",
			"DISTRICT_NAME" : "庄浪县",
			"SPELLING" : "Zhuanglang"
		},
		{
			"ID" : "620826",
			"PARENT_ID" : "620800",
			"DISTRICT_NAME" : "静宁县",
			"SPELLING" : "Jingning"
		},
		{
			"ID" : "620900",
			"PARENT_ID" : "620000",
			"DISTRICT_NAME" : "酒泉市",
			"SPELLING" : "Jiuquan"
		},
		{
			"ID" : "620902",
			"PARENT_ID" : "620900",
			"DISTRICT_NAME" : "肃州区",
			"SPELLING" : "Suzhou"
		},
		{
			"ID" : "620921",
			"PARENT_ID" : "620900",
			"DISTRICT_NAME" : "金塔县",
			"SPELLING" : "Jinta"
		},
		{
			"ID" : "620922",
			"PARENT_ID" : "620900",
			"DISTRICT_NAME" : "瓜州县",
			"SPELLING" : "Guazhou"
		},
		{
			"ID" : "620923",
			"PARENT_ID" : "620900",
			"DISTRICT_NAME" : "肃北蒙古族自治县",
			"SPELLING" : "Subei"
		},
		{
			"ID" : "620924",
			"PARENT_ID" : "620900",
			"DISTRICT_NAME" : "阿克塞哈萨克族自治县",
			"SPELLING" : "Akesai"
		},
		{
			"ID" : "620981",
			"PARENT_ID" : "620900",
			"DISTRICT_NAME" : "玉门市",
			"SPELLING" : "Yumen"
		},
		{
			"ID" : "620982",
			"PARENT_ID" : "620900",
			"DISTRICT_NAME" : "敦煌市",
			"SPELLING" : "Dunhuang"
		},
		{
			"ID" : "621000",
			"PARENT_ID" : "620000",
			"DISTRICT_NAME" : "庆阳市",
			"SPELLING" : "Qingyang"
		},
		{
			"ID" : "621002",
			"PARENT_ID" : "621000",
			"DISTRICT_NAME" : "西峰区",
			"SPELLING" : "Xifeng"
		},
		{
			"ID" : "621021",
			"PARENT_ID" : "621000",
			"DISTRICT_NAME" : "庆城县",
			"SPELLING" : "Qingcheng"
		},
		{
			"ID" : "621022",
			"PARENT_ID" : "621000",
			"DISTRICT_NAME" : "环县",
			"SPELLING" : "Huanxian"
		},
		{
			"ID" : "621023",
			"PARENT_ID" : "621000",
			"DISTRICT_NAME" : "华池县",
			"SPELLING" : "Huachi"
		},
		{
			"ID" : "621024",
			"PARENT_ID" : "621000",
			"DISTRICT_NAME" : "合水县",
			"SPELLING" : "Heshui"
		},
		{
			"ID" : "621025",
			"PARENT_ID" : "621000",
			"DISTRICT_NAME" : "正宁县",
			"SPELLING" : "Zhengning"
		},
		{
			"ID" : "621026",
			"PARENT_ID" : "621000",
			"DISTRICT_NAME" : "宁县",
			"SPELLING" : "Ningxian"
		},
		{
			"ID" : "621027",
			"PARENT_ID" : "621000",
			"DISTRICT_NAME" : "镇原县",
			"SPELLING" : "Zhenyuan"
		},
		{
			"ID" : "621100",
			"PARENT_ID" : "620000",
			"DISTRICT_NAME" : "定西市",
			"SPELLING" : "Dingxi"
		},
		{
			"ID" : "621102",
			"PARENT_ID" : "621100",
			"DISTRICT_NAME" : "安定区",
			"SPELLING" : "Anding"
		},
		{
			"ID" : "621121",
			"PARENT_ID" : "621100",
			"DISTRICT_NAME" : "通渭县",
			"SPELLING" : "Tongwei"
		},
		{
			"ID" : "621122",
			"PARENT_ID" : "621100",
			"DISTRICT_NAME" : "陇西县",
			"SPELLING" : "Longxi"
		},
		{
			"ID" : "621123",
			"PARENT_ID" : "621100",
			"DISTRICT_NAME" : "渭源县",
			"SPELLING" : "Weiyuan"
		},
		{
			"ID" : "621124",
			"PARENT_ID" : "621100",
			"DISTRICT_NAME" : "临洮县",
			"SPELLING" : "Lintao"
		},
		{
			"ID" : "621125",
			"PARENT_ID" : "621100",
			"DISTRICT_NAME" : "漳县",
			"SPELLING" : "Zhangxian"
		},
		{
			"ID" : "621126",
			"PARENT_ID" : "621100",
			"DISTRICT_NAME" : "岷县",
			"SPELLING" : "Minxian"
		},
		{
			"ID" : "621200",
			"PARENT_ID" : "620000",
			"DISTRICT_NAME" : "陇南市",
			"SPELLING" : "Longnan"
		},
		{
			"ID" : "621202",
			"PARENT_ID" : "621200",
			"DISTRICT_NAME" : "武都区",
			"SPELLING" : "Wudu"
		},
		{
			"ID" : "621221",
			"PARENT_ID" : "621200",
			"DISTRICT_NAME" : "成县",
			"SPELLING" : "Chengxian"
		},
		{
			"ID" : "621222",
			"PARENT_ID" : "621200",
			"DISTRICT_NAME" : "文县",
			"SPELLING" : "Wenxian"
		},
		{
			"ID" : "621223",
			"PARENT_ID" : "621200",
			"DISTRICT_NAME" : "宕昌县",
			"SPELLING" : "Dangchang"
		},
		{
			"ID" : "621224",
			"PARENT_ID" : "621200",
			"DISTRICT_NAME" : "康县",
			"SPELLING" : "Kangxian"
		},
		{
			"ID" : "621225",
			"PARENT_ID" : "621200",
			"DISTRICT_NAME" : "西和县",
			"SPELLING" : "Xihe"
		},
		{
			"ID" : "621226",
			"PARENT_ID" : "621200",
			"DISTRICT_NAME" : "礼县",
			"SPELLING" : "Lixian"
		},
		{
			"ID" : "621227",
			"PARENT_ID" : "621200",
			"DISTRICT_NAME" : "徽县",
			"SPELLING" : "Huixian"
		},
		{
			"ID" : "621228",
			"PARENT_ID" : "621200",
			"DISTRICT_NAME" : "两当县",
			"SPELLING" : "Liangdang"
		},
		{
			"ID" : "622900",
			"PARENT_ID" : "620000",
			"DISTRICT_NAME" : "临夏回族自治州",
			"SPELLING" : "Linxia"
		},
		{
			"ID" : "622901",
			"PARENT_ID" : "622900",
			"DISTRICT_NAME" : "临夏市",
			"SPELLING" : "Linxia"
		},
		{
			"ID" : "622921",
			"PARENT_ID" : "622900",
			"DISTRICT_NAME" : "临夏县",
			"SPELLING" : "Linxia"
		},
		{
			"ID" : "622922",
			"PARENT_ID" : "622900",
			"DISTRICT_NAME" : "康乐县",
			"SPELLING" : "Kangle"
		},
		{
			"ID" : "622923",
			"PARENT_ID" : "622900",
			"DISTRICT_NAME" : "永靖县",
			"SPELLING" : "Yongjing"
		},
		{
			"ID" : "622924",
			"PARENT_ID" : "622900",
			"DISTRICT_NAME" : "广河县",
			"SPELLING" : "Guanghe"
		},
		{
			"ID" : "622925",
			"PARENT_ID" : "622900",
			"DISTRICT_NAME" : "和政县",
			"SPELLING" : "Hezheng"
		},
		{
			"ID" : "622926",
			"PARENT_ID" : "622900",
			"DISTRICT_NAME" : "东乡族自治县",
			"SPELLING" : "Dongxiangzu"
		},
		{
			"ID" : "622927",
			"PARENT_ID" : "622900",
			"DISTRICT_NAME" : "积石山保安族东乡族撒拉族自治县",
			"SPELLING" : "Jishishan"
		},
		{
			"ID" : "623000",
			"PARENT_ID" : "620000",
			"DISTRICT_NAME" : "甘南藏族自治州",
			"SPELLING" : "Gannan"
		},
		{
			"ID" : "623001",
			"PARENT_ID" : "623000",
			"DISTRICT_NAME" : "合作市",
			"SPELLING" : "Hezuo"
		},
		{
			"ID" : "623021",
			"PARENT_ID" : "623000",
			"DISTRICT_NAME" : "临潭县",
			"SPELLING" : "Lintan"
		},
		{
			"ID" : "623022",
			"PARENT_ID" : "623000",
			"DISTRICT_NAME" : "卓尼县",
			"SPELLING" : "Zhuoni"
		},
		{
			"ID" : "623023",
			"PARENT_ID" : "623000",
			"DISTRICT_NAME" : "舟曲县",
			"SPELLING" : "Zhouqu"
		},
		{
			"ID" : "623024",
			"PARENT_ID" : "623000",
			"DISTRICT_NAME" : "迭部县",
			"SPELLING" : "Diebu"
		},
		{
			"ID" : "623025",
			"PARENT_ID" : "623000",
			"DISTRICT_NAME" : "玛曲县",
			"SPELLING" : "Maqu"
		},
		{
			"ID" : "623026",
			"PARENT_ID" : "623000",
			"DISTRICT_NAME" : "碌曲县",
			"SPELLING" : "Luqu"
		},
		{
			"ID" : "623027",
			"PARENT_ID" : "623000",
			"DISTRICT_NAME" : "夏河县",
			"SPELLING" : "Xiahe"
		},
		{
			"ID" : "630000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "青海省",
			"SPELLING" : "Qinghai"
		},
		{
			"ID" : "630100",
			"PARENT_ID" : "630000",
			"DISTRICT_NAME" : "西宁市",
			"SPELLING" : "Xining"
		},
		{
			"ID" : "630102",
			"PARENT_ID" : "630100",
			"DISTRICT_NAME" : "城东区",
			"SPELLING" : "Chengdong"
		},
		{
			"ID" : "630103",
			"PARENT_ID" : "630100",
			"DISTRICT_NAME" : "城中区",
			"SPELLING" : "Chengzhong"
		},
		{
			"ID" : "630104",
			"PARENT_ID" : "630100",
			"DISTRICT_NAME" : "城西区",
			"SPELLING" : "Chengxi"
		},
		{
			"ID" : "630105",
			"PARENT_ID" : "630100",
			"DISTRICT_NAME" : "城北区",
			"SPELLING" : "Chengbei"
		},
		{
			"ID" : "630121",
			"PARENT_ID" : "630100",
			"DISTRICT_NAME" : "大通回族土族自治县",
			"SPELLING" : "Datong"
		},
		{
			"ID" : "630122",
			"PARENT_ID" : "630100",
			"DISTRICT_NAME" : "湟中县",
			"SPELLING" : "Huangzhong"
		},
		{
			"ID" : "630123",
			"PARENT_ID" : "630100",
			"DISTRICT_NAME" : "湟源县",
			"SPELLING" : "Huangyuan"
		},
		{
			"ID" : "630200",
			"PARENT_ID" : "630000",
			"DISTRICT_NAME" : "海东市",
			"SPELLING" : "Haidong"
		},
		{
			"ID" : "630202",
			"PARENT_ID" : "630200",
			"DISTRICT_NAME" : "乐都区",
			"SPELLING" : "Ledu"
		},
		{
			"ID" : "630221",
			"PARENT_ID" : "630200",
			"DISTRICT_NAME" : "平安县",
			"SPELLING" : "Pingan"
		},
		{
			"ID" : "630222",
			"PARENT_ID" : "630200",
			"DISTRICT_NAME" : "民和回族土族自治县",
			"SPELLING" : "Minhe"
		},
		{
			"ID" : "630223",
			"PARENT_ID" : "630200",
			"DISTRICT_NAME" : "互助土族自治县",
			"SPELLING" : "Huzhu"
		},
		{
			"ID" : "630224",
			"PARENT_ID" : "630200",
			"DISTRICT_NAME" : "化隆回族自治县",
			"SPELLING" : "Hualong"
		},
		{
			"ID" : "630225",
			"PARENT_ID" : "630200",
			"DISTRICT_NAME" : "循化撒拉族自治县",
			"SPELLING" : "Xunhua"
		},
		{
			"ID" : "632200",
			"PARENT_ID" : "630000",
			"DISTRICT_NAME" : "海北藏族自治州",
			"SPELLING" : "Haibei"
		},
		{
			"ID" : "632221",
			"PARENT_ID" : "632200",
			"DISTRICT_NAME" : "门源回族自治县",
			"SPELLING" : "Menyuan"
		},
		{
			"ID" : "632222",
			"PARENT_ID" : "632200",
			"DISTRICT_NAME" : "祁连县",
			"SPELLING" : "Qilian"
		},
		{
			"ID" : "632223",
			"PARENT_ID" : "632200",
			"DISTRICT_NAME" : "海晏县",
			"SPELLING" : "Haiyan"
		},
		{
			"ID" : "632224",
			"PARENT_ID" : "632200",
			"DISTRICT_NAME" : "刚察县",
			"SPELLING" : "Gangcha"
		},
		{
			"ID" : "632300",
			"PARENT_ID" : "630000",
			"DISTRICT_NAME" : "黄南藏族自治州",
			"SPELLING" : "Huangnan"
		},
		{
			"ID" : "632321",
			"PARENT_ID" : "632300",
			"DISTRICT_NAME" : "同仁县",
			"SPELLING" : "Tongren"
		},
		{
			"ID" : "632322",
			"PARENT_ID" : "632300",
			"DISTRICT_NAME" : "尖扎县",
			"SPELLING" : "Jianzha"
		},
		{
			"ID" : "632323",
			"PARENT_ID" : "632300",
			"DISTRICT_NAME" : "泽库县",
			"SPELLING" : "Zeku"
		},
		{
			"ID" : "632324",
			"PARENT_ID" : "632300",
			"DISTRICT_NAME" : "河南蒙古族自治县",
			"SPELLING" : "Henan"
		},
		{
			"ID" : "632500",
			"PARENT_ID" : "630000",
			"DISTRICT_NAME" : "海南藏族自治州",
			"SPELLING" : "Hainan"
		},
		{
			"ID" : "632521",
			"PARENT_ID" : "632500",
			"DISTRICT_NAME" : "共和县",
			"SPELLING" : "Gonghe"
		},
		{
			"ID" : "632522",
			"PARENT_ID" : "632500",
			"DISTRICT_NAME" : "同德县",
			"SPELLING" : "Tongde"
		},
		{
			"ID" : "632523",
			"PARENT_ID" : "632500",
			"DISTRICT_NAME" : "贵德县",
			"SPELLING" : "Guide"
		},
		{
			"ID" : "632524",
			"PARENT_ID" : "632500",
			"DISTRICT_NAME" : "兴海县",
			"SPELLING" : "Xinghai"
		},
		{
			"ID" : "632525",
			"PARENT_ID" : "632500",
			"DISTRICT_NAME" : "贵南县",
			"SPELLING" : "Guinan"
		},
		{
			"ID" : "632600",
			"PARENT_ID" : "630000",
			"DISTRICT_NAME" : "果洛藏族自治州",
			"SPELLING" : "Golog"
		},
		{
			"ID" : "632621",
			"PARENT_ID" : "632600",
			"DISTRICT_NAME" : "玛沁县",
			"SPELLING" : "Maqin"
		},
		{
			"ID" : "632622",
			"PARENT_ID" : "632600",
			"DISTRICT_NAME" : "班玛县",
			"SPELLING" : "Banma"
		},
		{
			"ID" : "632623",
			"PARENT_ID" : "632600",
			"DISTRICT_NAME" : "甘德县",
			"SPELLING" : "Gande"
		},
		{
			"ID" : "632624",
			"PARENT_ID" : "632600",
			"DISTRICT_NAME" : "达日县",
			"SPELLING" : "Dari"
		},
		{
			"ID" : "632625",
			"PARENT_ID" : "632600",
			"DISTRICT_NAME" : "久治县",
			"SPELLING" : "Jiuzhi"
		},
		{
			"ID" : "632626",
			"PARENT_ID" : "632600",
			"DISTRICT_NAME" : "玛多县",
			"SPELLING" : "Maduo"
		},
		{
			"ID" : "632700",
			"PARENT_ID" : "630000",
			"DISTRICT_NAME" : "玉树藏族自治州",
			"SPELLING" : "Yushu"
		},
		{
			"ID" : "632701",
			"PARENT_ID" : "632700",
			"DISTRICT_NAME" : "玉树市",
			"SPELLING" : "Yushu"
		},
		{
			"ID" : "632722",
			"PARENT_ID" : "632700",
			"DISTRICT_NAME" : "杂多县",
			"SPELLING" : "Zaduo"
		},
		{
			"ID" : "632723",
			"PARENT_ID" : "632700",
			"DISTRICT_NAME" : "称多县",
			"SPELLING" : "Chenduo"
		},
		{
			"ID" : "632724",
			"PARENT_ID" : "632700",
			"DISTRICT_NAME" : "治多县",
			"SPELLING" : "Zhiduo"
		},
		{
			"ID" : "632725",
			"PARENT_ID" : "632700",
			"DISTRICT_NAME" : "囊谦县",
			"SPELLING" : "Nangqian"
		},
		{
			"ID" : "632726",
			"PARENT_ID" : "632700",
			"DISTRICT_NAME" : "曲麻莱县",
			"SPELLING" : "Qumalai"
		},
		{
			"ID" : "632800",
			"PARENT_ID" : "630000",
			"DISTRICT_NAME" : "海西蒙古族藏族自治州",
			"SPELLING" : "Haixi"
		},
		{
			"ID" : "632801",
			"PARENT_ID" : "632800",
			"DISTRICT_NAME" : "格尔木市",
			"SPELLING" : "Geermu"
		},
		{
			"ID" : "632802",
			"PARENT_ID" : "632800",
			"DISTRICT_NAME" : "德令哈市",
			"SPELLING" : "Delingha"
		},
		{
			"ID" : "632821",
			"PARENT_ID" : "632800",
			"DISTRICT_NAME" : "乌兰县",
			"SPELLING" : "Wulan"
		},
		{
			"ID" : "632822",
			"PARENT_ID" : "632800",
			"DISTRICT_NAME" : "都兰县",
			"SPELLING" : "Dulan"
		},
		{
			"ID" : "632823",
			"PARENT_ID" : "632800",
			"DISTRICT_NAME" : "天峻县",
			"SPELLING" : "Tianjun"
		},
		{
			"ID" : "640000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "宁夏回族自治区",
			"SPELLING" : "Ningxia"
		},
		{
			"ID" : "640100",
			"PARENT_ID" : "640000",
			"DISTRICT_NAME" : "银川市",
			"SPELLING" : "Yinchuan"
		},
		{
			"ID" : "640104",
			"PARENT_ID" : "640100",
			"DISTRICT_NAME" : "兴庆区",
			"SPELLING" : "Xingqing"
		},
		{
			"ID" : "640105",
			"PARENT_ID" : "640100",
			"DISTRICT_NAME" : "西夏区",
			"SPELLING" : "Xixia"
		},
		{
			"ID" : "640106",
			"PARENT_ID" : "640100",
			"DISTRICT_NAME" : "金凤区",
			"SPELLING" : "Jinfeng"
		},
		{
			"ID" : "640121",
			"PARENT_ID" : "640100",
			"DISTRICT_NAME" : "永宁县",
			"SPELLING" : "Yongning"
		},
		{
			"ID" : "640122",
			"PARENT_ID" : "640100",
			"DISTRICT_NAME" : "贺兰县",
			"SPELLING" : "Helan"
		},
		{
			"ID" : "640181",
			"PARENT_ID" : "640100",
			"DISTRICT_NAME" : "灵武市",
			"SPELLING" : "Lingwu"
		},
		{
			"ID" : "640200",
			"PARENT_ID" : "640000",
			"DISTRICT_NAME" : "石嘴山市",
			"SPELLING" : "Shizuishan"
		},
		{
			"ID" : "640202",
			"PARENT_ID" : "640200",
			"DISTRICT_NAME" : "大武口区",
			"SPELLING" : "Dawukou"
		},
		{
			"ID" : "640205",
			"PARENT_ID" : "640200",
			"DISTRICT_NAME" : "惠农区",
			"SPELLING" : "Huinong"
		},
		{
			"ID" : "640221",
			"PARENT_ID" : "640200",
			"DISTRICT_NAME" : "平罗县",
			"SPELLING" : "Pingluo"
		},
		{
			"ID" : "640300",
			"PARENT_ID" : "640000",
			"DISTRICT_NAME" : "吴忠市",
			"SPELLING" : "Wuzhong"
		},
		{
			"ID" : "640302",
			"PARENT_ID" : "640300",
			"DISTRICT_NAME" : "利通区",
			"SPELLING" : "Litong"
		},
		{
			"ID" : "640303",
			"PARENT_ID" : "640300",
			"DISTRICT_NAME" : "红寺堡区",
			"SPELLING" : "Hongsibao"
		},
		{
			"ID" : "640323",
			"PARENT_ID" : "640300",
			"DISTRICT_NAME" : "盐池县",
			"SPELLING" : "Yanchi"
		},
		{
			"ID" : "640324",
			"PARENT_ID" : "640300",
			"DISTRICT_NAME" : "同心县",
			"SPELLING" : "Tongxin"
		},
		{
			"ID" : "640381",
			"PARENT_ID" : "640300",
			"DISTRICT_NAME" : "青铜峡市",
			"SPELLING" : "Qingtongxia"
		},
		{
			"ID" : "640400",
			"PARENT_ID" : "640000",
			"DISTRICT_NAME" : "固原市",
			"SPELLING" : "Guyuan"
		},
		{
			"ID" : "640402",
			"PARENT_ID" : "640400",
			"DISTRICT_NAME" : "原州区",
			"SPELLING" : "Yuanzhou"
		},
		{
			"ID" : "640422",
			"PARENT_ID" : "640400",
			"DISTRICT_NAME" : "西吉县",
			"SPELLING" : "Xiji"
		},
		{
			"ID" : "640423",
			"PARENT_ID" : "640400",
			"DISTRICT_NAME" : "隆德县",
			"SPELLING" : "Longde"
		},
		{
			"ID" : "640424",
			"PARENT_ID" : "640400",
			"DISTRICT_NAME" : "泾源县",
			"SPELLING" : "Jingyuan"
		},
		{
			"ID" : "640425",
			"PARENT_ID" : "640400",
			"DISTRICT_NAME" : "彭阳县",
			"SPELLING" : "Pengyang"
		},
		{
			"ID" : "640500",
			"PARENT_ID" : "640000",
			"DISTRICT_NAME" : "中卫市",
			"SPELLING" : "Zhongwei"
		},
		{
			"ID" : "640502",
			"PARENT_ID" : "640500",
			"DISTRICT_NAME" : "沙坡头区",
			"SPELLING" : "Shapotou"
		},
		{
			"ID" : "640521",
			"PARENT_ID" : "640500",
			"DISTRICT_NAME" : "中宁县",
			"SPELLING" : "Zhongning"
		},
		{
			"ID" : "640522",
			"PARENT_ID" : "640500",
			"DISTRICT_NAME" : "海原县",
			"SPELLING" : "Haiyuan"
		},
		{
			"ID" : "650000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "新疆维吾尔自治区",
			"SPELLING" : "Xinjiang"
		},
		{
			"ID" : "650100",
			"PARENT_ID" : "650000",
			"DISTRICT_NAME" : "乌鲁木齐市",
			"SPELLING" : "Urumqi"
		},
		{
			"ID" : "650102",
			"PARENT_ID" : "650100",
			"DISTRICT_NAME" : "天山区",
			"SPELLING" : "Tianshan"
		},
		{
			"ID" : "650103",
			"PARENT_ID" : "650100",
			"DISTRICT_NAME" : "沙依巴克区",
			"SPELLING" : "Shayibake"
		},
		{
			"ID" : "650104",
			"PARENT_ID" : "650100",
			"DISTRICT_NAME" : "新市区",
			"SPELLING" : "Xinshi"
		},
		{
			"ID" : "650105",
			"PARENT_ID" : "650100",
			"DISTRICT_NAME" : "水磨沟区",
			"SPELLING" : "Shuimogou"
		},
		{
			"ID" : "650106",
			"PARENT_ID" : "650100",
			"DISTRICT_NAME" : "头屯河区",
			"SPELLING" : "Toutunhe"
		},
		{
			"ID" : "650107",
			"PARENT_ID" : "650100",
			"DISTRICT_NAME" : "达坂城区",
			"SPELLING" : "Dabancheng"
		},
		{
			"ID" : "650109",
			"PARENT_ID" : "650100",
			"DISTRICT_NAME" : "米东区",
			"SPELLING" : "Midong"
		},
		{
			"ID" : "650121",
			"PARENT_ID" : "650100",
			"DISTRICT_NAME" : "乌鲁木齐县",
			"SPELLING" : "Wulumuqi"
		},
		{
			"ID" : "650200",
			"PARENT_ID" : "650000",
			"DISTRICT_NAME" : "克拉玛依市",
			"SPELLING" : "Karamay"
		},
		{
			"ID" : "650202",
			"PARENT_ID" : "650200",
			"DISTRICT_NAME" : "独山子区",
			"SPELLING" : "Dushanzi"
		},
		{
			"ID" : "650203",
			"PARENT_ID" : "650200",
			"DISTRICT_NAME" : "克拉玛依区",
			"SPELLING" : "Kelamayi"
		},
		{
			"ID" : "650204",
			"PARENT_ID" : "650200",
			"DISTRICT_NAME" : "白碱滩区",
			"SPELLING" : "Baijiantan"
		},
		{
			"ID" : "650205",
			"PARENT_ID" : "650200",
			"DISTRICT_NAME" : "乌尔禾区",
			"SPELLING" : "Wuerhe"
		},
		{
			"ID" : "652100",
			"PARENT_ID" : "650000",
			"DISTRICT_NAME" : "吐鲁番地区",
			"SPELLING" : "Turpan"
		},
		{
			"ID" : "652101",
			"PARENT_ID" : "652100",
			"DISTRICT_NAME" : "吐鲁番市",
			"SPELLING" : "Tulufan"
		},
		{
			"ID" : "652122",
			"PARENT_ID" : "652100",
			"DISTRICT_NAME" : "鄯善县",
			"SPELLING" : "Shanshan"
		},
		{
			"ID" : "652123",
			"PARENT_ID" : "652100",
			"DISTRICT_NAME" : "托克逊县",
			"SPELLING" : "Tuokexun"
		},
		{
			"ID" : "652200",
			"PARENT_ID" : "650000",
			"DISTRICT_NAME" : "哈密地区",
			"SPELLING" : "Hami"
		},
		{
			"ID" : "652201",
			"PARENT_ID" : "652200",
			"DISTRICT_NAME" : "哈密市",
			"SPELLING" : "Hami"
		},
		{
			"ID" : "652222",
			"PARENT_ID" : "652200",
			"DISTRICT_NAME" : "巴里坤哈萨克自治县",
			"SPELLING" : "Balikun"
		},
		{
			"ID" : "652223",
			"PARENT_ID" : "652200",
			"DISTRICT_NAME" : "伊吾县",
			"SPELLING" : "Yiwu"
		},
		{
			"ID" : "652300",
			"PARENT_ID" : "650000",
			"DISTRICT_NAME" : "昌吉回族自治州",
			"SPELLING" : "Changji"
		},
		{
			"ID" : "652301",
			"PARENT_ID" : "652300",
			"DISTRICT_NAME" : "昌吉市",
			"SPELLING" : "Changji"
		},
		{
			"ID" : "652302",
			"PARENT_ID" : "652300",
			"DISTRICT_NAME" : "阜康市",
			"SPELLING" : "Fukang"
		},
		{
			"ID" : "652323",
			"PARENT_ID" : "652300",
			"DISTRICT_NAME" : "呼图壁县",
			"SPELLING" : "Hutubi"
		},
		{
			"ID" : "652324",
			"PARENT_ID" : "652300",
			"DISTRICT_NAME" : "玛纳斯县",
			"SPELLING" : "Manasi"
		},
		{
			"ID" : "652325",
			"PARENT_ID" : "652300",
			"DISTRICT_NAME" : "奇台县",
			"SPELLING" : "Qitai"
		},
		{
			"ID" : "652327",
			"PARENT_ID" : "652300",
			"DISTRICT_NAME" : "吉木萨尔县",
			"SPELLING" : "Jimusaer"
		},
		{
			"ID" : "652328",
			"PARENT_ID" : "652300",
			"DISTRICT_NAME" : "木垒哈萨克自治县",
			"SPELLING" : "Mulei"
		},
		{
			"ID" : "652700",
			"PARENT_ID" : "650000",
			"DISTRICT_NAME" : "博尔塔拉蒙古自治州",
			"SPELLING" : "Bortala"
		},
		{
			"ID" : "652701",
			"PARENT_ID" : "652700",
			"DISTRICT_NAME" : "博乐市",
			"SPELLING" : "Bole"
		},
		{
			"ID" : "652702",
			"PARENT_ID" : "652700",
			"DISTRICT_NAME" : "阿拉山口市",
			"SPELLING" : "Alashankou"
		},
		{
			"ID" : "652722",
			"PARENT_ID" : "652700",
			"DISTRICT_NAME" : "精河县",
			"SPELLING" : "Jinghe"
		},
		{
			"ID" : "652723",
			"PARENT_ID" : "652700",
			"DISTRICT_NAME" : "温泉县",
			"SPELLING" : "Wenquan"
		},
		{
			"ID" : "652800",
			"PARENT_ID" : "650000",
			"DISTRICT_NAME" : "巴音郭楞蒙古自治州",
			"SPELLING" : "Bayingol"
		},
		{
			"ID" : "652801",
			"PARENT_ID" : "652800",
			"DISTRICT_NAME" : "库尔勒市",
			"SPELLING" : "Kuerle"
		},
		{
			"ID" : "652822",
			"PARENT_ID" : "652800",
			"DISTRICT_NAME" : "轮台县",
			"SPELLING" : "Luntai"
		},
		{
			"ID" : "652823",
			"PARENT_ID" : "652800",
			"DISTRICT_NAME" : "尉犁县",
			"SPELLING" : "Yuli"
		},
		{
			"ID" : "652824",
			"PARENT_ID" : "652800",
			"DISTRICT_NAME" : "若羌县",
			"SPELLING" : "Ruoqiang"
		},
		{
			"ID" : "652825",
			"PARENT_ID" : "652800",
			"DISTRICT_NAME" : "且末县",
			"SPELLING" : "Qiemo"
		},
		{
			"ID" : "652826",
			"PARENT_ID" : "652800",
			"DISTRICT_NAME" : "焉耆回族自治县",
			"SPELLING" : "Yanqi"
		},
		{
			"ID" : "652827",
			"PARENT_ID" : "652800",
			"DISTRICT_NAME" : "和静县",
			"SPELLING" : "Hejing"
		},
		{
			"ID" : "652828",
			"PARENT_ID" : "652800",
			"DISTRICT_NAME" : "和硕县",
			"SPELLING" : "Heshuo"
		},
		{
			"ID" : "652829",
			"PARENT_ID" : "652800",
			"DISTRICT_NAME" : "博湖县",
			"SPELLING" : "Bohu"
		},
		{
			"ID" : "652900",
			"PARENT_ID" : "650000",
			"DISTRICT_NAME" : "阿克苏地区",
			"SPELLING" : "Aksu"
		},
		{
			"ID" : "652901",
			"PARENT_ID" : "652900",
			"DISTRICT_NAME" : "阿克苏市",
			"SPELLING" : "Akesu"
		},
		{
			"ID" : "652922",
			"PARENT_ID" : "652900",
			"DISTRICT_NAME" : "温宿县",
			"SPELLING" : "Wensu"
		},
		{
			"ID" : "652923",
			"PARENT_ID" : "652900",
			"DISTRICT_NAME" : "库车县",
			"SPELLING" : "Kuche"
		},
		{
			"ID" : "652924",
			"PARENT_ID" : "652900",
			"DISTRICT_NAME" : "沙雅县",
			"SPELLING" : "Shaya"
		},
		{
			"ID" : "652925",
			"PARENT_ID" : "652900",
			"DISTRICT_NAME" : "新和县",
			"SPELLING" : "Xinhe"
		},
		{
			"ID" : "652926",
			"PARENT_ID" : "652900",
			"DISTRICT_NAME" : "拜城县",
			"SPELLING" : "Baicheng"
		},
		{
			"ID" : "652927",
			"PARENT_ID" : "652900",
			"DISTRICT_NAME" : "乌什县",
			"SPELLING" : "Wushi"
		},
		{
			"ID" : "652928",
			"PARENT_ID" : "652900",
			"DISTRICT_NAME" : "阿瓦提县",
			"SPELLING" : "Awati"
		},
		{
			"ID" : "652929",
			"PARENT_ID" : "652900",
			"DISTRICT_NAME" : "柯坪县",
			"SPELLING" : "Keping"
		},
		{
			"ID" : "653000",
			"PARENT_ID" : "650000",
			"DISTRICT_NAME" : "克孜勒苏柯尔克孜自治州",
			"SPELLING" : "Kizilsu"
		},
		{
			"ID" : "653001",
			"PARENT_ID" : "653000",
			"DISTRICT_NAME" : "阿图什市",
			"SPELLING" : "Atushi"
		},
		{
			"ID" : "653022",
			"PARENT_ID" : "653000",
			"DISTRICT_NAME" : "阿克陶县",
			"SPELLING" : "Aketao"
		},
		{
			"ID" : "653023",
			"PARENT_ID" : "653000",
			"DISTRICT_NAME" : "阿合奇县",
			"SPELLING" : "Aheqi"
		},
		{
			"ID" : "653024",
			"PARENT_ID" : "653000",
			"DISTRICT_NAME" : "乌恰县",
			"SPELLING" : "Wuqia"
		},
		{
			"ID" : "653100",
			"PARENT_ID" : "650000",
			"DISTRICT_NAME" : "喀什地区",
			"SPELLING" : "Kashgar"
		},
		{
			"ID" : "653101",
			"PARENT_ID" : "653100",
			"DISTRICT_NAME" : "喀什市",
			"SPELLING" : "Kashi"
		},
		{
			"ID" : "653121",
			"PARENT_ID" : "653100",
			"DISTRICT_NAME" : "疏附县",
			"SPELLING" : "Shufu"
		},
		{
			"ID" : "653122",
			"PARENT_ID" : "653100",
			"DISTRICT_NAME" : "疏勒县",
			"SPELLING" : "Shule"
		},
		{
			"ID" : "653123",
			"PARENT_ID" : "653100",
			"DISTRICT_NAME" : "英吉沙县",
			"SPELLING" : "Yingjisha"
		},
		{
			"ID" : "653124",
			"PARENT_ID" : "653100",
			"DISTRICT_NAME" : "泽普县",
			"SPELLING" : "Zepu"
		},
		{
			"ID" : "653125",
			"PARENT_ID" : "653100",
			"DISTRICT_NAME" : "莎车县",
			"SPELLING" : "Shache"
		},
		{
			"ID" : "653126",
			"PARENT_ID" : "653100",
			"DISTRICT_NAME" : "叶城县",
			"SPELLING" : "Yecheng"
		},
		{
			"ID" : "653127",
			"PARENT_ID" : "653100",
			"DISTRICT_NAME" : "麦盖提县",
			"SPELLING" : "Maigaiti"
		},
		{
			"ID" : "653128",
			"PARENT_ID" : "653100",
			"DISTRICT_NAME" : "岳普湖县",
			"SPELLING" : "Yuepuhu"
		},
		{
			"ID" : "653129",
			"PARENT_ID" : "653100",
			"DISTRICT_NAME" : "伽师县",
			"SPELLING" : "Jiashi"
		},
		{
			"ID" : "653130",
			"PARENT_ID" : "653100",
			"DISTRICT_NAME" : "巴楚县",
			"SPELLING" : "Bachu"
		},
		{
			"ID" : "653131",
			"PARENT_ID" : "653100",
			"DISTRICT_NAME" : "塔什库尔干塔吉克自治县",
			"SPELLING" : "Tashikuergantajike"
		},
		{
			"ID" : "653200",
			"PARENT_ID" : "650000",
			"DISTRICT_NAME" : "和田地区",
			"SPELLING" : "Hotan"
		},
		{
			"ID" : "653201",
			"PARENT_ID" : "653200",
			"DISTRICT_NAME" : "和田市",
			"SPELLING" : "Hetianshi"
		},
		{
			"ID" : "653221",
			"PARENT_ID" : "653200",
			"DISTRICT_NAME" : "和田县",
			"SPELLING" : "Hetianxian"
		},
		{
			"ID" : "653222",
			"PARENT_ID" : "653200",
			"DISTRICT_NAME" : "墨玉县",
			"SPELLING" : "Moyu"
		},
		{
			"ID" : "653223",
			"PARENT_ID" : "653200",
			"DISTRICT_NAME" : "皮山县",
			"SPELLING" : "Pishan"
		},
		{
			"ID" : "653224",
			"PARENT_ID" : "653200",
			"DISTRICT_NAME" : "洛浦县",
			"SPELLING" : "Luopu"
		},
		{
			"ID" : "653225",
			"PARENT_ID" : "653200",
			"DISTRICT_NAME" : "策勒县",
			"SPELLING" : "Cele"
		},
		{
			"ID" : "653226",
			"PARENT_ID" : "653200",
			"DISTRICT_NAME" : "于田县",
			"SPELLING" : "Yutian"
		},
		{
			"ID" : "653227",
			"PARENT_ID" : "653200",
			"DISTRICT_NAME" : "民丰县",
			"SPELLING" : "Minfeng"
		},
		{
			"ID" : "654000",
			"PARENT_ID" : "650000",
			"DISTRICT_NAME" : "伊犁哈萨克自治州",
			"SPELLING" : "Ili"
		},
		{
			"ID" : "654002",
			"PARENT_ID" : "654000",
			"DISTRICT_NAME" : "伊宁市",
			"SPELLING" : "Yining"
		},
		{
			"ID" : "654003",
			"PARENT_ID" : "654000",
			"DISTRICT_NAME" : "奎屯市",
			"SPELLING" : "Kuitun"
		},
		{
			"ID" : "654004",
			"PARENT_ID" : "654000",
			"DISTRICT_NAME" : "霍尔果斯市",
			"SPELLING" : "Huoerguosi"
		},
		{
			"ID" : "654021",
			"PARENT_ID" : "654000",
			"DISTRICT_NAME" : "伊宁县",
			"SPELLING" : "Yining"
		},
		{
			"ID" : "654022",
			"PARENT_ID" : "654000",
			"DISTRICT_NAME" : "察布查尔锡伯自治县",
			"SPELLING" : "Chabuchaerxibo"
		},
		{
			"ID" : "654023",
			"PARENT_ID" : "654000",
			"DISTRICT_NAME" : "霍城县",
			"SPELLING" : "Huocheng"
		},
		{
			"ID" : "654024",
			"PARENT_ID" : "654000",
			"DISTRICT_NAME" : "巩留县",
			"SPELLING" : "Gongliu"
		},
		{
			"ID" : "654025",
			"PARENT_ID" : "654000",
			"DISTRICT_NAME" : "新源县",
			"SPELLING" : "Xinyuan"
		},
		{
			"ID" : "654026",
			"PARENT_ID" : "654000",
			"DISTRICT_NAME" : "昭苏县",
			"SPELLING" : "Zhaosu"
		},
		{
			"ID" : "654027",
			"PARENT_ID" : "654000",
			"DISTRICT_NAME" : "特克斯县",
			"SPELLING" : "Tekesi"
		},
		{
			"ID" : "654028",
			"PARENT_ID" : "654000",
			"DISTRICT_NAME" : "尼勒克县",
			"SPELLING" : "Nileke"
		},
		{
			"ID" : "654200",
			"PARENT_ID" : "650000",
			"DISTRICT_NAME" : "塔城地区",
			"SPELLING" : "Qoqek"
		},
		{
			"ID" : "654201",
			"PARENT_ID" : "654200",
			"DISTRICT_NAME" : "塔城市",
			"SPELLING" : "Tacheng"
		},
		{
			"ID" : "654202",
			"PARENT_ID" : "654200",
			"DISTRICT_NAME" : "乌苏市",
			"SPELLING" : "Wusu"
		},
		{
			"ID" : "654221",
			"PARENT_ID" : "654200",
			"DISTRICT_NAME" : "额敏县",
			"SPELLING" : "Emin"
		},
		{
			"ID" : "654223",
			"PARENT_ID" : "654200",
			"DISTRICT_NAME" : "沙湾县",
			"SPELLING" : "Shawan"
		},
		{
			"ID" : "654224",
			"PARENT_ID" : "654200",
			"DISTRICT_NAME" : "托里县",
			"SPELLING" : "Tuoli"
		},
		{
			"ID" : "654225",
			"PARENT_ID" : "654200",
			"DISTRICT_NAME" : "裕民县",
			"SPELLING" : "Yumin"
		},
		{
			"ID" : "654226",
			"PARENT_ID" : "654200",
			"DISTRICT_NAME" : "和布克赛尔蒙古自治县",
			"SPELLING" : "Hebukesaier"
		},
		{
			"ID" : "654300",
			"PARENT_ID" : "650000",
			"DISTRICT_NAME" : "阿勒泰地区",
			"SPELLING" : "Altay"
		},
		{
			"ID" : "654301",
			"PARENT_ID" : "654300",
			"DISTRICT_NAME" : "阿勒泰市",
			"SPELLING" : "Aletai"
		},
		{
			"ID" : "654321",
			"PARENT_ID" : "654300",
			"DISTRICT_NAME" : "布尔津县",
			"SPELLING" : "Buerjin"
		},
		{
			"ID" : "654322",
			"PARENT_ID" : "654300",
			"DISTRICT_NAME" : "富蕴县",
			"SPELLING" : "Fuyun"
		},
		{
			"ID" : "654323",
			"PARENT_ID" : "654300",
			"DISTRICT_NAME" : "福海县",
			"SPELLING" : "Fuhai"
		},
		{
			"ID" : "654324",
			"PARENT_ID" : "654300",
			"DISTRICT_NAME" : "哈巴河县",
			"SPELLING" : "Habahe"
		},
		{
			"ID" : "654325",
			"PARENT_ID" : "654300",
			"DISTRICT_NAME" : "青河县",
			"SPELLING" : "Qinghe"
		},
		{
			"ID" : "654326",
			"PARENT_ID" : "654300",
			"DISTRICT_NAME" : "吉木乃县",
			"SPELLING" : "Jimunai"
		},
		{
			"ID" : "710000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "台湾",
			"SPELLING" : "Taiwan"
		},
		{
			"ID" : "710100",
			"PARENT_ID" : "710000",
			"DISTRICT_NAME" : "台北市",
			"SPELLING" : "Taipei"
		},
		{
			"ID" : "710101",
			"PARENT_ID" : "710100",
			"DISTRICT_NAME" : "松山区",
			"SPELLING" : "Songshan"
		},
		{
			"ID" : "710102",
			"PARENT_ID" : "710100",
			"DISTRICT_NAME" : "信义区",
			"SPELLING" : "Xinyi"
		},
		{
			"ID" : "710103",
			"PARENT_ID" : "710100",
			"DISTRICT_NAME" : "大安区",
			"SPELLING" : "Daan"
		},
		{
			"ID" : "710104",
			"PARENT_ID" : "710100",
			"DISTRICT_NAME" : "中山区",
			"SPELLING" : "Zhongshan"
		},
		{
			"ID" : "710105",
			"PARENT_ID" : "710100",
			"DISTRICT_NAME" : "中正区",
			"SPELLING" : "Zhongzheng"
		},
		{
			"ID" : "710106",
			"PARENT_ID" : "710100",
			"DISTRICT_NAME" : "大同区",
			"SPELLING" : "Datong"
		},
		{
			"ID" : "710107",
			"PARENT_ID" : "710100",
			"DISTRICT_NAME" : "万华区",
			"SPELLING" : "Wanhua"
		},
		{
			"ID" : "710108",
			"PARENT_ID" : "710100",
			"DISTRICT_NAME" : "文山区",
			"SPELLING" : "Wenshan"
		},
		{
			"ID" : "710109",
			"PARENT_ID" : "710100",
			"DISTRICT_NAME" : "南港区",
			"SPELLING" : "Nangang"
		},
		{
			"ID" : "710110",
			"PARENT_ID" : "710100",
			"DISTRICT_NAME" : "内湖区",
			"SPELLING" : "Nahu"
		},
		{
			"ID" : "710111",
			"PARENT_ID" : "710100",
			"DISTRICT_NAME" : "士林区",
			"SPELLING" : "Shilin"
		},
		{
			"ID" : "710112",
			"PARENT_ID" : "710100",
			"DISTRICT_NAME" : "北投区",
			"SPELLING" : "Beitou"
		},
		{
			"ID" : "710200",
			"PARENT_ID" : "710000",
			"DISTRICT_NAME" : "高雄市",
			"SPELLING" : "Kaohsiung"
		},
		{
			"ID" : "710201",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "盐埕区",
			"SPELLING" : "Yancheng"
		},
		{
			"ID" : "710202",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "鼓山区",
			"SPELLING" : "Gushan"
		},
		{
			"ID" : "710203",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "左营区",
			"SPELLING" : "Zuoying"
		},
		{
			"ID" : "710204",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "楠梓区",
			"SPELLING" : "Nanzi"
		},
		{
			"ID" : "710205",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "三民区",
			"SPELLING" : "Sanmin"
		},
		{
			"ID" : "710206",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "新兴区",
			"SPELLING" : "Xinxing"
		},
		{
			"ID" : "710207",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "前金区",
			"SPELLING" : "Qianjin"
		},
		{
			"ID" : "710208",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "苓雅区",
			"SPELLING" : "Lingya"
		},
		{
			"ID" : "710209",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "前镇区",
			"SPELLING" : "Qianzhen"
		},
		{
			"ID" : "710210",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "旗津区",
			"SPELLING" : "Qijin"
		},
		{
			"ID" : "710211",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "小港区",
			"SPELLING" : "Xiaogang"
		},
		{
			"ID" : "710212",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "凤山区",
			"SPELLING" : "Fengshan"
		},
		{
			"ID" : "710213",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "林园区",
			"SPELLING" : "Linyuan"
		},
		{
			"ID" : "710214",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "大寮区",
			"SPELLING" : "Daliao"
		},
		{
			"ID" : "710215",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "大树区",
			"SPELLING" : "Dashu"
		},
		{
			"ID" : "710216",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "大社区",
			"SPELLING" : "Dashe"
		},
		{
			"ID" : "710217",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "仁武区",
			"SPELLING" : "Renwu"
		},
		{
			"ID" : "710218",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "鸟松区",
			"SPELLING" : "Niaosong"
		},
		{
			"ID" : "710219",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "冈山区",
			"SPELLING" : "Gangshan"
		},
		{
			"ID" : "710220",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "桥头区",
			"SPELLING" : "Qiaotou"
		},
		{
			"ID" : "710221",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "燕巢区",
			"SPELLING" : "Yanchao"
		},
		{
			"ID" : "710222",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "田寮区",
			"SPELLING" : "Tianliao"
		},
		{
			"ID" : "710223",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "阿莲区",
			"SPELLING" : "Alian"
		},
		{
			"ID" : "710224",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "路竹区",
			"SPELLING" : "Luzhu"
		},
		{
			"ID" : "710225",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "湖内区",
			"SPELLING" : "Huna"
		},
		{
			"ID" : "710226",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "茄萣区",
			"SPELLING" : "Qieding"
		},
		{
			"ID" : "710227",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "永安区",
			"SPELLING" : "Yongan"
		},
		{
			"ID" : "710228",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "弥陀区",
			"SPELLING" : "Mituo"
		},
		{
			"ID" : "710229",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "梓官区",
			"SPELLING" : "Ziguan"
		},
		{
			"ID" : "710230",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "旗山区",
			"SPELLING" : "Qishan"
		},
		{
			"ID" : "710231",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "美浓区",
			"SPELLING" : "Meinong"
		},
		{
			"ID" : "710232",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "六龟区",
			"SPELLING" : "Liugui"
		},
		{
			"ID" : "710233",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "甲仙区",
			"SPELLING" : "Jiaxian"
		},
		{
			"ID" : "710234",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "杉林区",
			"SPELLING" : "Shanlin"
		},
		{
			"ID" : "710235",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "内门区",
			"SPELLING" : "Namen"
		},
		{
			"ID" : "710236",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "茂林区",
			"SPELLING" : "Maolin"
		},
		{
			"ID" : "710237",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "桃源区",
			"SPELLING" : "Taoyuan"
		},
		{
			"ID" : "710238",
			"PARENT_ID" : "710200",
			"DISTRICT_NAME" : "那玛夏区",
			"SPELLING" : "Namaxia"
		},
		{
			"ID" : "710300",
			"PARENT_ID" : "710000",
			"DISTRICT_NAME" : "基隆市",
			"SPELLING" : "Keelung"
		},
		{
			"ID" : "710301",
			"PARENT_ID" : "710300",
			"DISTRICT_NAME" : "中正区",
			"SPELLING" : "Zhongzheng"
		},
		{
			"ID" : "710302",
			"PARENT_ID" : "710300",
			"DISTRICT_NAME" : "七堵区",
			"SPELLING" : "Qidu"
		},
		{
			"ID" : "710303",
			"PARENT_ID" : "710300",
			"DISTRICT_NAME" : "暖暖区",
			"SPELLING" : "Nuannuan"
		},
		{
			"ID" : "710304",
			"PARENT_ID" : "710300",
			"DISTRICT_NAME" : "仁爱区",
			"SPELLING" : "Renai"
		},
		{
			"ID" : "710305",
			"PARENT_ID" : "710300",
			"DISTRICT_NAME" : "中山区",
			"SPELLING" : "Zhongshan"
		},
		{
			"ID" : "710306",
			"PARENT_ID" : "710300",
			"DISTRICT_NAME" : "安乐区",
			"SPELLING" : "Anle"
		},
		{
			"ID" : "710307",
			"PARENT_ID" : "710300",
			"DISTRICT_NAME" : "信义区",
			"SPELLING" : "Xinyi"
		},
		{
			"ID" : "710400",
			"PARENT_ID" : "710000",
			"DISTRICT_NAME" : "台中市",
			"SPELLING" : "Taichung"
		},
		{
			"ID" : "710401",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "中区",
			"SPELLING" : "Zhongqu"
		},
		{
			"ID" : "710402",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "东区",
			"SPELLING" : "Dongqu"
		},
		{
			"ID" : "710403",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "南区",
			"SPELLING" : "Nanqu"
		},
		{
			"ID" : "710404",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "西区",
			"SPELLING" : "Xiqu"
		},
		{
			"ID" : "710405",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "北区",
			"SPELLING" : "Beiqu"
		},
		{
			"ID" : "710406",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "西屯区",
			"SPELLING" : "Xitun"
		},
		{
			"ID" : "710407",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "南屯区",
			"SPELLING" : "Nantun"
		},
		{
			"ID" : "710408",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "北屯区",
			"SPELLING" : "Beitun"
		},
		{
			"ID" : "710409",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "丰原区",
			"SPELLING" : "Fengyuan"
		},
		{
			"ID" : "710410",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "东势区",
			"SPELLING" : "Dongshi"
		},
		{
			"ID" : "710411",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "大甲区",
			"SPELLING" : "Dajia"
		},
		{
			"ID" : "710412",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "清水区",
			"SPELLING" : "Qingshui"
		},
		{
			"ID" : "710413",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "沙鹿区",
			"SPELLING" : "Shalu"
		},
		{
			"ID" : "710414",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "梧栖区",
			"SPELLING" : "Wuqi"
		},
		{
			"ID" : "710415",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "后里区",
			"SPELLING" : "Houli"
		},
		{
			"ID" : "710416",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "神冈区",
			"SPELLING" : "Shengang"
		},
		{
			"ID" : "710417",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "潭子区",
			"SPELLING" : "Tanzi"
		},
		{
			"ID" : "710418",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "大雅区",
			"SPELLING" : "Daya"
		},
		{
			"ID" : "710419",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "新社区",
			"SPELLING" : "Xinshe"
		},
		{
			"ID" : "710420",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "石冈区",
			"SPELLING" : "Shigang"
		},
		{
			"ID" : "710421",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "外埔区",
			"SPELLING" : "Waipu"
		},
		{
			"ID" : "710422",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "大安区",
			"SPELLING" : "Daan"
		},
		{
			"ID" : "710423",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "乌日区",
			"SPELLING" : "Wuri"
		},
		{
			"ID" : "710424",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "大肚区",
			"SPELLING" : "Dadu"
		},
		{
			"ID" : "710425",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "龙井区",
			"SPELLING" : "Longjing"
		},
		{
			"ID" : "710426",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "雾峰区",
			"SPELLING" : "Wufeng"
		},
		{
			"ID" : "710427",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "太平区",
			"SPELLING" : "Taiping"
		},
		{
			"ID" : "710428",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "大里区",
			"SPELLING" : "Dali"
		},
		{
			"ID" : "710429",
			"PARENT_ID" : "710400",
			"DISTRICT_NAME" : "和平区",
			"SPELLING" : "Heping"
		},
		{
			"ID" : "710500",
			"PARENT_ID" : "710000",
			"DISTRICT_NAME" : "台南市",
			"SPELLING" : "Tainan"
		},
		{
			"ID" : "710501",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "东区",
			"SPELLING" : "Dongqu"
		},
		{
			"ID" : "710502",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "南区",
			"SPELLING" : "Nanqu"
		},
		{
			"ID" : "710504",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "北区",
			"SPELLING" : "Beiqu"
		},
		{
			"ID" : "710506",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "安南区",
			"SPELLING" : "Annan"
		},
		{
			"ID" : "710507",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "安平区",
			"SPELLING" : "Anping"
		},
		{
			"ID" : "710508",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "中西区",
			"SPELLING" : "Zhongxi"
		},
		{
			"ID" : "710509",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "新营区",
			"SPELLING" : "Xinying"
		},
		{
			"ID" : "710510",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "盐水区",
			"SPELLING" : "Yanshui"
		},
		{
			"ID" : "710511",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "白河区",
			"SPELLING" : "Baihe"
		},
		{
			"ID" : "710512",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "柳营区",
			"SPELLING" : "Liuying"
		},
		{
			"ID" : "710513",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "后壁区",
			"SPELLING" : "Houbi"
		},
		{
			"ID" : "710514",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "东山区",
			"SPELLING" : "Dongshan"
		},
		{
			"ID" : "710515",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "麻豆区",
			"SPELLING" : "Madou"
		},
		{
			"ID" : "710516",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "下营区",
			"SPELLING" : "Xiaying"
		},
		{
			"ID" : "710517",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "六甲区",
			"SPELLING" : "Liujia"
		},
		{
			"ID" : "710518",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "官田区",
			"SPELLING" : "Guantian"
		},
		{
			"ID" : "710519",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "大内区",
			"SPELLING" : "Dana"
		},
		{
			"ID" : "710520",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "佳里区",
			"SPELLING" : "Jiali"
		},
		{
			"ID" : "710521",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "学甲区",
			"SPELLING" : "Xuejia"
		},
		{
			"ID" : "710522",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "西港区",
			"SPELLING" : "Xigang"
		},
		{
			"ID" : "710523",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "七股区",
			"SPELLING" : "Qigu"
		},
		{
			"ID" : "710524",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "将军区",
			"SPELLING" : "Jiangjun"
		},
		{
			"ID" : "710525",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "北门区",
			"SPELLING" : "Beimen"
		},
		{
			"ID" : "710526",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "新化区",
			"SPELLING" : "Xinhua"
		},
		{
			"ID" : "710527",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "善化区",
			"SPELLING" : "Shanhua"
		},
		{
			"ID" : "710528",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "新市区",
			"SPELLING" : "Xinshi"
		},
		{
			"ID" : "710529",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "安定区",
			"SPELLING" : "Anding"
		},
		{
			"ID" : "710530",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "山上区",
			"SPELLING" : "Shanshang"
		},
		{
			"ID" : "710531",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "玉井区",
			"SPELLING" : "Yujing"
		},
		{
			"ID" : "710532",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "楠西区",
			"SPELLING" : "Nanxi"
		},
		{
			"ID" : "710533",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "南化区",
			"SPELLING" : "Nanhua"
		},
		{
			"ID" : "710534",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "左镇区",
			"SPELLING" : "Zuozhen"
		},
		{
			"ID" : "710535",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "仁德区",
			"SPELLING" : "Rende"
		},
		{
			"ID" : "710536",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "归仁区",
			"SPELLING" : "Guiren"
		},
		{
			"ID" : "710537",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "关庙区",
			"SPELLING" : "Guanmiao"
		},
		{
			"ID" : "710538",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "龙崎区",
			"SPELLING" : "Longqi"
		},
		{
			"ID" : "710539",
			"PARENT_ID" : "710500",
			"DISTRICT_NAME" : "永康区",
			"SPELLING" : "Yongkang"
		},
		{
			"ID" : "710600",
			"PARENT_ID" : "710000",
			"DISTRICT_NAME" : "新竹市",
			"SPELLING" : "Hsinchu"
		},
		{
			"ID" : "710601",
			"PARENT_ID" : "710600",
			"DISTRICT_NAME" : "东区",
			"SPELLING" : "Dongqu"
		},
		{
			"ID" : "710602",
			"PARENT_ID" : "710600",
			"DISTRICT_NAME" : "北区",
			"SPELLING" : "Beiqu"
		},
		{
			"ID" : "710603",
			"PARENT_ID" : "710600",
			"DISTRICT_NAME" : "香山区",
			"SPELLING" : "Xiangshan"
		},
		{
			"ID" : "710700",
			"PARENT_ID" : "710000",
			"DISTRICT_NAME" : "嘉义市",
			"SPELLING" : "Chiayi"
		},
		{
			"ID" : "710701",
			"PARENT_ID" : "710700",
			"DISTRICT_NAME" : "东区",
			"SPELLING" : "Dongqu"
		},
		{
			"ID" : "710702",
			"PARENT_ID" : "710700",
			"DISTRICT_NAME" : "西区",
			"SPELLING" : "Xiqu"
		},
		{
			"ID" : "710800",
			"PARENT_ID" : "710000",
			"DISTRICT_NAME" : "新北市",
			"SPELLING" : "New Taipei"
		},
		{
			"ID" : "710801",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "板桥区",
			"SPELLING" : "Banqiao"
		},
		{
			"ID" : "710802",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "三重区",
			"SPELLING" : "Sanzhong"
		},
		{
			"ID" : "710803",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "中和区",
			"SPELLING" : "Zhonghe"
		},
		{
			"ID" : "710804",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "永和区",
			"SPELLING" : "Yonghe"
		},
		{
			"ID" : "710805",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "新庄区",
			"SPELLING" : "Xinzhuang"
		},
		{
			"ID" : "710806",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "新店区",
			"SPELLING" : "Xindian"
		},
		{
			"ID" : "710807",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "树林区",
			"SPELLING" : "Shulin"
		},
		{
			"ID" : "710808",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "莺歌区",
			"SPELLING" : "Yingge"
		},
		{
			"ID" : "710809",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "三峡区",
			"SPELLING" : "Sanxia"
		},
		{
			"ID" : "710810",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "淡水区",
			"SPELLING" : "Danshui"
		},
		{
			"ID" : "710811",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "汐止区",
			"SPELLING" : "Xizhi"
		},
		{
			"ID" : "710812",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "瑞芳区",
			"SPELLING" : "Ruifang"
		},
		{
			"ID" : "710813",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "土城区",
			"SPELLING" : "Tucheng"
		},
		{
			"ID" : "710814",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "芦洲区",
			"SPELLING" : "Luzhou"
		},
		{
			"ID" : "710815",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "五股区",
			"SPELLING" : "Wugu"
		},
		{
			"ID" : "710816",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "泰山区",
			"SPELLING" : "Taishan"
		},
		{
			"ID" : "710817",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "林口区",
			"SPELLING" : "Linkou"
		},
		{
			"ID" : "710818",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "深坑区",
			"SPELLING" : "Shenkeng"
		},
		{
			"ID" : "710819",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "石碇区",
			"SPELLING" : "Shiding"
		},
		{
			"ID" : "710820",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "坪林区",
			"SPELLING" : "Pinglin"
		},
		{
			"ID" : "710821",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "三芝区",
			"SPELLING" : "Sanzhi"
		},
		{
			"ID" : "710822",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "石门区",
			"SPELLING" : "Shimen"
		},
		{
			"ID" : "710823",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "八里区",
			"SPELLING" : "Bali"
		},
		{
			"ID" : "710824",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "平溪区",
			"SPELLING" : "Pingxi"
		},
		{
			"ID" : "710825",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "双溪区",
			"SPELLING" : "Shuangxi"
		},
		{
			"ID" : "710826",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "贡寮区",
			"SPELLING" : "Gongliao"
		},
		{
			"ID" : "710827",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "金山区",
			"SPELLING" : "Jinshan"
		},
		{
			"ID" : "710828",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "万里区",
			"SPELLING" : "Wanli"
		},
		{
			"ID" : "710829",
			"PARENT_ID" : "710800",
			"DISTRICT_NAME" : "乌来区",
			"SPELLING" : "Wulai"
		},
		{
			"ID" : "712200",
			"PARENT_ID" : "710000",
			"DISTRICT_NAME" : "宜兰县",
			"SPELLING" : "Yilan"
		},
		{
			"ID" : "712201",
			"PARENT_ID" : "712200",
			"DISTRICT_NAME" : "宜兰市",
			"SPELLING" : "Yilan"
		},
		{
			"ID" : "712221",
			"PARENT_ID" : "712200",
			"DISTRICT_NAME" : "罗东镇",
			"SPELLING" : "Luodong"
		},
		{
			"ID" : "712222",
			"PARENT_ID" : "712200",
			"DISTRICT_NAME" : "苏澳镇",
			"SPELLING" : "Suao"
		},
		{
			"ID" : "712223",
			"PARENT_ID" : "712200",
			"DISTRICT_NAME" : "头城镇",
			"SPELLING" : "Toucheng"
		},
		{
			"ID" : "712224",
			"PARENT_ID" : "712200",
			"DISTRICT_NAME" : "礁溪乡",
			"SPELLING" : "Jiaoxi"
		},
		{
			"ID" : "712225",
			"PARENT_ID" : "712200",
			"DISTRICT_NAME" : "壮围乡",
			"SPELLING" : "Zhuangwei"
		},
		{
			"ID" : "712226",
			"PARENT_ID" : "712200",
			"DISTRICT_NAME" : "员山乡",
			"SPELLING" : "Yuanshan"
		},
		{
			"ID" : "712227",
			"PARENT_ID" : "712200",
			"DISTRICT_NAME" : "冬山乡",
			"SPELLING" : "Dongshan"
		},
		{
			"ID" : "712228",
			"PARENT_ID" : "712200",
			"DISTRICT_NAME" : "五结乡",
			"SPELLING" : "Wujie"
		},
		{
			"ID" : "712229",
			"PARENT_ID" : "712200",
			"DISTRICT_NAME" : "三星乡",
			"SPELLING" : "Sanxing"
		},
		{
			"ID" : "712230",
			"PARENT_ID" : "712200",
			"DISTRICT_NAME" : "大同乡",
			"SPELLING" : "Datong"
		},
		{
			"ID" : "712231",
			"PARENT_ID" : "712200",
			"DISTRICT_NAME" : "南澳乡",
			"SPELLING" : "Nanao"
		},
		{
			"ID" : "712300",
			"PARENT_ID" : "710000",
			"DISTRICT_NAME" : "桃园县",
			"SPELLING" : "Taoyuan"
		},
		{
			"ID" : "712301",
			"PARENT_ID" : "712300",
			"DISTRICT_NAME" : "桃园市",
			"SPELLING" : "Taoyuan"
		},
		{
			"ID" : "712302",
			"PARENT_ID" : "712300",
			"DISTRICT_NAME" : "中坜市",
			"SPELLING" : "Zhongli"
		},
		{
			"ID" : "712303",
			"PARENT_ID" : "712300",
			"DISTRICT_NAME" : "平镇市",
			"SPELLING" : "Pingzhen"
		},
		{
			"ID" : "712304",
			"PARENT_ID" : "712300",
			"DISTRICT_NAME" : "八德市",
			"SPELLING" : "Bade"
		},
		{
			"ID" : "712305",
			"PARENT_ID" : "712300",
			"DISTRICT_NAME" : "杨梅市",
			"SPELLING" : "Yangmei"
		},
		{
			"ID" : "712306",
			"PARENT_ID" : "712300",
			"DISTRICT_NAME" : "芦竹市",
			"SPELLING" : "Luzhu"
		},
		{
			"ID" : "712321",
			"PARENT_ID" : "712300",
			"DISTRICT_NAME" : "大溪镇",
			"SPELLING" : "Daxi"
		},
		{
			"ID" : "712324",
			"PARENT_ID" : "712300",
			"DISTRICT_NAME" : "大园乡",
			"SPELLING" : "Dayuan"
		},
		{
			"ID" : "712325",
			"PARENT_ID" : "712300",
			"DISTRICT_NAME" : "龟山乡",
			"SPELLING" : "Guishan"
		},
		{
			"ID" : "712327",
			"PARENT_ID" : "712300",
			"DISTRICT_NAME" : "龙潭乡",
			"SPELLING" : "Longtan"
		},
		{
			"ID" : "712329",
			"PARENT_ID" : "712300",
			"DISTRICT_NAME" : "新屋乡",
			"SPELLING" : "Xinwu"
		},
		{
			"ID" : "712330",
			"PARENT_ID" : "712300",
			"DISTRICT_NAME" : "观音乡",
			"SPELLING" : "Guanyin"
		},
		{
			"ID" : "712331",
			"PARENT_ID" : "712300",
			"DISTRICT_NAME" : "复兴乡",
			"SPELLING" : "Fuxing"
		},
		{
			"ID" : "712400",
			"PARENT_ID" : "710000",
			"DISTRICT_NAME" : "新竹县",
			"SPELLING" : "Hsinchu"
		},
		{
			"ID" : "712401",
			"PARENT_ID" : "712400",
			"DISTRICT_NAME" : "竹北市",
			"SPELLING" : "Zhubei"
		},
		{
			"ID" : "712421",
			"PARENT_ID" : "712400",
			"DISTRICT_NAME" : "竹东镇",
			"SPELLING" : "Zhudong"
		},
		{
			"ID" : "712422",
			"PARENT_ID" : "712400",
			"DISTRICT_NAME" : "新埔镇",
			"SPELLING" : "Xinpu"
		},
		{
			"ID" : "712423",
			"PARENT_ID" : "712400",
			"DISTRICT_NAME" : "关西镇",
			"SPELLING" : "Guanxi"
		},
		{
			"ID" : "712424",
			"PARENT_ID" : "712400",
			"DISTRICT_NAME" : "湖口乡",
			"SPELLING" : "Hukou"
		},
		{
			"ID" : "712425",
			"PARENT_ID" : "712400",
			"DISTRICT_NAME" : "新丰乡",
			"SPELLING" : "Xinfeng"
		},
		{
			"ID" : "712426",
			"PARENT_ID" : "712400",
			"DISTRICT_NAME" : "芎林乡",
			"SPELLING" : "Xionglin"
		},
		{
			"ID" : "712427",
			"PARENT_ID" : "712400",
			"DISTRICT_NAME" : "横山乡",
			"SPELLING" : "Hengshan"
		},
		{
			"ID" : "712428",
			"PARENT_ID" : "712400",
			"DISTRICT_NAME" : "北埔乡",
			"SPELLING" : "Beipu"
		},
		{
			"ID" : "712429",
			"PARENT_ID" : "712400",
			"DISTRICT_NAME" : "宝山乡",
			"SPELLING" : "Baoshan"
		},
		{
			"ID" : "712430",
			"PARENT_ID" : "712400",
			"DISTRICT_NAME" : "峨眉乡",
			"SPELLING" : "Emei"
		},
		{
			"ID" : "712431",
			"PARENT_ID" : "712400",
			"DISTRICT_NAME" : "尖石乡",
			"SPELLING" : "Jianshi"
		},
		{
			"ID" : "712432",
			"PARENT_ID" : "712400",
			"DISTRICT_NAME" : "五峰乡",
			"SPELLING" : "Wufeng"
		},
		{
			"ID" : "712500",
			"PARENT_ID" : "710000",
			"DISTRICT_NAME" : "苗栗县",
			"SPELLING" : "Miaoli"
		},
		{
			"ID" : "712501",
			"PARENT_ID" : "712500",
			"DISTRICT_NAME" : "苗栗市",
			"SPELLING" : "Miaoli"
		},
		{
			"ID" : "712521",
			"PARENT_ID" : "712500",
			"DISTRICT_NAME" : "苑里镇",
			"SPELLING" : "Yuanli"
		},
		{
			"ID" : "712522",
			"PARENT_ID" : "712500",
			"DISTRICT_NAME" : "通霄镇",
			"SPELLING" : "Tongxiao"
		},
		{
			"ID" : "712523",
			"PARENT_ID" : "712500",
			"DISTRICT_NAME" : "竹南镇",
			"SPELLING" : "Zhunan"
		},
		{
			"ID" : "712524",
			"PARENT_ID" : "712500",
			"DISTRICT_NAME" : "头份镇",
			"SPELLING" : "Toufen"
		},
		{
			"ID" : "712525",
			"PARENT_ID" : "712500",
			"DISTRICT_NAME" : "后龙镇",
			"SPELLING" : "Houlong"
		},
		{
			"ID" : "712526",
			"PARENT_ID" : "712500",
			"DISTRICT_NAME" : "卓兰镇",
			"SPELLING" : "Zhuolan"
		},
		{
			"ID" : "712527",
			"PARENT_ID" : "712500",
			"DISTRICT_NAME" : "大湖乡",
			"SPELLING" : "Dahu"
		},
		{
			"ID" : "712528",
			"PARENT_ID" : "712500",
			"DISTRICT_NAME" : "公馆乡",
			"SPELLING" : "Gongguan"
		},
		{
			"ID" : "712529",
			"PARENT_ID" : "712500",
			"DISTRICT_NAME" : "铜锣乡",
			"SPELLING" : "Tongluo"
		},
		{
			"ID" : "712530",
			"PARENT_ID" : "712500",
			"DISTRICT_NAME" : "南庄乡",
			"SPELLING" : "Nanzhuang"
		},
		{
			"ID" : "712531",
			"PARENT_ID" : "712500",
			"DISTRICT_NAME" : "头屋乡",
			"SPELLING" : "Touwu"
		},
		{
			"ID" : "712532",
			"PARENT_ID" : "712500",
			"DISTRICT_NAME" : "三义乡",
			"SPELLING" : "Sanyi"
		},
		{
			"ID" : "712533",
			"PARENT_ID" : "712500",
			"DISTRICT_NAME" : "西湖乡",
			"SPELLING" : "Xihu"
		},
		{
			"ID" : "712534",
			"PARENT_ID" : "712500",
			"DISTRICT_NAME" : "造桥乡",
			"SPELLING" : "Zaoqiao"
		},
		{
			"ID" : "712535",
			"PARENT_ID" : "712500",
			"DISTRICT_NAME" : "三湾乡",
			"SPELLING" : "Sanwan"
		},
		{
			"ID" : "712536",
			"PARENT_ID" : "712500",
			"DISTRICT_NAME" : "狮潭乡",
			"SPELLING" : "Shitan"
		},
		{
			"ID" : "712537",
			"PARENT_ID" : "712500",
			"DISTRICT_NAME" : "泰安乡",
			"SPELLING" : "Taian"
		},
		{
			"ID" : "712700",
			"PARENT_ID" : "710000",
			"DISTRICT_NAME" : "彰化县",
			"SPELLING" : "Changhua"
		},
		{
			"ID" : "712701",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "彰化市",
			"SPELLING" : "Zhanghuashi"
		},
		{
			"ID" : "712721",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "鹿港镇",
			"SPELLING" : "Lugang"
		},
		{
			"ID" : "712722",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "和美镇",
			"SPELLING" : "Hemei"
		},
		{
			"ID" : "712723",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "线西乡",
			"SPELLING" : "Xianxi"
		},
		{
			"ID" : "712724",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "伸港乡",
			"SPELLING" : "Shengang"
		},
		{
			"ID" : "712725",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "福兴乡",
			"SPELLING" : "Fuxing"
		},
		{
			"ID" : "712726",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "秀水乡",
			"SPELLING" : "Xiushui"
		},
		{
			"ID" : "712727",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "花坛乡",
			"SPELLING" : "Huatan"
		},
		{
			"ID" : "712728",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "芬园乡",
			"SPELLING" : "Fenyuan"
		},
		{
			"ID" : "712729",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "员林镇",
			"SPELLING" : "Yuanlin"
		},
		{
			"ID" : "712730",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "溪湖镇",
			"SPELLING" : "Xihu"
		},
		{
			"ID" : "712731",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "田中镇",
			"SPELLING" : "Tianzhong"
		},
		{
			"ID" : "712732",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "大村乡",
			"SPELLING" : "Dacun"
		},
		{
			"ID" : "712733",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "埔盐乡",
			"SPELLING" : "Puyan"
		},
		{
			"ID" : "712734",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "埔心乡",
			"SPELLING" : "Puxin"
		},
		{
			"ID" : "712735",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "永靖乡",
			"SPELLING" : "Yongjing"
		},
		{
			"ID" : "712736",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "社头乡",
			"SPELLING" : "Shetou"
		},
		{
			"ID" : "712737",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "二水乡",
			"SPELLING" : "Ershui"
		},
		{
			"ID" : "712738",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "北斗镇",
			"SPELLING" : "Beidou"
		},
		{
			"ID" : "712739",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "二林镇",
			"SPELLING" : "Erlin"
		},
		{
			"ID" : "712740",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "田尾乡",
			"SPELLING" : "Tianwei"
		},
		{
			"ID" : "712741",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "埤头乡",
			"SPELLING" : "Pitou"
		},
		{
			"ID" : "712742",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "芳苑乡",
			"SPELLING" : "Fangyuan"
		},
		{
			"ID" : "712743",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "大城乡",
			"SPELLING" : "Dacheng"
		},
		{
			"ID" : "712744",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "竹塘乡",
			"SPELLING" : "Zhutang"
		},
		{
			"ID" : "712745",
			"PARENT_ID" : "712700",
			"DISTRICT_NAME" : "溪州乡",
			"SPELLING" : "Xizhou"
		},
		{
			"ID" : "712800",
			"PARENT_ID" : "710000",
			"DISTRICT_NAME" : "南投县",
			"SPELLING" : "Nantou"
		},
		{
			"ID" : "712801",
			"PARENT_ID" : "712800",
			"DISTRICT_NAME" : "南投市",
			"SPELLING" : "Nantoushi"
		},
		{
			"ID" : "712821",
			"PARENT_ID" : "712800",
			"DISTRICT_NAME" : "埔里镇",
			"SPELLING" : "Puli"
		},
		{
			"ID" : "712822",
			"PARENT_ID" : "712800",
			"DISTRICT_NAME" : "草屯镇",
			"SPELLING" : "Caotun"
		},
		{
			"ID" : "712823",
			"PARENT_ID" : "712800",
			"DISTRICT_NAME" : "竹山镇",
			"SPELLING" : "Zhushan"
		},
		{
			"ID" : "712824",
			"PARENT_ID" : "712800",
			"DISTRICT_NAME" : "集集镇",
			"SPELLING" : "Jiji"
		},
		{
			"ID" : "712825",
			"PARENT_ID" : "712800",
			"DISTRICT_NAME" : "名间乡",
			"SPELLING" : "Mingjian"
		},
		{
			"ID" : "712826",
			"PARENT_ID" : "712800",
			"DISTRICT_NAME" : "鹿谷乡",
			"SPELLING" : "Lugu"
		},
		{
			"ID" : "712827",
			"PARENT_ID" : "712800",
			"DISTRICT_NAME" : "中寮乡",
			"SPELLING" : "Zhongliao"
		},
		{
			"ID" : "712828",
			"PARENT_ID" : "712800",
			"DISTRICT_NAME" : "鱼池乡",
			"SPELLING" : "Yuchi"
		},
		{
			"ID" : "712829",
			"PARENT_ID" : "712800",
			"DISTRICT_NAME" : "国姓乡",
			"SPELLING" : "Guoxing"
		},
		{
			"ID" : "712830",
			"PARENT_ID" : "712800",
			"DISTRICT_NAME" : "水里乡",
			"SPELLING" : "Shuili"
		},
		{
			"ID" : "712831",
			"PARENT_ID" : "712800",
			"DISTRICT_NAME" : "信义乡",
			"SPELLING" : "Xinyi"
		},
		{
			"ID" : "712832",
			"PARENT_ID" : "712800",
			"DISTRICT_NAME" : "仁爱乡",
			"SPELLING" : "Renai"
		},
		{
			"ID" : "712900",
			"PARENT_ID" : "710000",
			"DISTRICT_NAME" : "云林县",
			"SPELLING" : "Yunlin"
		},
		{
			"ID" : "712901",
			"PARENT_ID" : "712900",
			"DISTRICT_NAME" : "斗六市",
			"SPELLING" : "Douliu"
		},
		{
			"ID" : "712921",
			"PARENT_ID" : "712900",
			"DISTRICT_NAME" : "斗南镇",
			"SPELLING" : "Dounan"
		},
		{
			"ID" : "712922",
			"PARENT_ID" : "712900",
			"DISTRICT_NAME" : "虎尾镇",
			"SPELLING" : "Huwei"
		},
		{
			"ID" : "712923",
			"PARENT_ID" : "712900",
			"DISTRICT_NAME" : "西螺镇",
			"SPELLING" : "Xiluo"
		},
		{
			"ID" : "712924",
			"PARENT_ID" : "712900",
			"DISTRICT_NAME" : "土库镇",
			"SPELLING" : "Tuku"
		},
		{
			"ID" : "712925",
			"PARENT_ID" : "712900",
			"DISTRICT_NAME" : "北港镇",
			"SPELLING" : "Beigang"
		},
		{
			"ID" : "712926",
			"PARENT_ID" : "712900",
			"DISTRICT_NAME" : "古坑乡",
			"SPELLING" : "Gukeng"
		},
		{
			"ID" : "712927",
			"PARENT_ID" : "712900",
			"DISTRICT_NAME" : "大埤乡",
			"SPELLING" : "Dapi"
		},
		{
			"ID" : "712928",
			"PARENT_ID" : "712900",
			"DISTRICT_NAME" : "莿桐乡",
			"SPELLING" : "Citong"
		},
		{
			"ID" : "712929",
			"PARENT_ID" : "712900",
			"DISTRICT_NAME" : "林内乡",
			"SPELLING" : "Linna"
		},
		{
			"ID" : "712930",
			"PARENT_ID" : "712900",
			"DISTRICT_NAME" : "二仑乡",
			"SPELLING" : "Erlun"
		},
		{
			"ID" : "712931",
			"PARENT_ID" : "712900",
			"DISTRICT_NAME" : "仑背乡",
			"SPELLING" : "Lunbei"
		},
		{
			"ID" : "712932",
			"PARENT_ID" : "712900",
			"DISTRICT_NAME" : "麦寮乡",
			"SPELLING" : "Mailiao"
		},
		{
			"ID" : "712933",
			"PARENT_ID" : "712900",
			"DISTRICT_NAME" : "东势乡",
			"SPELLING" : "Dongshi"
		},
		{
			"ID" : "712934",
			"PARENT_ID" : "712900",
			"DISTRICT_NAME" : "褒忠乡",
			"SPELLING" : "Baozhong"
		},
		{
			"ID" : "712935",
			"PARENT_ID" : "712900",
			"DISTRICT_NAME" : "台西乡",
			"SPELLING" : "Taixi"
		},
		{
			"ID" : "712936",
			"PARENT_ID" : "712900",
			"DISTRICT_NAME" : "元长乡",
			"SPELLING" : "Yuanchang"
		},
		{
			"ID" : "712937",
			"PARENT_ID" : "712900",
			"DISTRICT_NAME" : "四湖乡",
			"SPELLING" : "Sihu"
		},
		{
			"ID" : "712938",
			"PARENT_ID" : "712900",
			"DISTRICT_NAME" : "口湖乡",
			"SPELLING" : "Kouhu"
		},
		{
			"ID" : "712939",
			"PARENT_ID" : "712900",
			"DISTRICT_NAME" : "水林乡",
			"SPELLING" : "Shuilin"
		},
		{
			"ID" : "713000",
			"PARENT_ID" : "710000",
			"DISTRICT_NAME" : "嘉义县",
			"SPELLING" : "Chiayi"
		},
		{
			"ID" : "713001",
			"PARENT_ID" : "713000",
			"DISTRICT_NAME" : "太保市",
			"SPELLING" : "Taibao"
		},
		{
			"ID" : "713002",
			"PARENT_ID" : "713000",
			"DISTRICT_NAME" : "朴子市",
			"SPELLING" : "Puzi"
		},
		{
			"ID" : "713023",
			"PARENT_ID" : "713000",
			"DISTRICT_NAME" : "布袋镇",
			"SPELLING" : "Budai"
		},
		{
			"ID" : "713024",
			"PARENT_ID" : "713000",
			"DISTRICT_NAME" : "大林镇",
			"SPELLING" : "Dalin"
		},
		{
			"ID" : "713025",
			"PARENT_ID" : "713000",
			"DISTRICT_NAME" : "民雄乡",
			"SPELLING" : "Minxiong"
		},
		{
			"ID" : "713026",
			"PARENT_ID" : "713000",
			"DISTRICT_NAME" : "溪口乡",
			"SPELLING" : "Xikou"
		},
		{
			"ID" : "713027",
			"PARENT_ID" : "713000",
			"DISTRICT_NAME" : "新港乡",
			"SPELLING" : "Xingang"
		},
		{
			"ID" : "713028",
			"PARENT_ID" : "713000",
			"DISTRICT_NAME" : "六脚乡",
			"SPELLING" : "Liujiao"
		},
		{
			"ID" : "713029",
			"PARENT_ID" : "713000",
			"DISTRICT_NAME" : "东石乡",
			"SPELLING" : "Dongshi"
		},
		{
			"ID" : "713030",
			"PARENT_ID" : "713000",
			"DISTRICT_NAME" : "义竹乡",
			"SPELLING" : "Yizhu"
		},
		{
			"ID" : "713031",
			"PARENT_ID" : "713000",
			"DISTRICT_NAME" : "鹿草乡",
			"SPELLING" : "Lucao"
		},
		{
			"ID" : "713032",
			"PARENT_ID" : "713000",
			"DISTRICT_NAME" : "水上乡",
			"SPELLING" : "Shuishang"
		},
		{
			"ID" : "713033",
			"PARENT_ID" : "713000",
			"DISTRICT_NAME" : "中埔乡",
			"SPELLING" : "Zhongpu"
		},
		{
			"ID" : "713034",
			"PARENT_ID" : "713000",
			"DISTRICT_NAME" : "竹崎乡",
			"SPELLING" : "Zhuqi"
		},
		{
			"ID" : "713035",
			"PARENT_ID" : "713000",
			"DISTRICT_NAME" : "梅山乡",
			"SPELLING" : "Meishan"
		},
		{
			"ID" : "713036",
			"PARENT_ID" : "713000",
			"DISTRICT_NAME" : "番路乡",
			"SPELLING" : "Fanlu"
		},
		{
			"ID" : "713037",
			"PARENT_ID" : "713000",
			"DISTRICT_NAME" : "大埔乡",
			"SPELLING" : "Dapu"
		},
		{
			"ID" : "713038",
			"PARENT_ID" : "713000",
			"DISTRICT_NAME" : "阿里山乡",
			"SPELLING" : "Alishan"
		},
		{
			"ID" : "713300",
			"PARENT_ID" : "710000",
			"DISTRICT_NAME" : "屏东县",
			"SPELLING" : "Pingtung"
		},
		{
			"ID" : "713301",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "屏东市",
			"SPELLING" : "Pingdong"
		},
		{
			"ID" : "713321",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "潮州镇",
			"SPELLING" : "Chaozhou"
		},
		{
			"ID" : "713322",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "东港镇",
			"SPELLING" : "Donggang"
		},
		{
			"ID" : "713323",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "恒春镇",
			"SPELLING" : "Hengchun"
		},
		{
			"ID" : "713324",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "万丹乡",
			"SPELLING" : "Wandan"
		},
		{
			"ID" : "713325",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "长治乡",
			"SPELLING" : "Changzhi"
		},
		{
			"ID" : "713326",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "麟洛乡",
			"SPELLING" : "Linluo"
		},
		{
			"ID" : "713327",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "九如乡",
			"SPELLING" : "Jiuru"
		},
		{
			"ID" : "713328",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "里港乡",
			"SPELLING" : "Ligang"
		},
		{
			"ID" : "713329",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "盐埔乡",
			"SPELLING" : "Yanpu"
		},
		{
			"ID" : "713330",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "高树乡",
			"SPELLING" : "Gaoshu"
		},
		{
			"ID" : "713331",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "万峦乡",
			"SPELLING" : "Wanluan"
		},
		{
			"ID" : "713332",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "内埔乡",
			"SPELLING" : "Napu"
		},
		{
			"ID" : "713333",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "竹田乡",
			"SPELLING" : "Zhutian"
		},
		{
			"ID" : "713334",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "新埤乡",
			"SPELLING" : "Xinpi"
		},
		{
			"ID" : "713335",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "枋寮乡",
			"SPELLING" : "Fangliao"
		},
		{
			"ID" : "713336",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "新园乡",
			"SPELLING" : "Xinyuan"
		},
		{
			"ID" : "713337",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "崁顶乡",
			"SPELLING" : "Kanding"
		},
		{
			"ID" : "713338",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "林边乡",
			"SPELLING" : "Linbian"
		},
		{
			"ID" : "713339",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "南州乡",
			"SPELLING" : "Nanzhou"
		},
		{
			"ID" : "713340",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "佳冬乡",
			"SPELLING" : "Jiadong"
		},
		{
			"ID" : "713341",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "琉球乡",
			"SPELLING" : "Liuqiu"
		},
		{
			"ID" : "713342",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "车城乡",
			"SPELLING" : "Checheng"
		},
		{
			"ID" : "713343",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "满州乡",
			"SPELLING" : "Manzhou"
		},
		{
			"ID" : "713344",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "枋山乡",
			"SPELLING" : "Fangshan"
		},
		{
			"ID" : "713345",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "三地门乡",
			"SPELLING" : "Sandimen"
		},
		{
			"ID" : "713346",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "雾台乡",
			"SPELLING" : "Wutai"
		},
		{
			"ID" : "713347",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "玛家乡",
			"SPELLING" : "Majia"
		},
		{
			"ID" : "713348",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "泰武乡",
			"SPELLING" : "Taiwu"
		},
		{
			"ID" : "713349",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "来义乡",
			"SPELLING" : "Laiyi"
		},
		{
			"ID" : "713350",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "春日乡",
			"SPELLING" : "Chunri"
		},
		{
			"ID" : "713351",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "狮子乡",
			"SPELLING" : "Shizi"
		},
		{
			"ID" : "713352",
			"PARENT_ID" : "713300",
			"DISTRICT_NAME" : "牡丹乡",
			"SPELLING" : "Mudan"
		},
		{
			"ID" : "713400",
			"PARENT_ID" : "710000",
			"DISTRICT_NAME" : "台东县",
			"SPELLING" : "Taitung"
		},
		{
			"ID" : "713401",
			"PARENT_ID" : "713400",
			"DISTRICT_NAME" : "台东市",
			"SPELLING" : "Taidong"
		},
		{
			"ID" : "713421",
			"PARENT_ID" : "713400",
			"DISTRICT_NAME" : "成功镇",
			"SPELLING" : "Chenggong"
		},
		{
			"ID" : "713422",
			"PARENT_ID" : "713400",
			"DISTRICT_NAME" : "关山镇",
			"SPELLING" : "Guanshan"
		},
		{
			"ID" : "713423",
			"PARENT_ID" : "713400",
			"DISTRICT_NAME" : "卑南乡",
			"SPELLING" : "Beinan"
		},
		{
			"ID" : "713424",
			"PARENT_ID" : "713400",
			"DISTRICT_NAME" : "鹿野乡",
			"SPELLING" : "Luye"
		},
		{
			"ID" : "713425",
			"PARENT_ID" : "713400",
			"DISTRICT_NAME" : "池上乡",
			"SPELLING" : "Chishang"
		},
		{
			"ID" : "713426",
			"PARENT_ID" : "713400",
			"DISTRICT_NAME" : "东河乡",
			"SPELLING" : "Donghe"
		},
		{
			"ID" : "713427",
			"PARENT_ID" : "713400",
			"DISTRICT_NAME" : "长滨乡",
			"SPELLING" : "Changbin"
		},
		{
			"ID" : "713428",
			"PARENT_ID" : "713400",
			"DISTRICT_NAME" : "太麻里乡",
			"SPELLING" : "Taimali"
		},
		{
			"ID" : "713429",
			"PARENT_ID" : "713400",
			"DISTRICT_NAME" : "大武乡",
			"SPELLING" : "Dawu"
		},
		{
			"ID" : "713430",
			"PARENT_ID" : "713400",
			"DISTRICT_NAME" : "绿岛乡",
			"SPELLING" : "Lvdao"
		},
		{
			"ID" : "713431",
			"PARENT_ID" : "713400",
			"DISTRICT_NAME" : "海端乡",
			"SPELLING" : "Haiduan"
		},
		{
			"ID" : "713432",
			"PARENT_ID" : "713400",
			"DISTRICT_NAME" : "延平乡",
			"SPELLING" : "Yanping"
		},
		{
			"ID" : "713433",
			"PARENT_ID" : "713400",
			"DISTRICT_NAME" : "金峰乡",
			"SPELLING" : "Jinfeng"
		},
		{
			"ID" : "713434",
			"PARENT_ID" : "713400",
			"DISTRICT_NAME" : "达仁乡",
			"SPELLING" : "Daren"
		},
		{
			"ID" : "713435",
			"PARENT_ID" : "713400",
			"DISTRICT_NAME" : "兰屿乡",
			"SPELLING" : "Lanyu"
		},
		{
			"ID" : "713500",
			"PARENT_ID" : "710000",
			"DISTRICT_NAME" : "花莲县",
			"SPELLING" : "Hualien"
		},
		{
			"ID" : "713501",
			"PARENT_ID" : "713500",
			"DISTRICT_NAME" : "花莲市",
			"SPELLING" : "Hualian"
		},
		{
			"ID" : "713521",
			"PARENT_ID" : "713500",
			"DISTRICT_NAME" : "凤林镇",
			"SPELLING" : "Fenglin"
		},
		{
			"ID" : "713522",
			"PARENT_ID" : "713500",
			"DISTRICT_NAME" : "玉里镇",
			"SPELLING" : "Yuli"
		},
		{
			"ID" : "713523",
			"PARENT_ID" : "713500",
			"DISTRICT_NAME" : "新城乡",
			"SPELLING" : "Xincheng"
		},
		{
			"ID" : "713524",
			"PARENT_ID" : "713500",
			"DISTRICT_NAME" : "吉安乡",
			"SPELLING" : "Jian"
		},
		{
			"ID" : "713525",
			"PARENT_ID" : "713500",
			"DISTRICT_NAME" : "寿丰乡",
			"SPELLING" : "Shoufeng"
		},
		{
			"ID" : "713526",
			"PARENT_ID" : "713500",
			"DISTRICT_NAME" : "光复乡",
			"SPELLING" : "Guangfu"
		},
		{
			"ID" : "713527",
			"PARENT_ID" : "713500",
			"DISTRICT_NAME" : "丰滨乡",
			"SPELLING" : "Fengbin"
		},
		{
			"ID" : "713528",
			"PARENT_ID" : "713500",
			"DISTRICT_NAME" : "瑞穗乡",
			"SPELLING" : "Ruisui"
		},
		{
			"ID" : "713529",
			"PARENT_ID" : "713500",
			"DISTRICT_NAME" : "富里乡",
			"SPELLING" : "Fuli"
		},
		{
			"ID" : "713530",
			"PARENT_ID" : "713500",
			"DISTRICT_NAME" : "秀林乡",
			"SPELLING" : "Xiulin"
		},
		{
			"ID" : "713531",
			"PARENT_ID" : "713500",
			"DISTRICT_NAME" : "万荣乡",
			"SPELLING" : "Wanrong"
		},
		{
			"ID" : "713532",
			"PARENT_ID" : "713500",
			"DISTRICT_NAME" : "卓溪乡",
			"SPELLING" : "Zhuoxi"
		},
		{
			"ID" : "713600",
			"PARENT_ID" : "710000",
			"DISTRICT_NAME" : "澎湖县",
			"SPELLING" : "Penghu"
		},
		{
			"ID" : "713601",
			"PARENT_ID" : "713600",
			"DISTRICT_NAME" : "马公市",
			"SPELLING" : "Magong"
		},
		{
			"ID" : "713621",
			"PARENT_ID" : "713600",
			"DISTRICT_NAME" : "湖西乡",
			"SPELLING" : "Huxi"
		},
		{
			"ID" : "713622",
			"PARENT_ID" : "713600",
			"DISTRICT_NAME" : "白沙乡",
			"SPELLING" : "Baisha"
		},
		{
			"ID" : "713623",
			"PARENT_ID" : "713600",
			"DISTRICT_NAME" : "西屿乡",
			"SPELLING" : "Xiyu"
		},
		{
			"ID" : "713624",
			"PARENT_ID" : "713600",
			"DISTRICT_NAME" : "望安乡",
			"SPELLING" : "Wangan"
		},
		{
			"ID" : "713625",
			"PARENT_ID" : "713600",
			"DISTRICT_NAME" : "七美乡",
			"SPELLING" : "Qimei"
		},
		{
			"ID" : "713700",
			"PARENT_ID" : "710000",
			"DISTRICT_NAME" : "金门县",
			"SPELLING" : "Jinmen"
		},
		{
			"ID" : "713701",
			"PARENT_ID" : "713700",
			"DISTRICT_NAME" : "金城镇",
			"SPELLING" : "Jincheng"
		},
		{
			"ID" : "713702",
			"PARENT_ID" : "713700",
			"DISTRICT_NAME" : "金湖镇",
			"SPELLING" : "Jinhu"
		},
		{
			"ID" : "713703",
			"PARENT_ID" : "713700",
			"DISTRICT_NAME" : "金沙镇",
			"SPELLING" : "Jinsha"
		},
		{
			"ID" : "713704",
			"PARENT_ID" : "713700",
			"DISTRICT_NAME" : "金宁乡",
			"SPELLING" : "Jinning"
		},
		{
			"ID" : "713705",
			"PARENT_ID" : "713700",
			"DISTRICT_NAME" : "烈屿乡",
			"SPELLING" : "Lieyu"
		},
		{
			"ID" : "713706",
			"PARENT_ID" : "713700",
			"DISTRICT_NAME" : "乌丘乡",
			"SPELLING" : "Wuqiu"
		},
		{
			"ID" : "713800",
			"PARENT_ID" : "710000",
			"DISTRICT_NAME" : "连江县",
			"SPELLING" : "Lienchiang"
		},
		{
			"ID" : "713801",
			"PARENT_ID" : "713800",
			"DISTRICT_NAME" : "南竿乡",
			"SPELLING" : "Nangan"
		},
		{
			"ID" : "713802",
			"PARENT_ID" : "713800",
			"DISTRICT_NAME" : "北竿乡",
			"SPELLING" : "Beigan"
		},
		{
			"ID" : "713803",
			"PARENT_ID" : "713800",
			"DISTRICT_NAME" : "莒光乡",
			"SPELLING" : "Juguang"
		},
		{
			"ID" : "713804",
			"PARENT_ID" : "713800",
			"DISTRICT_NAME" : "东引乡",
			"SPELLING" : "Dongyin"
		},
		{
			"ID" : "810000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "香港特别行政区",
			"SPELLING" : "Hong Kong"
		},
		{
			"ID" : "810100",
			"PARENT_ID" : "810000",
			"DISTRICT_NAME" : "香港岛",
			"SPELLING" : "Hong Kong Island"
		},
		{
			"ID" : "810101",
			"PARENT_ID" : "810100",
			"DISTRICT_NAME" : "中西区",
			"SPELLING" : "Central and Western District"
		},
		{
			"ID" : "810102",
			"PARENT_ID" : "810100",
			"DISTRICT_NAME" : "湾仔区",
			"SPELLING" : "Wan Chai District"
		},
		{
			"ID" : "810103",
			"PARENT_ID" : "810100",
			"DISTRICT_NAME" : "东区",
			"SPELLING" : "Eastern District"
		},
		{
			"ID" : "810104",
			"PARENT_ID" : "810100",
			"DISTRICT_NAME" : "南区",
			"SPELLING" : "Southern District"
		},
		{
			"ID" : "810200",
			"PARENT_ID" : "810000",
			"DISTRICT_NAME" : "九龙",
			"SPELLING" : "Kowloon"
		},
		{
			"ID" : "810201",
			"PARENT_ID" : "810200",
			"DISTRICT_NAME" : "油尖旺区",
			"SPELLING" : "Yau Tsim Mong"
		},
		{
			"ID" : "810202",
			"PARENT_ID" : "810200",
			"DISTRICT_NAME" : "深水埗区",
			"SPELLING" : "Sham Shui Po"
		},
		{
			"ID" : "810203",
			"PARENT_ID" : "810200",
			"DISTRICT_NAME" : "九龙城区",
			"SPELLING" : "Jiulongcheng"
		},
		{
			"ID" : "810204",
			"PARENT_ID" : "810200",
			"DISTRICT_NAME" : "黄大仙区",
			"SPELLING" : "Wong Tai Sin"
		},
		{
			"ID" : "810205",
			"PARENT_ID" : "810200",
			"DISTRICT_NAME" : "观塘区",
			"SPELLING" : "Kwun Tong"
		},
		{
			"ID" : "810300",
			"PARENT_ID" : "810000",
			"DISTRICT_NAME" : "新界",
			"SPELLING" : "New Territories"
		},
		{
			"ID" : "810301",
			"PARENT_ID" : "810300",
			"DISTRICT_NAME" : "荃湾区",
			"SPELLING" : "Tsuen Wan"
		},
		{
			"ID" : "810302",
			"PARENT_ID" : "810300",
			"DISTRICT_NAME" : "屯门区",
			"SPELLING" : "Tuen Mun"
		},
		{
			"ID" : "810303",
			"PARENT_ID" : "810300",
			"DISTRICT_NAME" : "元朗区",
			"SPELLING" : "Yuen Long"
		},
		{
			"ID" : "810304",
			"PARENT_ID" : "810300",
			"DISTRICT_NAME" : "北区",
			"SPELLING" : "North District"
		},
		{
			"ID" : "810305",
			"PARENT_ID" : "810300",
			"DISTRICT_NAME" : "大埔区",
			"SPELLING" : "Tai Po"
		},
		{
			"ID" : "810306",
			"PARENT_ID" : "810300",
			"DISTRICT_NAME" : "西贡区",
			"SPELLING" : "Sai Kung"
		},
		{
			"ID" : "810307",
			"PARENT_ID" : "810300",
			"DISTRICT_NAME" : "沙田区",
			"SPELLING" : "Sha Tin"
		},
		{
			"ID" : "810308",
			"PARENT_ID" : "810300",
			"DISTRICT_NAME" : "葵青区",
			"SPELLING" : "Kwai Tsing"
		},
		{
			"ID" : "810309",
			"PARENT_ID" : "810300",
			"DISTRICT_NAME" : "离岛区",
			"SPELLING" : "Outlying Islands"
		},
		{
			"ID" : "820000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "澳门特别行政区",
			"SPELLING" : "Macau"
		},
		{
			"ID" : "820100",
			"PARENT_ID" : "820000",
			"DISTRICT_NAME" : "澳门半岛",
			"SPELLING" : "MacauPeninsula"
		},
		{
			"ID" : "820101",
			"PARENT_ID" : "820100",
			"DISTRICT_NAME" : "花地玛堂区",
			"SPELLING" : "Nossa Senhora de Fatima"
		},
		{
			"ID" : "820102",
			"PARENT_ID" : "820100",
			"DISTRICT_NAME" : "圣安多尼堂区",
			"SPELLING" : "Santo Antonio"
		},
		{
			"ID" : "820103",
			"PARENT_ID" : "820100",
			"DISTRICT_NAME" : "大堂区",
			"SPELLING" : "S?"
		},
		{
			"ID" : "820104",
			"PARENT_ID" : "820100",
			"DISTRICT_NAME" : "望德堂区",
			"SPELLING" : "Sao Lazaro"
		},
		{
			"ID" : "820105",
			"PARENT_ID" : "820100",
			"DISTRICT_NAME" : "风顺堂区",
			"SPELLING" : "Sao Lourenco"
		},
		{
			"ID" : "820200",
			"PARENT_ID" : "820000",
			"DISTRICT_NAME" : "氹仔岛",
			"SPELLING" : "Taipa"
		},
		{
			"ID" : "820201",
			"PARENT_ID" : "820200",
			"DISTRICT_NAME" : "嘉模堂区",
			"SPELLING" : "Our Lady Of Carmels Parish"
		},
		{
			"ID" : "820300",
			"PARENT_ID" : "820000",
			"DISTRICT_NAME" : "路环岛",
			"SPELLING" : "Coloane"
		},
		{
			"ID" : "820301",
			"PARENT_ID" : "820300",
			"DISTRICT_NAME" : "圣方济各堂区",
			"SPELLING" : "St Francis Xaviers Parish"
		},
		{
			"ID" : "900000",
			"PARENT_ID" : "100000",
			"DISTRICT_NAME" : "钓鱼岛",
			"SPELLING" : "DiaoyuDao"
		},
		{
			"ID" : "900001",
			"PARENT_ID" : "320500",
			"DISTRICT_NAME" : "工业园区",
			"SPELLING" : "Gongyeyuanqu"
		}
	];
