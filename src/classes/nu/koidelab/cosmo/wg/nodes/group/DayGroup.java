package nu.koidelab.cosmo.wg.nodes.group;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import nu.koidelab.cosmo.manager.CSDGeneralManager;
import nu.koidelab.cosmo.util.function.OrbitFunction;
import nu.koidelab.cosmo.util.function.calendar.CalendarFunction;
import nu.koidelab.cosmo.util.function.decoration.DayOrbitDecoration;
import nu.koidelab.cosmo.util.gesture.menu.ExtraCSDMenu;
import nu.koidelab.cosmo.util.gesture.menu.MenuIcon;
import nu.koidelab.cosmo.util.gesture.menu.MenuList;
import nu.koidelab.cosmo.util.gesture.menu.AllowMenu;
import nu.koidelab.cosmo.util.schedule.Plan;
import nu.koidelab.cosmo.wg.nodes.EditableOrbiter;
import nu.koidelab.cosmo.wg.nodes.Orbiter;
import nu.koidelab.cosmo.wg.nodes.Planet;
import nu.koidelab.cosmo.wg.shape.NumberPanel;

import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.LayoutManager3D;

public class DayGroup extends TimeGroup {
	private int date;
	private MonthGroup parent;
	private int dayOfWeek;
	public List<Orbiter> orbiters;
	private DayOrbitDecoration deco;
	private DateIDPanel numPanel;
	
	private static MenuList menu = new MenuList();
	static{
        menu.add(ExtraCSDMenu.ZOOM_OUT);
        menu.add(MenuIcon.NEW);
        menu.add(ExtraCSDMenu.ZOOM_IN);
        menu.add(MenuIcon.NULL_MENU);
	}

