package me.kacshuffle;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Label;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class GUI extends KeyAdapter{

    private JFrame frameOpposite;
    private JTable table;
    private DefaultTableModel defaultmodel;
    private Game game = new Game();


	private TableCellRenderer getRenderer() {
		return new DefaultTableCellRenderer(){
			int top = 3;
			int left = 3;
			int bottom = 3;
			int right = 3;
			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				Component tableCellRendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,row, column);
				if("jatekos".equals(value)){
					tableCellRendererComponent.setBackground(Color.red);
				} else if ("gamer".equals(value) || "l-gamer".equals(value) || "a-gamer".equals(value)){
					tableCellRendererComponent.setBackground(Color.blue);
				}else if(value == null){
					tableCellRendererComponent.setBackground(Color.white);
				} else if ("a".equals(value)) {
					this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, right, Color.black));
					tableCellRendererComponent.setBackground(Color.white);
				} else if ("l".equals(value)) {
					this.setBorder(BorderFactory.createMatteBorder(0, left, bottom, 0, Color.black));
					tableCellRendererComponent.setBackground(Color.white);
				} else if ("l-jatekos".equals(value)) {
					this.setBorder(BorderFactory.createMatteBorder(0, left, bottom, 0, Color.black));
					tableCellRendererComponent.setBackground(Color.red);
				}
				return tableCellRendererComponent;
			}
		};
	}

	private MouseListener tMouse = new MouseAdapter(){
		@Override
		public void mouseClicked(MouseEvent e){
			if(e.getButton() == MouseEvent.BUTTON1){
				game.setclickedPosition(table.rowAtPoint(e.getPoint()),table.columnAtPoint(e.getPoint()));
				game.step();
				fullTableRender();
			}
		}
	};

	@SuppressWarnings("serial")
	private void refreshDM(){
		table.setModel(new DefaultTableModel(game.getMap(),
				new String[] {
			"", "New column", "New column", "New column", "New column", "New column", "New column"
		}
				)
		{
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		});
	}

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI window = new GUI();
                    window.frameOpposite.setVisible(true);
                    window.table.addMouseListener(window.tMouse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

	private void fullTableRender(){
		this.refreshDM();
		table.repaint();
		for(int i = 0; i < table.getColumnCount(); i++)
			table.getColumnModel().getColumn(i).setCellRenderer(getRenderer());
	}

    /**
     * Create the application.
     */
    public GUI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frameOpposite = new JFrame();
        frameOpposite.setBounds(100, 100, 480, 480);
        frameOpposite.setTitle("Opposite Game");
        frameOpposite.setResizable(false);
        frameOpposite.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frameOpposite.getContentPane().add(panel, BorderLayout.CENTER);

        table = new JTable();
        Border outsideBorder = BorderFactory.createLoweredBevelBorder();
		Border insideBorder = BorderFactory.createRaisedBevelBorder();
		table.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setSelectionBackground(null);

		table.setBounds(20, 20, 420, 420);
		table.setRowHeight(60);

		panel.setLayout(null);
		panel.add(table);
		fullTableRender();
}


}