package com.jack.test.soap;
import org.apache.axis.client.Service;
import org.apache.axis.client.Call;
import org.apache.axis.utils.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
/*
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
*/

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;

import java.rmi.RemoteException;
import java.util.*;


/**
 * @description 三库一平台 省内企业和人员信息对接接口
 * @author: zq
 * @create: 2020-12-11 15:25
 **/
/*@Controller
@RequestMapping("/keyProjectsInProvincePlatform")*/
public class KeyProjectsThreeLibOnePlatformInProvinceController {
	/**
	 * 省内企业信息和人员信息请求地址
	 */
	private static final String WDSL_URL = "http://210.76.74.217/GDCICServiceCenter/GovermentService.svc?wsdl";
	/**
	 *namespace
	 */
	private static final String NAMESPACE ="http://tempuri.org/";
	/**
	 * 获取省内企业信息方法
	 */
	private static final String METHOD_NAME = "GetGdEnterpriseInfo";
	/**
	 * 获取省内企业信息Action
	 */
	private static final String ACTION_SOAP = "http://tempuri.org/IGovermentService/GetGdEnterpriseInfo";
	/**
	 * 获取省内人员从业详细信息方法
	 */
	private static final String PERSON_METHOD_NAME = "GetGdPersonInfo";
	/**
	 * 获取省内人员从业详细信息ACTION
	 */
	private static final String PERSON_ACTION_SOAP = "http://tempuri.org/IGovermentService/GetGdPersonInfo";


	/**
	 * 进粤企业及人员信息数据共享接口地址
	 */
	private static final String ENTER_WDSL_URL = "http://210.76.74.212/IntoGDService/DataExchangeService.svc?wsdl";
	/**
	 *namespace
	 */
	private static final String ENTER_NAMESPACE ="http://www.gdcic.net/IntoGDService";
	/**
	 * 省外进粤企业备案信息方法名称
	 */
	private static final String ENTER_PRISE_INFO_METHOD_NAME = "GetEnterpriseInfo";
	/**
	 * 省外进粤企业备案信息Action
	 */
	private static final String ENTER_PRISE_INFO_ACTION ="http://www.gdcic.net/IntoGDService/DataExchangeService" +
			"/GetEnterpriseInfo";
	/**
	 * 省外进粤企业人员备案信息方法
	 */
	private static final String PERSON_INFO_METHOD_NAME = "GetPersonInfo";
	/**
	 * 省外进粤企业人员备案Action
	 */
	private static final String PERSON_INFO_ACTION= "http://www.gdcic.net/IntoGDService/DataExchangeService/GetPersonInfo";
	/**
	 * 用户名
	 */
	private static final String USERNAME = "006939799";
	/**
	 * 密码
	 */
	private static final String PASSWORD = "D42WA7Z7Z";

	public static void main(String[] args) throws DocumentException, RemoteException, ServiceException {
		KeyProjectsThreeLibOnePlatformInProvinceController platform =
				new KeyProjectsThreeLibOnePlatformInProvinceController();
		//省内测试
		//Map<String, Object> map = platform.GetGdEnterpriseInfo("195174149");
		//Map<String, Object> map = platform.GetGdPersonInfo("440711198001063915");
		//省外测试
		Map<String, Object> map = platform.GetEnterpriseInfo("736578454");
		System.out.println("map:"+map);
	}

