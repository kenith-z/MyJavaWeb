package cn.gzccc.util;

public class ChineseSpelling {
	
	private static int[] pyValue = new int[] {
		-20319, -20317, -20304, -20295,
		-20292, -20283, -20265, -20257,
		-20242, -20230, -20051, -20036,
		-20032, -20026, -20002, -19990,
		-19986, -19982, -19976, -19805,
		-19784, -19775, -19774, -19763,
		-19756, -19751, -19746, -19741,
		-19739, -19728, -19725, -19715,
		-19540, -19531, -19525, -19515,
		-19500, -19484, -19479, -19467,
		-19289, -19288, -19281, -19275,
		-19270, -19263, -19261, -19249,
		-19243, -19242, -19238, -19235,
		-19227, -19224, -19218, -19212,
		-19038, -19023, -19018, -19006,
		-19003, -18996, -18977, -18961,
		-18952, -18783, -18774, -18773,
		-18763, -18756, -18741, -18735,
		-18731, -18722, -18710, -18697,
		-18696, -18526, -18518, -18501,
		-18490, -18478, -18463, -18448,
		-18447, -18446, -18239, -18237,
		-18231, -18220, -18211, -18201,
		-18184, -18183, -18181, -18012,
		-17997, -17988, -17970, -17964,
		-17961, -17950, -17947, -17931,
		-17928, -17922, -17759, -17752,
		-17733, -17730, -17721, -17703,
		-17701, -17697, -17692, -17683,
		-17676, -17496, -17487, -17482,
		-17468, -17454, -17433, -17427,
		-17417, -17202, -17185, -16983,
		-16970, -16942, -16915, -16733,
		-16708, -16706, -16689, -16664,
		-16657, -16647, -16474, -16470,
		-16465, -16459, -16452, -16448,
		-16433, -16429, -16427, -16423,
		-16419, -16412, -16407, -16403,
		-16401, -16393, -16220, -16216,
		-16212, -16205, -16202, -16187,
		-16180, -16171, -16169, -16158,
		-16155, -15959, -15958, -15944,
		-15933, -15920, -15915, -15903,
		-15889, -15878, -15707, -15701,
		-15681, -15667, -15661, -15659,
		-15652, -15640, -15631, -15625,
		-15454, -15448, -15436, -15435,
		-15419, -15416, -15408, -15394,
		-15385, -15377, -15375, -15369,
		-15363, -15362, -15183, -15180,
		-15165, -15158, -15153, -15150,
		-15149, -15144, -15143, -15141,
		-15140, -15139, -15128, -15121,
		-15119, -15117, -15110, -15109,
		-14941, -14937, -14933, -14930,
		-14929, -14928, -14926, -14922,
		-14921, -14914, -14908, -14902,
		-14894, -14889, -14882, -14873,
		-14871, -14857, -14678, -14674,
		-14670, -14668, -14663, -14654,
		-14645, -14630, -14594, -14429,
		-14407, -14399, -14384, -14379,
		-14368, -14355, -14353, -14345,
		-14170, -14159, -14151, -14149,
		-14145, -14140, -14137, -14135,
		-14125, -14123, -14122, -14112,
		-14109, -14099, -14097, -14094,
		-14092, -14090, -14087, -14083,
		-13917, -13914, -13910, -13907,
		-13906, -13905, -13896, -13894,
		-13878, -13870, -13859, -13847,
		-13831, -13658, -13611, -13601,
		-13406, -13404, -13400, -13398,
		-13395, -13391, -13387, -13383,
		-13367, -13359, -13356, -13343,
		-13340, -13329, -13326, -13318,
		-13147, -13138, -13120, -13107,
		-13096, -13095, -13091, -13076,
		-13068, -13063, -13060, -12888,
		-12875, -12871, -12860, -12858,
		-12852, -12849, -12838, -12831,
		-12829, -12812, -12802, -12607,
		-12597, -12594, -12585, -12556,
		-12359, -12346, -12320, -12300,
		-12120, -12099, -12089, -12074,
		-12067, -12058, -12039, -11867,
		-11861, -11847, -11831, -11798,
		-11781, -11604, -11589, -11536,
		-11358, -11340, -11339, -11324,
		-11303, -11097, -11077, -11067,
		-11055, -11052, -11045, -11041,
		-11038, -11024, -11020, -11019,
		-11018, -11014, -10838, -10832,
		-10815, -10800, -10790, -10780,
		-10764, -10587, -10544, -10533,
		-10519, -10331, -10329, -10328,
		-10322, -10315, -10309, -10307,
		-10296, -10281, -10274, -10270,
		-10262, -10260, -10256, -10254
	};
	
