package sup.tampilan;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import sistem.Corel;
import sistem.Operator;
import tampilan.Form_Barang;
import tampilan.Form_Transaksi;

public class CustKeyListener implements KeyListener {

	public static final int NUMBER_ONLY = 0, ON_STOCK = 1;
	private int mode;
	private JTextField jf;
	private Form_Transaksi frmFormTrans;
	private Form_Barang frmFormBarang;
	private JButton btn;
	private Corel core;

	public CustKeyListener(Form_Transaksi frmFormTrans, JTextField jf,
			int mode) {
		this.frmFormTrans = frmFormTrans;
		this.jf = jf;
		this.mode = mode;
	}

	public CustKeyListener(Corel core, Form_Barang frm, JTextField jf,
			int mode) {
		this.core = core;
		this.frmFormBarang = frm;
		this.jf = jf;
		this.mode = mode;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent ev) {
		switch (mode) {
		case ON_STOCK:
			final int LIMIT = frmFormTrans.getSelectedBarang().getStok();
			if (jf.getText().equalsIgnoreCase("")) {

			} else if (Integer.parseInt(jf.getText()) > LIMIT) {
				jf.setText("" + LIMIT);
			}
			break;
		case NUMBER_ONLY:
			if (jf.getText().equalsIgnoreCase("")) {
				jf.setText("1");
			}
			break;
		}

	}

	public void keyTyped(KeyEvent ev) {
		switch (mode) {
		case NUMBER_ONLY:
			if (ev.getKeyChar() < '0' || ev.getKeyChar() > '9') {
				ev.consume();
			}
			break;
		case ON_STOCK:
			final int LIMIT = frmFormTrans.getSelectedBarang().getStok();
			if (jf.getText().equalsIgnoreCase("")) {

			} else if (Integer.parseInt(jf.getText()) > LIMIT) {
				ev.consume();
			}
			break;
		}

	}
}
