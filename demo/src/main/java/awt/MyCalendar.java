package awt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JApplet;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class MyCalendar extends JApplet {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    //���������쵽������
    public static final String WEEK_SUN = "SUN";
    public static final String WEEK_MON = "MON";
    public static final String WEEK_TUE = "TUE";
    public static final String WEEK_WED = "WED";
    public static final String WEEK_THU = "THU";
    public static final String WEEK_FRI = "FRI";
    public static final String WEEK_SAT = "SAT";
    //���ñ�����ɫ
    public static final Color background = Color.white;
    //����ǰ����ɫ
    public static final Color foreground = Color.black;
    //������ͷ���ڵı�����ɫ��ǰ����ɫ
    public static final Color headerBackground = Color.blue;
    public static final Color headerForeground = Color.white;
    //���ñ�ѡ�е����ڵı�����ɫ��ǰ����ɫ
    public static final Color selectedBackground = Color.blue;
    public static final Color selectedForeground = Color.white;

    private JPanel cPane;
    private JLabel yearsLabel; //��
    private JSpinner yearsSpinner; //�����
    private JLabel monthsLabel; //��
    private JComboBox<Integer> monthsComboBox; //��������
    private JTable daysTable; //������ʾ���ڵ�table
    private AbstractTableModel daysModel;
    private Calendar calendar;

    //���췽�� ��ʼ��panel
    public MyCalendar() {
        cPane = (JPanel) getContentPane();
    }

    //��ʼ���������еĿռ���в��֡�
    public void init() {
        cPane.setLayout(new BorderLayout());
        //ʹ��border���ֹ�����
        calendar = Calendar.getInstance();
        calendar = Calendar.getInstance();
        yearsLabel = new JLabel("Year: ");
        yearsSpinner = new JSpinner();
        yearsSpinner.setEditor(new JSpinner.NumberEditor(yearsSpinner, "0000"));
        yearsSpinner.setValue(new Integer(calendar.get(Calendar.YEAR)));
        //���Ӽ��� ������ݵĸı�
        yearsSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.YEAR, ((Integer) yearsSpinner.getValue()).intValue());
                int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                calendar.set(Calendar.DAY_OF_MONTH, day > maxDay ? maxDay : day);
                updateView();
            }
        });

        JPanel yearMonthPanel = new JPanel();
        cPane.add(yearMonthPanel, BorderLayout.NORTH);
        yearMonthPanel.setLayout(new BorderLayout());
        yearMonthPanel.add(new JPanel(), BorderLayout.CENTER);
        JPanel yearPanel = new JPanel();
        yearMonthPanel.add(yearPanel, BorderLayout.WEST);
        yearPanel.setLayout(new BorderLayout());
        yearPanel.add(yearsLabel, BorderLayout.WEST);
        yearPanel.add(yearsSpinner, BorderLayout.CENTER);

        monthsLabel = new JLabel("Month: ");
        //���·�����������������
        monthsComboBox = new JComboBox<Integer>();
        for (int i = 1; i <= 12; i++) {
            monthsComboBox.addItem(new Integer(i));
        }
        monthsComboBox.setSelectedIndex(calendar.get(Calendar.MONTH));
        //�����·ݵĸı�
        monthsComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.MONTH, monthsComboBox.getSelectedIndex());
                int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                calendar.set(Calendar.DAY_OF_MONTH, day > maxDay ? maxDay : day);
                updateView();
            }
        });
        JPanel monthPanel = new JPanel();
        yearMonthPanel.add(monthPanel, BorderLayout.EAST);
        monthPanel.setLayout(new BorderLayout());
        monthPanel.add(monthsLabel, BorderLayout.WEST);
        monthPanel.add(monthsComboBox, BorderLayout.CENTER);

        daysModel = new AbstractTableModel() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            //������7
            public int getRowCount() {
                return 7;
            }

            //������7
            public int getColumnCount() {
                return 7;
            }

            public Object getValueAt(int row, int column) {
                if (row == 0) {
                    return getHeader(column);
                }
                row--;
                Calendar calendar = (Calendar) MyCalendar.this.calendar.clone();
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                int dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                int moreDayCount = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                int index = row * 7 + column;
                int dayIndex = index - moreDayCount + 1;
                if (index < moreDayCount || dayIndex > dayCount) {
                    return null;
                } else {
                    return new Integer(dayIndex);
                }
            }
        };

        daysTable = new CalendarTable(daysModel, calendar);
        //����ÿ��cell���Ա�ѡ��
        daysTable.setCellSelectionEnabled(true);
        daysTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        daysTable.setDefaultRenderer(daysTable.getColumnClass(0), new TableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                String text = (value == null) ? "" : value.toString();
                JLabel cell = new JLabel(text);
                cell.setOpaque(true);
                if (row == 0) {
                    cell.setForeground(headerForeground);
                    cell.setBackground(headerBackground);
                } else {
                    if (isSelected) {
                        cell.setForeground(selectedForeground);
                        cell.setBackground(selectedBackground);
                    } else {
                        cell.setForeground(foreground);
                        cell.setBackground(background);
                    }
                }

                return cell;
            }
        });
        updateView();

        cPane.add(daysTable, BorderLayout.CENTER);
    }

    public static String getHeader(int index) {
        switch (index) {
            case 0:
                return WEEK_SUN;
            case 1:
                return WEEK_MON;
            case 2:
                return WEEK_TUE;
            case 3:
                return WEEK_WED;
            case 4:
                return WEEK_THU;
            case 5:
                return WEEK_FRI;
            case 6:
                return WEEK_SAT;
            default:
                return null;
        }
    }

    public void updateView() {
        daysModel.fireTableDataChanged();
        daysTable.setRowSelectionInterval(calendar.get(Calendar.WEEK_OF_MONTH),
                calendar.get(Calendar.WEEK_OF_MONTH));
        daysTable.setColumnSelectionInterval(calendar.get(Calendar.DAY_OF_WEEK) - 1,
                calendar.get(Calendar.DAY_OF_WEEK) - 1);
    }

    //����������table
    public static class CalendarTable extends JTable {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private Calendar calendar;

        public CalendarTable(TableModel model, Calendar calendar) {
            super(model);
            this.calendar = calendar;
        }

        public void changeSelection(int row, int column, boolean toggle, boolean extend) {
            super.changeSelection(row, column, toggle, extend);
            if (row == 0) {
                return;
            }
            Object obj = getValueAt(row, column);
            if (obj != null) {
                calendar.set(Calendar.DAY_OF_MONTH, ((Integer) obj).intValue());
            }
        }

    }

    //��applet��Ϊһ����ִ�еĳ���������
    public static void main(String[] args) {
        JFrame frame = new JFrame("Calendar Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyCalendar myCalendar = new MyCalendar();
        myCalendar.init();
        frame.getContentPane().add(myCalendar);
        frame.setSize(240, 172);
        frame.setVisible(true);
    }

}