	DayGroup(int date, MonthGroup mg, List<Plan> planList) {
		this.date = date;
		int year = mg.getYearGroup().getYear();
		int month = mg.getMonth();
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(year, month, date, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		st = cal.getTimeInMillis();
		dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		cal.set(year, month, date + 1, 0, 0, 0);
		ed = cal.getTimeInMillis();
		parent = mg;
		setLayout(new OrbiterLayout());
		pickoutPlans(planList);

		deco = new DayOrbitDecoration(st, ed, dayOfWeek);
		addChild(deco);
		numPanel = new DateIDPanel(date, dayOfWeek);
		addChild(numPanel);
	}

	int getDate() {
		return this.date;
	}

	List<Plan> getPlans() {
		System.err.println("return :" + date + " / " + plans.size());
		return plans;
	}

	void addPlan(Plan newPlan) {
		long time = newPlan.getTime();
		if (isInRange(time)) {
			plans.add(newPlan);
			orbiters.add(new Planet(newPlan));
			addChild(orbiters.get(orbiters.size() - 1));
		} else { 
			System.err.println("(EE) DayGroup#addPlan line : 90");
		}	
	}

	void removePlan(Plan plan) {
		long time = plan.getTime();
		if (isInRange(time)) {
			int index = getOrbiterIndexOf(plan);
			if (index >= 0) {
				Orbiter o = orbiters.get(index);
				orbiters.remove(index);
				removeChild(o);
				plans.remove(plan);
			} else {
				System.err.println("(EE) DayGroup#removePlan line : 104");
			}
		} else {
			System.err.println("(EE) DayGroup#removePlan line : 107");
		}
	}

	private int getOrbiterIndexOf(Plan plan) {
		int index = -1;
		for (int i = orbiters.size() - 1; i >= 0; i--) {
			if (orbiters.get(i) instanceof EditableOrbiter) {
				EditableOrbiter eo = (EditableOrbiter) orbiters.get(i);
				if (eo.getPlan().equals(plan)) {
					index = i;
				}
			}

		}
		return index;
	}

	@Override
	protected void pickoutPlans(List<Plan> target) {
		if (orbiters == null)
			orbiters = new LinkedList<Orbiter>();
		if (plans == null)
			plans = new LinkedList<Plan>();
		for (int i = 0; i < target.size(); i++) {
			Plan p = target.get(i);
			if (isInRange(p.getTime())) {
				plans.add(p);
				orbiters.add(new Planet(p));
				addChild(orbiters.get(orbiters.size() - 1));
				target.remove(p);
				i--;
			}
		}
	}

	private void setDecoration() {
		OrbitFunction func = CSDGeneralManager.getInstance().getFunction();
		func.setDecoration(deco);
	}

	void setMode(int mode) {
		switch (mode) {
		case Calendar.YEAR:
			setVisible(false);
			break;

		case Calendar.MONTH:
			setVisible(true);
			setDecoration();
			((OrbiterLayout)getLayout()).setLayoutMode(OrbiterLayout.CENTER);
			break;

		case Calendar.WEEK_OF_MONTH:  /* 4 */
		case Calendar.DATE:			   /* 5 */
		case Calendar.HOUR_OF_DAY:    /* 11 */
			setVisible(true);
			setDecoration();
			((OrbiterLayout)getLayout()).setLayoutMode(OrbiterLayout.NORMAL);
			break;
            

		default:
			throw new IllegalArgumentException("Argument Not suported" + mode);			
		}
	}
	
	private void tmpCalendarFunc(CalendarFunction cf){
		cf.setPosition(this);
	}
	
	public IDPanel getIDPanel(){
		return numPanel;
	}

	public class OrbiterLayout implements LayoutManager3D {
		public static final int CENTER = 0;
		public static final int TATE = 1;
		public static final int NORMAL = 2;
		private List<Orbiter> compList = new LinkedList<Orbiter>();
		private int currentMode = NORMAL;
		
		private OrbiterLayout() {}

		public void addLayoutComponent(Component3D comp, Object constraints) {
			if (comp instanceof Orbiter) 
				compList.add((Orbiter) comp);
		}

		public void layoutContainer() {
			OrbitFunction func = CSDGeneralManager.getInstance().getFunction();

			if (func instanceof CalendarFunction) {
				CalendarFunction cf = (CalendarFunction) func;
				tmpCalendarFunc(cf);
				return;
			}
			if (NORMAL == currentMode) {
				for (int i = compList.size() - 1; i >= 0; i--) {
					Orbiter o = compList.get(i);
					func.setPosition(o);
					if (o instanceof EditableOrbiter) {
						EditableOrbiter eo = (EditableOrbiter) o;
//						eo.setScale(1);
						eo.setBodyVisible(true);
						eo.beRepresentative(false);
					}
				}
			}

			if (TATE == currentMode) {
				System.err.println(this.getClass()
						+ "\n      -> rearrangeLayout(TATE-MODE) called;");
				long center = st + ((ed - st) / 2);
				float[] vec = func.getPosition(center);
				int numChildren = compList.size();

				long[] times = new long[numChildren];
				for (int i = numChildren - 1; i >= 0; i--) {
					Orbiter o = compList.get(i);
					if (o instanceof EditableOrbiter) {
						EditableOrbiter eo = (EditableOrbiter) o;
						times[i] = eo.getPlan().getTime();
//						eo.setScale(1);
						eo.setBodyVisible(true);
						eo.beRepresentative(false);
					}
				}
				Arrays.sort(times);

				boolean isEvenNum = ((numChildren % 2) == 0);
				float space = 0.04f;
				float yPos = 0;
				for (int i = compList.size() - 1; i >= 0; i--) {
					Orbiter o = compList.get(i);
					if (o instanceof EditableOrbiter) {
						EditableOrbiter eo = (EditableOrbiter) o;
						int index = -1;
						for (int j = times.length - 1; j >= 0; j--) {
							if (times[j] == eo.getPlan().getTime())
								index = j;
						}
						assert (index >= 0);
						if (isEvenNum) {
							yPos = space * (-numChildren + 2 * index + 1);
						} else { /* if number of children is odd number. */
							yPos = space * (index - (numChildren - 1) / 2);
						}
						eo.changeTranslation(vec[0], vec[1] + yPos, vec[2]);
					}
				}
			}

			if (CENTER == currentMode) {
				long center = st + ((ed - st) / 2);
				float[] vec = func.getPosition(center);
				
				int index = -1;
				int maxPriority = -1;
				for (int i = compList.size() - 1; i >= 0; i--) {
					Orbiter o = compList.get(i);
					if (o instanceof EditableOrbiter) {
						EditableOrbiter eo = (EditableOrbiter) o;
						int priority = eo.getPlan().getPriority();
						if (maxPriority < priority) {
							maxPriority = priority;
							index = i;
						}
						eo.setBodyVisible(false);
						eo.setTranslation(vec[0], vec[1], vec[2]);
					} else {
						func.setPosition(o);
					}
				}
				/* If this DayGroup has no EditableOrbiter, do this. */
				if (index < 0)
					return;
				EditableOrbiter representative = (EditableOrbiter) compList
						.get(index);
				representative.beRepresentative(true);
				representative.setBodyVisible(true);				
				if (!isInRange(representative.getTime())) {
					func.setPosition(representative);
				} else {
					representative.changeTranslation(vec[0], vec[1], vec[2]);
				}
			}
		}

		public boolean rearrangeLayoutComponent(Component3D comp,
				Object newConstraints) {
			if (compList.size() <= 0)
				return false;
			if (!(newConstraints instanceof Integer))
				return false;			
			currentMode = ((Integer) newConstraints).intValue();
			return true;
		}

		public void removeLayoutComponent(Component3D comp) {
			assert (comp instanceof Orbiter);
			compList.remove(comp);
		}

		public void setContainer(Container3D cont) {
			if (!(cont instanceof DayGroup))
				throw new IllegalArgumentException(
						"argument need be instance of DayGroup!");
		}
		
		private void setLayoutMode(Object newConstraints){
			if(rearrangeLayoutComponent(null, newConstraints))
				revalidate();			
		}
	}

	public class DateIDPanel extends IDPanel implements AllowMenu{
		private Component3D comp = new Component3D();

		private DateIDPanel(int num, int dayOfWeek) {
			Shape3D s = new NumberPanel(num);
			if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY){
				SimpleAppearance sa =(SimpleAppearance)s.getAppearance();
				if(dayOfWeek == Calendar.SUNDAY)
					sa.setColor(0.8f, 0.6f, 0.6f);
				else
					sa.setColor(0.6f, 0.6f, 0.8f);
			}			
			comp.addChild(s);
			addChild(comp);
		}

        
        /* ********** Implementation of Orbiter ************* */        
		@Override
		public long getTime() {
			return st + ((ed - st) / 24);
		}

        
		public void addNewPlan(String name, int priority, String reference) {
			Plan p = new Plan(name, priority, st + ((ed - st) / 2));
			p.setReference(reference);
			addPlan(p);
		}

        /* ************* Implementation of IDPanel ************* */
		protected void setFocus(boolean focus) {
			if (focus) {
				comp.changeScale(1.2f);
//				comp.changeTranslation(0, 0.003f, 0);
                comp.changeTranslation(0, 0, 0);
			} else {
				comp.changeScale(1.0f);
				comp.changeTranslation(0, 0, 0); 
			}
		}

		protected void doubleClicked() {
            /*
			setRotationAngle(0);
			changeRotationAngle((float) (2 * Math.PI));
            int mode = CSDGeneralManager.getInstance().getFunction().getMode();
			if (mode == Calendar.MONTH) {
				CSDGeneralManager.getInstance().setMode(Calendar.WEEK_OF_MONTH);
			} else if (mode == Calendar.WEEK_OF_MONTH) {
				CSDGeneralManager.getInstance().setMode(Calendar.DAY_OF_MONTH);
			} else if (mode == Calendar.DAY_OF_MONTH) {
//				CSDGeneralManager.getInstance().setMode(Calendar.HOUR_OF_DAY);
//            } else if (mode == Calendar.HOUR_OF_DAY) {
//                CSDGeneralManager.getInstance().setMode(Calendar.MONTH);
//              } else if (mode == Calendar.HOUR_OF_DAY) {
              CSDGeneralManager.getInstance().setMode(Calendar.MONTH);
			}
            */
            long pos = st + ((ed - st) /24);
            CSDGeneralManager.getInstance().setCameraPosition(pos);
		}

		protected void selected(){
            /*
			long pos = st + ((ed - st) /24);
			CSDGeneralManager.getInstance().setCameraPosition(pos);
            */
		}
        /* ************* Implementation of IDPanel ************* */
		
        
        /* *************** Menu ************** */
		public MenuList getMenuList() {
			return menu;
		}
		
		public void processMenu(MenuIcon menu) {
            if(menu == MenuIcon.NEW){
                CSDGeneralManager.getInstance().getEditor().launchNewPanel(this);
			} else if(menu == ExtraCSDMenu.ZOOM_OUT){
                zoom(false);
            } else if(menu == ExtraCSDMenu.ZOOM_IN){
                zoom(true);
            }
		}
        /* *************** Menu ************** */
        
        private void zoom(boolean in){
            int currentMode = CSDGeneralManager.getInstance().getFunction().getMode();
            int nextMode = -1;
            if(in){
                switch (currentMode) {
                case Calendar.YEAR:
                    nextMode = Calendar.MONTH;
                    break;
                case Calendar.MONTH:
                    nextMode = Calendar.WEEK_OF_MONTH;
                    break;
                case Calendar.WEEK_OF_MONTH:
                    nextMode = Calendar.DAY_OF_MONTH;
                    break;
                case Calendar.DAY_OF_MONTH:
                case Calendar.HOUR_OF_DAY:
                    nextMode = Calendar.HOUR_OF_DAY;
                    break;
                default:
                    /* Do nothing. */
                    break;
                }
            } else {
                switch (currentMode) {
                case Calendar.YEAR:
                case Calendar.MONTH:
                    nextMode = Calendar.YEAR;
                    break;
                case Calendar.WEEK_OF_MONTH:
                    nextMode = Calendar.MONTH;
                    break;
                case Calendar.DAY_OF_MONTH:
                    nextMode = Calendar.WEEK_OF_MONTH;
                    break;
                case Calendar.HOUR_OF_DAY:
                    nextMode = Calendar.DAY_OF_MONTH;
                    break;
                default:
                    /* Do nothing. */
                    break;
                }
            }
            if(nextMode >= 0){
                if(currentMode != nextMode)
                    CSDGeneralManager.getInstance().setMode(nextMode);
            }
        }
	}

}
