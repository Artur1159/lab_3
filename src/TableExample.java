import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class TableExample {
    public static void main(String[] args) {
        // Создаем главное окно
        JFrame frame = new JFrame("Приложение");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Добавляем меню
        JMenuBar menuBar = new JMenuBar();
        JMenu helpMenu = new JMenu("Справка");

        // Пункт "О программе"
        JMenuItem aboutItem = new JMenuItem("О программе");
        aboutItem.addActionListener(e -> {
            // Показываем диалог "О программе"
            JOptionPane.showMessageDialog(
                    frame, // Передаем окно как родителя
                    "Автор: Гец Артур\nГруппа: 7",
                    "О программе",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);

        // Таблица
        Object[][] data = {
                {3.0, "x^2 + 2x + 1", null},
                {5.5, "2x^3 + x + 1", null},
                {2.0, "x^2 - 4", null},
                {4.3, "3x + 2", null}
        };

        // Вычисляем значения для третьего столбца
        for (Object[] row : data) {
            double value = (double) row[0];
            row[2] = (value % 1 == 0); // true, если дробной части нет
        }

        // Создаем таблицу
        String[] columnNames = {"Значение", "Многочлен", "Точное значение?"};
        PolynomialTableModel model = new PolynomialTableModel(data, columnNames);
        JTable table = new JTable(model);

        // Устанавливаем отображение флажков в третьем столбце
        table.getColumnModel().getColumn(2).setCellRenderer(table.getDefaultRenderer(Boolean.class));
        table.getColumnModel().getColumn(2).setCellEditor(table.getDefaultEditor(Boolean.class));

        // Добавляем таблицу в окно
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // Отображаем окно
        frame.setVisible(true);
    }

    // Класс модели таблицы
    static class PolynomialTableModel extends AbstractTableModel {
        private final Object[][] data;
        private final String[] columnNames;

        public PolynomialTableModel(Object[][] data, String[] columnNames) {
            this.data = data;
            this.columnNames = columnNames;
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            data[rowIndex][columnIndex] = value;
            fireTableCellUpdated(rowIndex, columnIndex);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 2; // Только третий столбец редактируем
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 2) {
                return Boolean.class; // Третий столбец содержит булевские значения
            }
            return String.class; // Остальные столбцы строки или числа
        }
    }
}