	/**
	 *获取省内企业详细信息
	 * @param orgCode 组织机构代码 --》对应的是单位信息表中统一社会信用代码的9-17位
	 * @return
	 * @throws ServiceException
	 * @throws RemoteException
	 * @throws DocumentException
	 */
	/*@RequestMapping("/GetGdEnterpriseInfo")
	@ResponseBody*/
	public  Map<String,Object> GetGdEnterpriseInfo(String orgCode) throws ServiceException, RemoteException,
			DocumentException {
		String result = callWebService(orgCode, WDSL_URL, ACTION_SOAP, METHOD_NAME,NAMESPACE);
		if (StringUtils.isEmpty(result)){
			HashMap<String, Object> errorMap = new HashMap<>();
			errorMap.put("message","返回数据为空！");
			return errorMap;
		}
		//解析数据
		Map<String, Object> map = parseGdEnterpriseInfo("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + result);
		return map;
	}

	/**
	 * 获取省内企业人员信息详情接口
	 * @param idNumber 人员身份证号码
	 * @return
	 * @throws RemoteException
	 * @throws ServiceException
	 */
	/*@RequestMapping("/GetGdPersonInfo")
	@ResponseBody*/
	public  Map<String,Object> GetGdPersonInfo(String idNumber) throws RemoteException, ServiceException,
			DocumentException {
		String result = callWebService(idNumber, WDSL_URL, PERSON_ACTION_SOAP, PERSON_METHOD_NAME,NAMESPACE);
		if (StringUtils.isEmpty(result)){
			HashMap<String, Object> errorMap = new HashMap<>();
			errorMap.put("message","返回数据为空！");
			return errorMap;
		}
		Map<String, Object> map = parseGetGdPersonInfo("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + result);
		return map;
	}

	/**
	 *获取省外进粤企业详细信息
	 * @param orgCode 组织机构代码 --》对应的是单位信息表中统一社会信用代码的9-17位
	 * @return
	 * @throws ServiceException
	 * @throws RemoteException
	 * @throws DocumentException
	 */
	/*@RequestMapping("/GetEnterpriseInfo")
	@ResponseBody*/
	public Map<String,Object> GetEnterpriseInfo(String orgCode) throws ServiceException, RemoteException,
			DocumentException {
		String result = callWebService(orgCode, ENTER_WDSL_URL,
				ENTER_PRISE_INFO_ACTION,ENTER_PRISE_INFO_METHOD_NAME,ENTER_NAMESPACE);
		if (StringUtils.isEmpty(result)){
			HashMap<String, Object> errorMap = new HashMap<>();
			errorMap.put("message","返回数据为空！");
			return errorMap;
		}
		//解析数据
		Map<String, Object> map = parseEnterpriseInfo("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + result);
		return map;
	}

	/**
	 * 获取省内企业人员信息详情接口
	 * @param idNumber 人员身份证号码
	 * @return
	 * @throws RemoteException
	 * @throws ServiceException
	 */
	/*@RequestMapping("/GetPersonInfo")
	@ResponseBody*/
	public  Map<String,Object> GetPersonInfo(String idNumber) throws RemoteException, ServiceException,
			DocumentException {
		String result = callWebService(idNumber, ENTER_WDSL_URL,
				PERSON_INFO_ACTION, PERSON_INFO_METHOD_NAME,ENTER_NAMESPACE);
		if (StringUtils.isEmpty(result)){
			HashMap<String, Object> errorMap = new HashMap<>();
			errorMap.put("message","返回数据为空！");
			return errorMap;
		}
		Map<String, Object> map = parseGetPersonInfo("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + result);
		return map;
	}


	/**
	 * 调用webservice接口
	 * @param param 传入查询的参数
	 * @param url   webservice接口地址
	 * @param action 调用地址的action
	 * @param methodName 调用方法名称
	 * @return
	 * @throws RemoteException
	 * @throws ServiceException
	 */
	public static String callWebService(String param,String url,String action,String methodName,String nameSpace) throws RemoteException,
			ServiceException {
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(url);
		call.setUseSOAPAction(true);
		call.setSOAPActionURI(action);
		call.setOperationName(new QName(nameSpace,methodName));
		call.addParameter(new QName(nameSpace, "RequestXML"), XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter(new QName(nameSpace, "UserName"), XMLType.XSD_STRING,ParameterMode.IN);
		call.addParameter(new QName(nameSpace, "Password"), XMLType.XSD_STRING,ParameterMode.IN);
		call.addParameter(new QName(nameSpace, "Ticket"), XMLType.XSD_STRING,ParameterMode.IN);
		call.setReturnType(XMLType.XSD_STRING);
		//对传入的参数进行赋值
		String[] str = new String[4];
		//省内企业基本信息
		if (METHOD_NAME.equals(methodName) || ENTER_PRISE_INFO_METHOD_NAME.equals(methodName)){
			str[0] = "<Request> <Parameters><OrgCode>"+param+"</OrgCode></Parameters></Request>";
		}else {
			str[0] = "<Request> <Parameters><Idnum>"+param+"</Idnum></Parameters></Request>";
		}
		str[1] = USERNAME;
		str[2] = PASSWORD;
		str[3] = "";
		String result = (String) call.invoke(str);
		System.out.println(result);
		return result;
	}

	/**
	 * 解析省外企业详细信息
	 * @param strXml
	 * @return
	 * @throws DocumentException
	 */
	public static Map<String,Object> parseEnterpriseInfo(String strXml) throws DocumentException {
		Map<String,Object> enterPriseInfoMap = new HashMap<>(16);
		Document document = DocumentHelper.parseText(strXml);
		//获取到根元素
		Element rootElement = document.getRootElement();
		System.out.println(rootElement);
		Iterator iterator = rootElement.elementIterator();
		while (iterator.hasNext()){
			Element next = (Element) iterator.next();
			System.out.println(next.getName()+":"+next.getTextTrim());
			String name = next.getName();
			if ("EntBaseInfo".equals(name)){
				Map<String, String> map = parseEntBaseInfo(next);
				System.out.println("entBaseInfo:"+map);
				enterPriseInfoMap.put("entBaseInfo",map);
			}

			if ("EntQuaInfo".equals(name)){
				List<Map<String, String>> entQuaInfo = parseEntQuaInfo(next);
				System.out.println("entQuaInfo:"+entQuaInfo);
				enterPriseInfoMap.put("entQuaInfo",entQuaInfo);
			}
			if ("EntBranchInfo".equals(name)){
				List<Map<String, String>> quaInfo = parseEntBranchInfo(next);
				System.out.println("EntBranchInfo:"+quaInfo);
				enterPriseInfoMap.put("entBranchInfo",quaInfo);
			}
			if ("PersonList".equals(name)){
				List<Map<String, String>> personList = parsePersonList(next);
				System.out.println("personList:"+personList);
				enterPriseInfoMap.put("personList",personList);
			}
		}
		return enterPriseInfoMap;
	}

	/**
	 * 解析省外进粤企业随从人员备案信息
	 * @param strXml
	 * @return
	 * @throws DocumentException
	 */
	public static Map<String,Object> parseGetPersonInfo(String strXml) throws DocumentException {
		Map<String,Object> enterPriseInfoMap = new HashMap<>(16);
		Document document = DocumentHelper.parseText(strXml);
		//获取到根元素
		Element rootElement = document.getRootElement();
		System.out.println(rootElement);
		Iterator iterator = rootElement.elementIterator();
		while (iterator.hasNext()){
			Element next = (Element) iterator.next();
			System.out.println(next.getName()+":"+next.getTextTrim());
			String name = next.getName();
			//人员基本信息
			if ("PerBaseInfo".equals(name)){
				Map<String, String> personBaseInfo = parsePerBaseInfo(next);
				enterPriseInfoMap.put("perBaseInfo",personBaseInfo);
			}
			//备案人员执业注册证信息
			if("PerRegInfo".equals(name)){
				enterPriseInfoMap.put("perRegInfo",parsePerData(next));
			}
			//备案人员安全生产考核合格证信息
			if ("PerSafeABCInfo".equals(name)){
				enterPriseInfoMap.put("perSafeABCInfo",parsePerData(next));
			}
			//备案人员职业资格证信息
			if("PerCertInfo".equals(name)){
				enterPriseInfoMap.put("perCertInfo",parsePerData(next));
			}
			//备案人员特种作业操作资格证信息
			if ("PerSpecInfo".equals(name)){
				enterPriseInfoMap.put("perSpecInfo",parsePerData(next));
			}

		}
		return enterPriseInfoMap;
	}


	/**
	 * 解析省内企业详细信息
	 * @param strXml
	 * @return
	 * @throws DocumentException
	 */
	public static Map<String,Object> parseGdEnterpriseInfo(String strXml) throws DocumentException {
		Map<String,Object> enterPriseInfoMap = new HashMap<>(16);
		Document document = DocumentHelper.parseText(strXml);
		//获取到根元素
		Element rootElement = document.getRootElement();
		System.out.println(rootElement);
		Iterator iterator = rootElement.elementIterator();
		while (iterator.hasNext()){
			Element next = (Element) iterator.next();
			System.out.println(next.getName()+":"+next.getTextTrim());
			String name = next.getName();
			if ("BaseInfo".equals(name)){
				Map<String, String> map = parseBaseInfo(next);
				enterPriseInfoMap.put("baseInfo",map);
			}
			if("SafeLicence".equals(name)){
				Map<String, String> map = parseSafeLicence(next);
				System.out.println("safeLicenceMap:"+map);
				enterPriseInfoMap.put("safeLicence",map);
			}
			if ("QuaInfo".equals(name)){
				List<Map<String, String>> quaInfo = parseQuaInfo(next);
				System.out.println("quaInfo:"+quaInfo);
				enterPriseInfoMap.put("quaInfo",quaInfo);
			}
			if ("PersonList".equals(name)){
				List<Map<String, String>> personList = parsePersonList(next);
				System.out.println("personList:"+personList);
				enterPriseInfoMap.put("personList",personList);
			}
		}
		return enterPriseInfoMap;
	}

	/**
	 *解析基本企业信息
	 * @param baseInfo
	 * @return
	 */
	public static Map<String,String> parseBaseInfo(Element baseInfo){
		Map<String,String> map = new HashMap<>(16);
		Iterator base = baseInfo.elementIterator();
		while (base.hasNext()){
			Element element = (Element) base.next();
			String name = element.getName();
			switch (name){
				case "OrgCode":
					map.put(name,element.getTextTrim());
					break;
				case  "OrgName":
					map.put(name,element.getTextTrim());
					break;
				case "BizLicenNum":
					map.put(name,element.getTextTrim());
					break;
				case "RegCapital":
					map.put(name,element.getTextTrim());
					break;
				case "DtReg":
					map.put(name,element.getTextTrim());
					break;
				case "DtFound":
					map.put(name,element.getTextTrim());
					break;
				case "RegAddress":
					map.put(name,element.getTextTrim());
					break;
				case "EntType":
					map.put(name,element.getTextTrim());
					break;
				case "LegalName":
					map.put(name,element.getTextTrim());
					break;
				default:
					break;
			}
		}
		return map;
	}

	/**
	 *解析企业安全生产许可证信息
	 * @param safeLicence
	 * @return
	 */
	public static Map<String,String> parseSafeLicence(Element safeLicence){
		Map<String,String> map = new HashMap<>(16);
		Iterator base = safeLicence.elementIterator();
		while (base.hasNext()){
			Element element = (Element) base.next();
			String name = element.getName();
			switch (name){
				case "OrgCode":
					map.put(name,element.getTextTrim());
					break;
				case  "CertNum":
					map.put(name,element.getTextTrim());
					break;
				case "IssueBy":
					map.put(name,element.getTextTrim());
					break;
				case "DTIssue":
					map.put(name,element.getTextTrim());
					break;
				case "DTExpire":
					map.put(name,element.getTextTrim());
					break;
				default:
					break;
			}
		}
		return map;
	}

	/**
	 *解析企业资质信息列表
	 * @param quaInfo
	 * @return
	 */
	public static List<Map<String,String>> parseQuaInfo(Element quaInfo){
		List<Map<String,String>> list = new ArrayList<>();
		Iterator base = quaInfo.elementIterator();
		while (base.hasNext()){
			Element element = (Element) base.next();
			Map<String, String> quaInfoItem = parseQuaInfoItem(element);
			list.add(quaInfoItem);
		}
		return list;
	}

	/**
	 * 解析企业资质信息
	 * @param quaInfoItem
	 * @return
	 */
	public static Map<String,String> parseQuaInfoItem(Element quaInfoItem){
		Map<String,String> map = new HashMap<>(16);
		Iterator item = quaInfoItem.elementIterator();
		while(item.hasNext()){
			Element element1 = (Element) item.next();
			String name = element1.getName();
			switch (name){
				case "OrgCode":
					map.put(name,element1.getTextTrim());
					break;
				case  "QuaType":
					map.put(name,element1.getTextTrim());
					break;
				case "QuaName":
					map.put(name,element1.getTextTrim());
					break;
				case "QuaNum":
					map.put(name,element1.getTextTrim());
					break;
				case "IsMainQua":
					map.put(name,element1.getTextTrim());
					break;
				case "ApproveBy":
					map.put(name,element1.getTextTrim());
					break;
				case "DtApprove":
					map.put(name,element1.getTextTrim());
					break;
				case "DtExpire":
					map.put(name,element1.getTextTrim());
					break;
				case "QuaScope":
					map.put(name,element1.getTextTrim());
					break;
				case "QuaRank":
					map.put(name,element1.getTextTrim());
					break;
				default:
					break;
			}
		}
		return map;
	}

	/**
	 *解析企业人员信息列表
	 * @param personList
	 * @return
	 */
	public static List<Map<String,String>> parsePersonList(Element personList){
		List<Map<String,String>> list = new ArrayList<>();
		Iterator base = personList.elementIterator();
		while (base.hasNext()){
			Element element = (Element) base.next();
			Map<String, String> itemMap = parsePersonListItem(element);
			list.add(itemMap);
		}
		return list;
	}

	/**
	 * 解析人员信息
	 * @param item
	 * @return
	 */
	public static Map<String,String> parsePersonListItem(Element item){
		Map<String,String> map = new HashMap<>(16);
		Iterator itemIterator = item.elementIterator();
		while (itemIterator.hasNext()){
			Element itemElement = (Element) itemIterator.next();
			String name = itemElement.getName();
			switch (name){
				case "IdNum":
					map.put(name,itemElement.getTextTrim());
					break;
				case  "IdType":
					map.put(name,itemElement.getTextTrim());
					break;
				case "PName":
					map.put(name,itemElement.getTextTrim());
					break;
				case "Sex":
					map.put(name,itemElement.getTextTrim());
					break;
				case "HirerOrgName":
					map.put(name,itemElement.getTextTrim());
					break;
				case "Status":
					map.put(name,itemElement.getTextTrim());
					break;
				case "DtRecord":
					map.put(name,itemElement.getTextTrim());
					break;
				case "Mobile":
					map.put(name,itemElement.getTextTrim());
					break;
				case "Title":
					map.put(name,itemElement.getTextTrim());
					break;
				case "TitleNo":
					map.put(name,itemElement.getTextTrim());
					break;
				case "TitleSpty":
					map.put(name,itemElement.getTextTrim());
					break;
				case "Post":
					map.put(name,itemElement.getTextTrim());
					break;
				default:
					break;
			}
		}
		return map;
	}


	/**
	 * 解析省内人员信息详情信息
	 * @param strXml
	 * @return
	 * @throws DocumentException
	 */
	public static Map<String,Object> parseGetGdPersonInfo(String strXml) throws DocumentException {
		Map<String,Object> enterPriseInfoMap = new HashMap<>(16);
		Document document = DocumentHelper.parseText(strXml);
		//获取到根元素
		Element rootElement = document.getRootElement();
		System.out.println(rootElement);
		Iterator iterator = rootElement.elementIterator();
		while (iterator.hasNext()){
			Element next = (Element) iterator.next();
			System.out.println(next.getName()+":"+next.getTextTrim());
			String name = next.getName();
			//人员基本信息
			if ("BaseInfo".equals(name)){
				Map<String, String> personBaseInfo = parsePersonListItem(next);
				enterPriseInfoMap.put("personBaseInfo",personBaseInfo);
			}
			//注册证书信息
			if("RegInfo".equals(name)){
				enterPriseInfoMap.put("regInfo",parseData(next));
			}
			//职称证书信息
			if ("TechTitleInfo".equals(name)){
				enterPriseInfoMap.put("techTitleInfo",parseData(next));
			}
			//安全考核合格证书信息
			if("SafeABCInfo".equals(name)){
				enterPriseInfoMap.put("safeABCInfo",parseData(next));
			}
			//特种作业证书信息
			if ("SpecInfo".equals(name)){
				enterPriseInfoMap.put("specInfo",parseData(next));
			}
			//五大员证书信息（预算员、监理员、质安员、施工员、材料员）
			if ("FiveMemberInfo".equals(name)){
				enterPriseInfoMap.put("fiveMemberInfo",parseData(next));
			}
			//建筑类八大工种、园林绿化类工种从业资格证书信息
			if ("WorkTradeInfo".equals(name)){
				enterPriseInfoMap.put("workTradeInfo",parseData(next));
			}
		}
		return enterPriseInfoMap;
	}

	/**
	 * 解析省内人员信息根节点下的子节点
	 * @param element
	 * @return
	 */
	public static List<Map<String,String>> parseData(Element element){
		List<Map<String,String>> list = new ArrayList<>();
		Iterator itemIterator = element.elementIterator();
		while (itemIterator.hasNext()){
			Element next = (Element) itemIterator.next();
			Map<String, String> map = pasreDataItem(next);
			list.add(map);
		}
		return list;
	}

	/**
	 * 通用解析方法，解析人员 不同种类证书方法
	 * @param item
	 * @return
	 */
	public static Map<String,String> pasreDataItem(Element item){
		Map<String,String> map = new HashMap<>(16);
		Iterator itemIterator = item.elementIterator();
		while (itemIterator.hasNext()) {
			Element itemElement = (Element) itemIterator.next();
			String name = itemElement.getName();
			switch (name) {
				case "IdNum":
					map.put(name, itemElement.getTextTrim());
					break;
				case "RegNum":
					map.put(name, itemElement.getTextTrim());
					break;
				case "RegSealNum":
					map.put(name, itemElement.getTextTrim());
					break;
				case "RegCerNum":
					map.put(name, itemElement.getTextTrim());
					break;
				case "RegType":
					map.put(name, itemElement.getTextTrim());
					break;
				case "RegSpty":
					map.put(name, itemElement.getTextTrim());
					break;
				case "DtReg":
					map.put(name, itemElement.getTextTrim());
					break;
				case "DtExpire":
					map.put(name, itemElement.getTextTrim());
					break;
				case "IssueBy":
					map.put(name, itemElement.getTextTrim());
					break;
				case "CertName":
					map.put(name, itemElement.getTextTrim());
					break;
				case "CertNum":
					map.put(name, itemElement.getTextTrim());
					break;
				case "CertRank":
					map.put(name, itemElement.getTextTrim());
					break;
				case "CertSpty":
					map.put(name, itemElement.getTextTrim());
					break;
				case "DtIssue":
					map.put(name, itemElement.getTextTrim());
					break;
				case "WorkType":
					map.put(name, itemElement.getTextTrim());
					break;
				case "WorkRank":
					map.put(name, itemElement.getTextTrim());
					break;
				case "QuaCerNum":
					map.put(name, itemElement.getTextTrim());
					break;
				case "WebUrl":
					map.put(name, itemElement.getTextTrim());
					break;
				default:
					break;
			}
		}
		return map;
	}

	/**
	 * 省外 备案人员基本信息
	 * @param perBaseInfo
	 * @return
	 */
	public static Map<String,String> parsePerBaseInfo(Element perBaseInfo){
		Map<String,String> map = new HashMap<>(16);
		Iterator base = perBaseInfo.elementIterator();
		while (base.hasNext()) {
			Element element = (Element) base.next();
			String name = element.getName();
			switch (name) {
				case "IdNum":
					map.put(name, element.getTextTrim());
					break;
				case "PName":
					map.put(name, element.getTextTrim());
					break;
				case "Status":
					map.put(name, element.getTextTrim());
					break;
				case "DtRecord":
					map.put(name, element.getTextTrim());
					break;
				case "Sex":
					map.put(name, element.getTextTrim());
					break;
				case "HirerOrgCode":
					map.put(name, element.getTextTrim());
					break;
				case "HirerOrgName":
					map.put(name, element.getTextTrim());
					break;
				case "Mobile":
					map.put(name, element.getTextTrim());
					break;
				case "Title":
					map.put(name, element.getTextTrim());
					break;
				case "TitleNo":
					map.put(name, element.getTextTrim());
					break;
				case "TitleSpty":
					map.put(name, element.getTextTrim());
					break;
				case "Post":
					map.put(name, element.getTextTrim());
					break;
				case "CurrentProject":
					map.put(name, element.getTextTrim());
					break;
				case "PrjCity":
					map.put(name, element.getTextTrim());
					break;
				case "Photo":
					map.put(name, element.getTextTrim());
					break;
				case "SignPhoto":
					map.put(name, element.getTextTrim());
					break;
				default:
					break;
			}
		}
		return map;
	}

	/**
	 * 解析省外人员信息根节点下的子节点
	 * @param element
	 * @return
	 */
	public static List<Map<String,String>> parsePerData(Element element){
		List<Map<String,String>> list = new ArrayList<>();
		Iterator itemIterator = element.elementIterator();
		while (itemIterator.hasNext()){
			Element next = (Element) itemIterator.next();
			Map<String, String> map = pasreDataItem(next);
			list.add(map);
		}
		return list;
	}



	/**
	 *解析基本企业信息
	 * @param entBaseInfo
	 * @return
	 */
	public static Map<String,String> parseEntBaseInfo(Element entBaseInfo){
		Map<String,String> map = new HashMap<>(16);
		Iterator base = entBaseInfo.elementIterator();
		while (base.hasNext()){
			Element element = (Element) base.next();
			String name = element.getName();
			switch (name){
				case "OrgCode":
					map.put(name,element.getTextTrim());
					break;
				case  "OrgName":
					map.put(name,element.getTextTrim());
					break;
				case "Statue":
					map.put(name,element.getTextTrim());
					break;
				case "DtRecord":
					map.put(name,element.getTextTrim());
					break;
				case "DtReg":
					map.put(name,element.getTextTrim());
					break;
				case "DtFound":
					map.put(name,element.getTextTrim());
					break;
				case "RegCity":
					map.put(name,element.getTextTrim());
					break;
				case "RegAddress":
					map.put(name,element.getTextTrim());
					break;
				case "PostCode":
					map.put(name,element.getTextTrim());
					break;
				case "HasBranch":
					map.put(name,element.getTextTrim());
					break;
				case "HasSubPlace":
					map.put(name,element.getTextTrim());
					break;
				case "LegalIdnum":
					map.put(name,element.getTextTrim());
					break;
				case "LegalName":
					map.put(name,element.getTextTrim());
					break;
				case "LegalPost":
					map.put(name,element.getTextTrim());
					break;
				case "LegalTitle":
					map.put(name,element.getTextTrim());
					break;
				case "LegalTel":
					map.put(name,element.getTextTrim());
					break;
				case "LegalPhoto":
					map.put(name,element.getTextTrim());
					break;
				case "LegalSignPhoto":
					map.put(name,element.getTextTrim());
					break;
				case "TechIdnum":
					map.put(name,element.getTextTrim());
					break;
				case "TechName":
					map.put(name,element.getTextTrim());
					break;
				case "TechPost":
					map.put(name,element.getTextTrim());
					break;
				case "TechTitle":
					map.put(name,element.getTextTrim());
					break;
				case "TechTel":
					map.put(name,element.getTextTrim());
					break;
				case "SubManagerIdnum":
					map.put(name,element.getTextTrim());
					break;
				case "SubManagerName":
					map.put(name,element.getTextTrim());
					break;
				case "SubManagerPost":
					map.put(name,element.getTextTrim());
					break;
				case "SubManagerTitle":
					map.put(name,element.getTextTrim());
					break;
				case "SubManagerTel":
					map.put(name,element.getTextTrim());
					break;
				case "SafeLicenseNum":
					map.put(name,element.getTextTrim());
					break;
				case "DtSafeExpirer":
					map.put(name,element.getTextTrim());
					break;
				case "SafeIssueBy":
					map.put(name,element.getTextTrim());
					break;
				default:
					break;
			}
		}
		return map;
	}

	/**
	 *解析企业资质信息列表
	 * @param entQuaInfo
	 * @return
	 */
	public static List<Map<String,String>> parseEntQuaInfo(Element entQuaInfo){
		List<Map<String,String>> list = new ArrayList<>();
		Iterator base = entQuaInfo.elementIterator();
		while (base.hasNext()){
			Element element = (Element) base.next();
			Map<String, String> quaInfoItem =parseQuaInfoItem(element);
			list.add(quaInfoItem);
		}
		return list;
	}

	/**
	 *解析备案企业驻粤分支机构信息
	 * @param entBranchInfo
	 * @return
	 */
	public static List<Map<String,String>> parseEntBranchInfo(Element entBranchInfo){
		List<Map<String,String>> list = new ArrayList<>();
		Iterator base = entBranchInfo.elementIterator();
		while (base.hasNext()){
			Element element = (Element) base.next();
			Map<String, String> map = parseEntBranchInfoItem(element);
			list.add(map);
		}
		return list;
	}






	/**
	 * 解析备案企业驻粤分支机构信息分支下子节点
	 * @param element
	 * @return
	 */
	public static Map<String,String> parseEntBranchInfoItem(Element element){
		Map<String,String> map = new HashMap<>(16);
		Iterator item = element.elementIterator();
		while(item.hasNext()){
			Element element1 = (Element) item.next();
			String name = element1.getName();
			switch (name){
				case "OrgCode":
					map.put(name,element1.getTextTrim());
					break;
				case  "BrhName":
					map.put(name,element1.getTextTrim());
					break;
				case "BrhOrgCode":
					map.put(name,element1.getTextTrim());
					break;
				case "IsMainBrh":
					map.put(name,element1.getTextTrim());
					break;
				case "BrhCity":
					map.put(name,element1.getTextTrim());
					break;
				case "BrhAddress":
					map.put(name,element1.getTextTrim());
					break;
				case "BrhPostCode":
					map.put(name,element1.getTextTrim());
					break;
				case "Contactor":
					map.put(name,element1.getTextTrim());
					break;
				case "Mobile":
					map.put(name,element1.getTextTrim());
					break;
				case "Tel":
					map.put(name,element1.getTextTrim());
					break;
				case "Fax":
					map.put(name,element1.getTextTrim());
					break;
				case "Email":
					map.put(name,element1.getTextTrim());
					break;
				case "BizNum":
					map.put(name,element1.getTextTrim());
					break;
				case "DtFound":
					map.put(name,element1.getTextTrim());
					break;
				case "Manager":
					map.put(name,element1.getTextTrim());
					break;
				case "ManagerIdnum":
					map.put(name,element1.getTextTrim());
					break;
				case "ManagerReg":
					map.put(name,element1.getTextTrim());
					break;
				case "ManagerRegSpty":
					map.put(name,element1.getTextTrim());
					break;
				case "ManagerTitle":
					map.put(name,element1.getTextTrim());
					break;
				case "ManagerMobile":
					map.put(name,element1.getTextTrim());
					break;
				case "ManagerABCNum":
					map.put(name,element1.getTextTrim());
					break;
				case "Tech":
					map.put(name,element1.getTextTrim());
					break;
				case "TechIdnum":
					map.put(name,element1.getTextTrim());
					break;
				case "TechReg":
					map.put(name,element1.getTextTrim());
					break;
				case "TechRegSpty":
					map.put(name,element1.getTextTrim());
					break;
				case "TechTitle":
					map.put(name,element1.getTextTrim());
					break;
				case "TechMobile":
					map.put(name,element1.getTextTrim());
					break;
				case "TechABCNum":
					map.put(name,element1.getTextTrim());
					break;
				case "Safer":
					map.put(name,element1.getTextTrim());
					break;
				case "SaferIdnum":
					map.put(name,element1.getTextTrim());
					break;
				case "SaferReg":
					map.put(name,element1.getTextTrim());
					break;
				case "SaferRegSpty":
					map.put(name,element1.getTextTrim());
					break;
				case "SaferTitle":
					map.put(name,element1.getTextTrim());
					break;
				case "SaferMobile":
					map.put(name,element1.getTextTrim());
					break;
				case "SaferABCNum":
					map.put(name,element1.getTextTrim());
					break;
				case "Qtyer":
					map.put(name,element1.getTextTrim());
					break;
				case "QtyerIdnum":
					map.put(name,element1.getTextTrim());
					break;
				case "QtyerReg":
					map.put(name,element1.getTextTrim());
					break;
				case "QtyerRegSpty":
					map.put(name,element1.getTextTrim());
					break;
				case "QtyerTitle":
					map.put(name,element1.getTextTrim());
					break;
				case "QtyerMobile":
					map.put(name,element1.getTextTrim());
					break;
				case "QtyerABCNum":
					map.put(name,element1.getTextTrim());
					break;
				default:
					break;
			}
		}
		return map;
	}



}