	private static String[] pyStr = new String[] {
		"a", "ai", "an", "ang",
		"ao", "ba", "bai", "ban",
		"bang", "bao", "bei", "ben",
		"beng", "bi", "bian", "biao",
		"bie", "bin", "bing", "bo",
		"bu", "ca", "cai", "can",
		"cang", "cao", "ce", "ceng",
		"cha", "chai", "chan", "chang",
		"chao", "che", "chen", "cheng",
		"chi", "chong", "chou", "chu",
		"chuai", "chuan", "chuang", "chui",
		"chun", "chuo", "ci", "cong",
		"cou", "cu", "cuan", "cui",
		"cun", "cuo", "da", "dai",
		"dan", "dang", "dao", "de",
		"deng", "di", "dian", "diao",
		"die", "ding", "diu", "dong",
		"dou", "du", "duan", "dui",
		"dun", "duo", "e", "en",
		"er", "fa", "fan", "fang",
		"fei", "fen", "feng", "fo",
		"fou", "fu", "ga", "gai",
		"gan", "gang", "gao", "ge",
		"gei", "gen", "geng", "gong",
		"gou", "gu", "gua", "guai",
		"guan", "guang", "gui", "gun",
		"guo", "ha", "hai", "han",
		"hang", "hao", "he", "hei",
		"hen", "heng", "hong", "hou",
		"hu", "hua", "huai", "huan",
		"huang", "hui", "hun", "huo",
		"ji", "jia", "jian", "jiang",
		"jiao", "jie", "jin", "jing",
		"jiong", "jiu", "ju", "juan",
		"jue", "jun", "ka", "kai",
		"kan", "kang", "kao", "ke",
		"ken", "keng", "kong", "kou",
		"ku", "kua", "kuai", "kuan",
		"kuang", "kui", "kun", "kuo",
		"la", "lai", "lan", "lang",
		"lao", "le", "lei", "leng",
		"li", "lia", "lian", "liang",
		"liao", "lie", "lin", "ling",
		"liu", "long", "lou", "lu",
		"lv", "luan", "lue", "lun",
		"luo", "ma", "mai", "man",
		"mang", "mao", "me", "mei",
		"men", "meng", "mi", "mian",
		"miao", "mie", "min", "ming",
		"miu", "mo", "mou", "mu",
		"na", "nai", "nan", "nang",
		"nao", "ne", "nei", "nen",
		"neng", "ni", "nian", "niang",
		"niao", "nie", "nin", "ning",
		"niu", "nong", "nu", "nv",
		"nuan", "nue", "nuo", "o",
		"ou", "pa", "pai", "pan",
		"pang", "pao", "pei", "pen",
		"peng", "pi", "pian", "piao",
		"pie", "pin", "ping", "po",
		"pu", "qi", "qia", "qian",
		"qiang", "qiao", "qie", "qin",
		"qing", "qiong", "qiu", "qu",
		"quan", "que", "qun", "ran",
		"rang", "rao", "re", "ren",
		"reng", "ri", "rong", "rou",
		"ru", "ruan", "rui", "run",
		"ruo", "sa", "sai", "san",
		"sang", "sao", "se", "sen",
		"seng", "sha", "shai", "shan",
		"shang", "shao", "she", "shen",
		"sheng", "shi", "shou", "shu",
		"shua", "shuai", "shuan", "shuang",
		"shui", "shun", "shuo", "si",
		"song", "sou", "su", "suan",
		"sui", "sun", "suo", "ta",
		"tai", "tan", "tang", "tao",
		"te", "teng", "ti", "tian",
		"tiao", "tie", "ting", "tong",
		"tou", "tu", "tuan", "tui",
		"tun", "tuo", "wa", "wai",
		"wan", "wang", "wei", "wen",
		"weng", "wo", "wu", "xi",
		"xia", "xian", "xiang", "xiao",
		"xie", "xin", "xing", "xiong",
		"xiu", "xu", "xuan", "xue",
		"xun", "ya", "yan", "yang",
		"yao", "ye", "yi", "yin",
		"ying", "yo", "yong", "you",
		"yu", "yuan", "yue", "yun",
		"za", "zai", "zan", "zang",
		"zao", "ze", "zei", "zen",
		"zeng", "zha", "zhai", "zhan",
		"zhang", "zhao", "zhe", "zhen",
		"zheng", "zhi", "zhong", "zhou",
		"zhu", "zhua", "zhuai", "zhuan",
		"zhuang", "zhui", "zhun", "zhuo",
		"zi", "zong", "zou", "zu",
		"zuan", "zui", "zun", "zuo",
	};
	
