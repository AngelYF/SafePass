package por.ayf.eng.sp.view.comp;

import por.ayf.eng.sp.view.ViewMainWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JDialog with info about me.
 *
 * @author: Ángel Yagüe Flor.
 * @version: 2.0
 */

public class ComponentViewCreator extends JDialog {
    private static final long serialVersionUID = 1L;

    private final JPanel contentPanel = new JPanel();
    private JLabel lblIcon;
    private JLabel lblVersion;
    private JLabel lblAngel;
    private JLabel lblProyectText;
    private JButton btnAcept;

    public ComponentViewCreator(ViewMainWindow ventana, boolean modal) {
        super(ventana, modal);
        initComponents();
    }

    private void initComponents() {
        setTitle("Acerca del creador");
        setBounds(100, 100, 400, 200);
        setLocationRelativeTo(null);        // Center the view.
        setResizable(false);                // Cannot Resizable.
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        // Text:

        lblIcon = new JLabel("");
        lblIcon.setIcon(new ImageIcon(getClass().getResource("/images/icon.png")));
        lblIcon.setBounds(10, 11, 135, 145);
        contentPanel.add(lblIcon);

        lblProyectText = new JLabel("SafePass");
        lblProyectText.setBounds(155, 25, 215, 14);
        contentPanel.add(lblProyectText);

        lblAngel = new JLabel("Programado por Ángel Yagüe Flor");
        lblAngel.setBounds(155, 50, 215, 14);
        contentPanel.add(lblAngel);

        lblVersion = new JLabel("Versión 2.0");
        lblVersion.setBounds(155, 75, 215, 14);
        contentPanel.add(lblVersion);

        // Buttons:

        btnAcept = new JButton("Aceptar");
        btnAcept.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        btnAcept.setBounds(281, 128, 89, 23);
        contentPanel.add(btnAcept);
    }
}
