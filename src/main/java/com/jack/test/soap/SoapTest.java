package com.jack.test.soap;


import org.apache.axis.client.Service;
import org.apache.axis.client.Call;
import org.apache.axis.utils.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;

import java.rmi.RemoteException;
import java.util.*;

/**
 * @description
 * @author: zq
 * @create: 2020-12-10 10:54
 **/
public class SoapTest {

	public static void main(String[] args) throws ServiceException, RemoteException, DocumentException {
		String wdslUrl = "http://210.76.74.218/TestGDCICServiceCenter/GovermentService.svc?wdsl";
		String namespace ="http://tempuri.org/";
		String methodName = "GetGdEnterpriseInfo";
		String actionSoap = "http://tempuri.org/IGovermentService/GetGdEnterpriseInfo";
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(wdslUrl);
		call.setUseSOAPAction(true);
		call.setSOAPActionURI(actionSoap);
		call.setOperationName(new QName(namespace,methodName));
		call.addParameter(new QName(namespace, "RequestXML"), XMLType.XSD_STRING,ParameterMode.IN);
		call.addParameter(new QName(namespace, "UserName"), XMLType.XSD_STRING,ParameterMode.IN);
		call.addParameter(new QName(namespace, "Password"), XMLType.XSD_STRING,ParameterMode.IN);
		call.addParameter(new QName(namespace, "Ticket"), XMLType.XSD_STRING,ParameterMode.IN);

		call.setReturnType(XMLType.XSD_STRING);
		//对传入的参数进行赋值
		String requestXML = "<Request> <Parameters><OrgCode>195174149</OrgCode></Parameters></Request>";
		String userName = "test_007329909";
		String password = "123456";
		String ticket = "";
		String[] str = new String[4];
		str[0]=requestXML;
		str[1]=userName;
		str[2]=password;
		str[3]=ticket;
		String result = (String) call.invoke(str);
		if (StringUtils.isEmpty(result)){
			System.out.println("调用接口失败");
			return;
		}
		System.out.println(result);
		//解析数据
		parseGdEnterpriseInfo("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+result);
	}

	public static void parseGdEnterpriseInfo(String strXml) throws DocumentException {
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
				System.out.println(map);
			}
			if("SafeLicence".equals(name)){
				Map<String, String> map = parseSafeLicence(next);
				System.out.println("safeLicenceMap:"+map);
			}
			if ("QuaInfo".equals(name)){
				List<Map<String, String>> quaInfo = parseQuaInfo(next);
				System.out.println("quaInfo:"+quaInfo);
			}
			if ("PersonList".equals(name)){
				List<Map<String, String>> personList = parsePersonList(next);
				System.out.println("personList:"+personList);
			}
		}

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
	 * 解析企业人员信息
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
				default:
					break;
			}
		}
		return map;
	}

}