	private static int getChsAscii(String chs) {
		int asc = 0;
		try {
			byte[] bytes = chs.getBytes("GBK");
			if (bytes==null || bytes.length>2 || bytes.length<=0) {
				throw new RuntimeException("illegal source string");
			}
			//英文字符
			if (bytes.length==1) {
				asc = bytes[0];
			}
			//中文字符
			if (bytes.length==2) {
				int hightByte = 256 + bytes[0];
				int lowByte = 256 + bytes[1];
				asc = (256 * hightByte + lowByte) - 256 * 256;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return asc;
	}
	
	private static String convert(String str) {
		String result = null;
		int ascii = getChsAscii(str);
		if (ascii>0 && ascii<160) {
			result = String.valueOf((char) ascii);
		} else {
			for (int i=(pyValue.length-1); i>=0; i--) {
				if (pyValue[i]<=ascii) {
					result = pyStr[i];
					break;
				}
			}
		}
		return result;
	}
	
	public static String getSpelling(String chs) {
		if(chs==null || chs.trim().length()==0) return chs;
		String key, value;
		StringBuilder buffer = new StringBuilder();
		for (int i=0; i<chs.length(); i++) {
			key = chs.substring(i, i+1);
			int len = key.getBytes().length;
			if (len==2 || len==3) {
				value = (String)convert(key);
				if (value==null) {
					if("—".equals(key)){
						value = "-";
					}else if("（".equals(key)){
						value = "(";
					}else if("）".equals(key)){
						value = ")";
					}else if("·".equals(key)){
						value = ".";
					}else if("、".equals(key)){
						value = "_";
					}else if("０".equals(key)){
						value = "0";
					}else if("１".equals(key)){
						value = "1";
					}else if("２".equals(key)){
						value = "2";
					}else if("３".equals(key)){
						value = "3";
					}else if("４".equals(key)){
						value = "4";
					}else if("５".equals(key)){
						value = "5";
					}else if("６".equals(key)){
						value = "6";
					}else if("７".equals(key)){
						value = "7";
					}else if("８".equals(key)){
						value = "8";
					}else if("９".equals(key)){
						value = "9";
					}else if("Ａ".equals(key)){
						value = "A";
					}else if("Ｂ".equals(key)){
						value = "B";
					}else if("Ｃ".equals(key)){
						value = "C";
					}else if("Ｄ".equals(key)){
						value = "D";
					}else if("Ｅ".equals(key)){
						value = "E";
					}else if("Ｆ".equals(key)){
						value = "F";
					}else if("Ｇ".equals(key)){
						value = "G";
					}else if("Ｈ".equals(key)){
						value = "H";
					}else if("Ｉ".equals(key)){
						value = "I";
					}else if("Ｊ".equals(key)){
						value = "J";
					}else if("Ｋ".equals(key)){
						value = "K";
					}else if("Ｌ".equals(key)){
						value = "L";
					}else if("Ｍ".equals(key)){
						value = "M";
					}else if("Ｎ".equals(key)){
						value = "N";
					}else if("Ｏ".equals(key)){
						value = "O";
					}else if("Ｐ".equals(key)){
						value = "P";
					}else if("Ｑ".equals(key)){
						value = "Q";
					}else if("Ｒ".equals(key)){
						value = "R";
					}else if("Ｓ".equals(key)){
						value = "S";
					}else if("Ｔ".equals(key)){
						value = "T";
					}else if("Ｕ".equals(key)){
						value = "U";
					}else if("Ｖ".equals(key)){
						value = "V";
					}else if("Ｗ".equals(key)){
						value = "W";
					}else if("Ｘ".equals(key)){
						value = "X";
					}else if("Ｙ".equals(key)){
						value = "Y";
					}else if("Ｚ".equals(key)){
						value = "Z";
					}else if("ａ".equals(key)){
						value = "a";
					}else if("ｂ".equals(key)){
						value = "b";
					}else if("ｃ".equals(key)){
						value = "c";
					}else if("ｄ".equals(key)){
						value = "d";
					}else if("ｅ".equals(key)){
						value = "e";
					}else if("ｆ".equals(key)){
						value = "f";
					}else if("ｇ".equals(key)){
						value = "g";
					}else if("ｈ".equals(key)){
						value = "h";
					}else if("ｉ".equals(key)){
						value = "i";
					}else if("ｊ".equals(key)){
						value = "j";
					}else if("ｋ".equals(key)){
						value = "k";
					}else if("ｌ".equals(key)){
						value = "l";
					}else if("ｍ".equals(key)){
						value = "m";
					}else if("ｎ".equals(key)){
						value = "n";
					}else if("ｏ".equals(key)){
						value = "o";
					}else if("ｐ".equals(key)){
						value = "p";
					}else if("ｑ".equals(key)){
						value = "q";
					}else if("ｒ".equals(key)){
						value = "r";
					}else if("ｓ".equals(key)){
						value = "s";
					}else if("ｔ".equals(key)){
						value = "t";
					}else if("ｕ".equals(key)){
						value = "u";
					}else if("ｖ".equals(key)){
						value = "v";
					}else if("ｗ".equals(key)){
						value = "w";
					}else if("ｘ".equals(key)){
						value = "x";
					}else if("ｙ".equals(key)){
						value = "y";
					}else if("ｚ".equals(key)){
						value = "z";
					}else{
						value = "unknown";
					}
				}
			} else {
				value = key;
			}
			if(value.length()==1){
				value = value.toUpperCase();
			}else if(value.length()>1){
				value = String.valueOf(value.charAt(0)).toUpperCase() + value.substring(1);
			}
			buffer.append(value);
		}
		return buffer.toString();
	}

}
