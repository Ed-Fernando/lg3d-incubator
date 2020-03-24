package nu.koidelab.cosmo.util.bind2d;

import java.util.ArrayList;
import java.util.List;

import nu.koidelab.cosmo.util.schedule.Plan;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author fumi CosmoXMLParser nu.koidelab.cosmo.util.bind2d
 */
public class CosmoXMLParser {
	private CosmoXMLParser() {}
    
    public static List<Plan> loadPlans(String filename) {
        Cosmo_BindXML binder = new Cosmo_BindXML();
        binder.setFileName(filename);
        
        NodeList plans = binder.loadListByTagName("plan");
        Element plan;        
        
        /*-- Plan data ---*/
        String name;
        int priority = 0;
        long startTime;
        long time;
        long endTime;
        String detail;
        String reference;
        String category;
        int weight;
        String imageFile;
        /* Plan List */
        List<Plan> planList = new ArrayList<Plan>();
        for (int i = 0; i < plans.getLength(); i++) {
            plan = (Element) plans.item(i);
            
            /* ----- ̾���������١��Ťߤν��� ----- */
            name = plan.getAttribute("name");
            try {
                priority = Integer.parseInt(plan.getAttribute("priority"));
                weight = Integer.parseInt(plan.getAttribute("weight"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.err.println("�ե�����ν񼰤��ְ�äƤ��ޤ���priority��weight�����Ƥ˸�꤬����ޤ���");
                System.err.println(i + "���ܤ�<plan>");
                weight = 0;
            }

            /* ----- ���եǡ����ν��� ----- */
            Element times = (Element)(plan.getElementsByTagName("times").item(0));
            String tmp = getNodeValue("time", times);
            if(tmp != null){
                time = Plan.stringToMsecValue(tmp);
            } else {
                time = System.currentTimeMillis();
                System.err.println("���顼 : ���դ����ꤵ��Ƥ��ʤ�ͽ�꤬����ޤ�����");
                System.err.println("�������������߻���˽������ޤ���");
            }
            Plan p = new Plan(name, priority, time);
            tmp = getNodeValue("startTime", times);
            if(tmp != null){
                startTime = Plan.stringToMsecValue(tmp);
                p.setStartTime(startTime);
            }
            
            tmp = getNodeValue("endTime", times);
            if(tmp != null){
                endTime = Plan.stringToMsecValue(getNodeValue("endTime", times));
                p.setEndtTime(endTime);
            }
            

            /* ----- �ܺ٥ǡ����ν��� ----- */
            category = getNodeValue("category", plan);
            detail = getNodeValue("detail", plan);
            reference = getNodeValue("reference", plan);
            imageFile = getNodeValue("imageFile", plan);

            if(category != null)
                p.setCategory(category);
            if(detail != null)
                p.setDetail(detail);
            if(reference != null)
                p.setReference(reference);
            p.setWeight(weight);
            if(imageFile != null)
                p.setImageFile(imageFile);

            
            /* ----- �ꥹ�Ȥ��ɲ� ----- */
            if (name.equals("")) {
                System.err.println("̾���Τʤ��������塼�뤬����ޤ���");
                System.err.println((i + 1) + "���ܤ�<plan>");
            } else{                                
                planList.add(p);
            }
        }
        return planList;
    }
    
    private static String getNodeValue(String NodeName, Element element) {
        try {
            return element.getElementsByTagName(NodeName).item(0).getFirstChild().getNodeValue();
        } catch (NullPointerException e) {
            return null;
        }
    }
    
    public static void savePlans(List<Plan> schedule, String filename){
        Cosmo_BindXML binder = new Cosmo_BindXML();
        Document document = binder.getNewDocument("cosmo");
        Element root = document.getDocumentElement();

        Element planElem;
        Element timesElem;
        Element timeElem;
        Element startTimeElem;
        Element endTimeElem;
        Element detailElem;
        Element refElem;
        Element categoryElem;
        Element imagefileElem;
        for (int i = 0; i < schedule.size(); i++) {
            /* Plan Tag */
            planElem = document.createElement("plan");
            planElem.setAttribute("name", schedule.get(i).getName());            
            planElem.setAttribute("priority", String.valueOf(schedule
                    .get(i).getPriority()));
            planElem.setAttribute("weight", String.valueOf(schedule
                    .get(i).getWeight()));
            
            /* times Tag and its elements */
            timesElem = document.createElement("times");
            planElem.appendChild(timesElem);
            timeElem = document.createElement("time");                        
            timeElem.appendChild(document.createTextNode(Plan
                    .msecValueToString(schedule.get(i).getTime())));
            timesElem.appendChild(timeElem);
            
            if(schedule.get(i).getStartTime() > 0){
                startTimeElem = document.createElement("startTime");
                startTimeElem.appendChild(document.createTextNode(Plan
                        .msecValueToString(schedule.get(i).getStartTime())));
                timesElem.appendChild(startTimeElem);
            }
            if(schedule.get(i).getEndtTime() > 0){
                endTimeElem = document.createElement("endTime");
                endTimeElem.appendChild(document.createTextNode(Plan
                        .msecValueToString(schedule.get(i).getEndtTime())));
                timesElem.appendChild(endTimeElem);
            }

            /* other Tags */
            if (schedule.get(i).getDetail() != "") {
                detailElem = document.createElement("detail");
                detailElem.appendChild(document.createTextNode(schedule
                        .get(i).getDetail()));
                planElem.appendChild(detailElem);
            }
            if (schedule.get(i).getReference() != "") {
                refElem = document.createElement("reference");
                refElem.appendChild(document.createTextNode(schedule
                        .get(i).getReference()));
                planElem.appendChild(refElem);
            }
            if (schedule.get(i).getCategory() != "") {
                categoryElem = document.createElement("category");
                categoryElem.appendChild(document.createTextNode(schedule
                        .get(i).getCategory()));
                planElem.appendChild(categoryElem);
            }            
            if (schedule.get(i).getImageFile() != "") {
                imagefileElem = document.createElement("imageFile");
                imagefileElem.appendChild(document.createTextNode(schedule
                        .get(i).getImageFile()));
                planElem.appendChild(imagefileElem);
            }
            
            root.appendChild(planElem);
        }

        Cosmo_BindXML.saveDocument(document, filename);
    }
    
	public static HolidayList loadHolidays(String filename) {
        Cosmo_BindXML binder = new Cosmo_BindXML();
        binder.setFileName(filename);
		HolidayList list = new HolidayList();
		NodeList holidays = binder.loadListByTagName("holiday");
		Element holiday;
		for (int i = 0; i < holidays.getLength(); i++) {
			holiday = (Element) holidays.item(i);
			list.add(new Holiday(holiday.getAttribute("name"), holiday
					.getAttribute("date")));
		}
		return list;
	}

	public static void saveHolidays(HolidayList list, String filename) {
        Cosmo_BindXML binder = new Cosmo_BindXML();
		Document document = binder.getNewDocument("holidays");
		Element root = document.getDocumentElement();

		Element holidayElem;
		for (int i = 0; i < list.size(); i++) {
			holidayElem = document.createElement("holiday");
			holidayElem.setAttribute("name", list.get(i).getName());
			holidayElem.setAttribute("date", list.get(i).getDate());

			root.appendChild(holidayElem);
		}

		Cosmo_BindXML.saveDocument(document, filename);
	}
    
    /* Test program for  I/O (schedule file) */
    public static void main(String[] args) {
        List<Plan> plans = CosmoXMLParser.loadPlans("nu/koidelab/cosmo/resources/xml/schedule.xml");
        for (int i = plans.size() - 1; i >= 0; i--) {
            System.err.println( plans.get(i).getName() );
        }
        plans.add(new Plan("FILE I/O implemented", 1, System.currentTimeMillis()));
        CosmoXMLParser.savePlans(plans, "nu/koidelab/cosmo/resources/xml/schedule2.xml");
    }
	/*
	 * /* ������򥤥�ݡ��Ȥ������������ѹ����ƽ��ϡ�
	 * 
	 * public static void main(String[] args) { if (args.length > 0) { if
	 * (args[0].equals("test")) { if (args.length > 1) { ScheduleList list = new
	 * ScheduleList(CosmoXMLParser.loadSchedule(args[1])); list.printToSysout();
	 * System.out.println("OK." + list.size()); } else {
	 * System.err.println("�ե�����̾����������Ϥ��Ʋ�������");
	 *  } } else { binder.setFileName(args[0]); ScheduleList list = new
	 * ScheduleList(binder.loadDate2()); list.printToSysout();
	 * CosmoXMLParser.saveSchedule(list, args[0]); } } else {
	 * System.err.println("�ե�����̾����������Ϥ��Ʋ�������"); } }
	 */
}