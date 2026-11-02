package icc.pap.pl1.patrones.vista;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import icc.pap.pl1.patrones.decorator.Azucar;
import icc.pap.pl1.patrones.decorator.Crema;
import icc.pap.pl1.patrones.decorator.Pepperoni;
import icc.pap.pl1.patrones.decorator.Pollo;
import icc.pap.pl1.patrones.decorator.Queso;
import icc.pap.pl1.patrones.decorator.Tomate;
import icc.pap.pl1.patrones.modelo.Pedido;
import icc.pap.pl1.patrones.modelo.TipoIngrediente;
import icc.pap.pl1.patrones.modelo.TipoProducto;
import icc.pap.pl1.patrones.prototype.RegistroPrototipos;
import icc.pap.pl1.patrones.servicio.ServicioPedidos;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;

public class VentanaPedidos extends JFrame{
    private JComboBox<TipoProducto> cmbProducto;
    private JComboBox<TipoIngrediente> cmbIngrediente;
    private JButton btnCrearPedido;
    private JButton btnAgregarIngrediente;
    private JLabel lblIngrediente;
    private RegistroPrototipos registroPrototipos;
    private JCheckBox chkMostrarPrecio;
    private JLabel lblPrecioIngrediente;
    private JPanel pnlSelectorIngrediente;
    private ServicioPedidos servicioPedidos;
    private JTable tablaPedidos;
    private DefaultTableModel modeloTablaPedidos;

    public VentanaPedidos(RegistroPrototipos registroPrototipos){
        this.registroPrototipos = registroPrototipos;
        this.servicioPedidos = new ServicioPedidos(registroPrototipos);

        modeloTablaPedidos = new DefaultTableModel(
            new Object[]{"ID", "Producto", "Composición", "TOTAL"}, 0){
                @Override
                public boolean isCellEditable(int fila, int columna){
                    return false;
                }
            };
        tablaPedidos =  new JTable(modeloTablaPedidos);

        setTitle("PedidoLab");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //salir al CERRAR
        setLocationRelativeTo(null);

        JPanel pnlPrincipal = new JPanel(new GridLayout(1, 3)); //1 FILA y 3 COLUMNAS

        JPanel pnlCrear = new JPanel();//
        pnlCrear.setLayout(new BoxLayout(pnlCrear, BoxLayout.Y_AXIS));

        JPanel pnlPedidos = new JPanel();
        pnlPedidos.setLayout(new BorderLayout());
        pnlPedidos.add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);

        lblIngrediente = new JLabel();


        JLabel lblProducto = new JLabel("1. Seleccionar un producto base");
        lblProducto.setFont(new Font("Consolas", Font.BOLD, 17));
        JLabel lblDescripcionProducto = new JLabel("Elige un producto para crear un nuevo pedido");
        lblDescripcionProducto.setFont(new Font("Consolas", Font.PLAIN, 13));
        lblIngrediente = new JLabel("2. Seleccionar un ingrediente");
        lblIngrediente.setFont(new Font("Consolas", Font.BOLD, 17));
        JLabel lblDescripcionIngrediente = new JLabel("Estos ingredientes seran añadidos a tu pedido");
        lblDescripcionIngrediente.setFont(new Font("Consolas", Font.PLAIN, 12));

        //alinear TITULOS y DESCRIPCIONES a la IZQUIERDA
        lblProducto.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblDescripcionProducto.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblIngrediente.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblDescripcionIngrediente.setAlignmentX(Component.LEFT_ALIGNMENT);

        // texto a la IZQUIERDA
        lblProducto.setHorizontalAlignment(SwingConstants.LEFT);
        lblDescripcionProducto.setHorizontalAlignment(SwingConstants.LEFT);
        lblIngrediente.setHorizontalAlignment(SwingConstants.LEFT);
        lblDescripcionIngrediente.setHorizontalAlignment(SwingConstants.LEFT);

        // ancho MAXIMO
        lblProducto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        lblDescripcionProducto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        lblIngrediente.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        lblDescripcionIngrediente.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        //selectores y BOTONES
        cmbProducto = new JComboBox<>(TipoProducto.values());
        btnCrearPedido = new JButton("Crear pedido");

        btnCrearPedido.addActionListener(e -> { //el boton CREAR PEDIDO añade el pedido A LA LISTA
            TipoProducto tipo = (TipoProducto) cmbProducto.getSelectedItem();

            try {
                Pedido nuevoPedido = servicioPedidos.crearPedido(tipo);
                modeloTablaPedidos.addRow(new Object[]{
                    nuevoPedido.id(),
                    nuevoPedido.tipo(),
                    nuevoPedido.descripcion(),
                    nuevoPedido.precio()
                });
            } catch (IllegalArgumentException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        cmbIngrediente = new JComboBox<>(TipoIngrediente.values());
        lblPrecioIngrediente = new JLabel(); //
        chkMostrarPrecio = new JCheckBox("Mostrar precio del ingrediente");

        pnlSelectorIngrediente = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlSelectorIngrediente.add(cmbIngrediente);
        pnlSelectorIngrediente.add(lblPrecioIngrediente);

        btnAgregarIngrediente = new JButton("Agregar ingrediente");

        cmbProducto.setAlignmentX(Component.LEFT_ALIGNMENT);
        cmbIngrediente.setAlignmentX(Component.LEFT_ALIGNMENT);

        //paneles de cada BOTON
        JPanel pnlBotonCrearPedido = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JPanel pnlBotonAgregarIngrediente = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        pnlBotonCrearPedido.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlBotonAgregarIngrediente.setAlignmentX(Component.LEFT_ALIGNMENT);

        //DIMENSIONES
        cmbProducto.setPreferredSize(new Dimension(200, 40));
        cmbProducto.setMaximumSize(new Dimension(200, 40));

        cmbIngrediente.setPreferredSize(new Dimension(200, 40));
        cmbIngrediente.setMaximumSize(new Dimension(200, 40));

        btnCrearPedido.setPreferredSize(new Dimension(280, 50));
        btnAgregarIngrediente.setPreferredSize(new Dimension(280, 40));

        btnCrearPedido.setFont(new Font("Consolas", Font.BOLD, 16));
        btnAgregarIngrediente.setFont(new Font("Consolas", Font.BOLD, 16));

        //agregar BOTONES a sus PANELES
        pnlBotonCrearPedido.add(btnCrearPedido);
        pnlBotonAgregarIngrediente.add(btnAgregarIngrediente);

        pnlCrear.add(Box.createVerticalStrut(20));
        pnlCrear.add(lblProducto);
        pnlCrear.add(lblDescripcionProducto);

        pnlCrear.add(Box.createHorizontalStrut(15));
        pnlCrear.add(cmbProducto);
        pnlCrear.add(Box.createHorizontalStrut(15));
        pnlCrear.add(pnlBotonCrearPedido);
        
        pnlCrear.add(Box.createVerticalStrut(25));

        pnlCrear.add(new JSeparator());
        pnlCrear.add(Box.createVerticalStrut(25));

        //seleccionar un INGREDIENTE
        pnlCrear.add(lblIngrediente);
        pnlCrear.add(lblDescripcionIngrediente);
        pnlCrear.add(Box.createVerticalStrut(15));
        pnlCrear.add(pnlSelectorIngrediente);
        pnlCrear.add(Box.createVerticalStrut(10));
        pnlCrear.add(chkMostrarPrecio);

        pnlCrear.add(Box.createHorizontalStrut(15));
        pnlCrear.add(pnlBotonAgregarIngrediente);

        //la tercera COLUMNA tiene dos SECCIONES
        JPanel pnlDetalles = new JPanel(new GridLayout(2, 1));
        JPanel pnlDetallePedido = new JPanel();
        JPanel pnlIngredientes = new JPanel();


        //TITULOS
        pnlCrear.setBorder(BorderFactory.createTitledBorder("Crear pedido"));
        pnlPedidos.setBorder(BorderFactory.createTitledBorder("Pedidos creados"));
        pnlDetallePedido.setBorder(BorderFactory.createTitledBorder("Detalle del pedido"));
        pnlIngredientes.setBorder(BorderFactory.createTitledBorder("Agregar ingredientes"));

        pnlDetalles.add(pnlDetallePedido);

        //agregar las COLUMNAS al PANEL PRINCIPAL
        pnlPrincipal.add(pnlCrear);
        pnlPrincipal.add(pnlPedidos);
        pnlPrincipal.add(pnlDetalles);


        chkMostrarPrecio.setSelected(false);
        lblPrecioIngrediente.setVisible(false);

        //MOSTRAR el PRECIO
        chkMostrarPrecio.addActionListener(e -> {
            lblPrecioIngrediente.setVisible(chkMostrarPrecio.isSelected());
        });

        cmbIngrediente.addActionListener(e -> actualizarPrecioIngrediente());
        actualizarPrecioIngrediente();

        add(pnlPrincipal);
    }

    public void probarVentana() {
        this.setVisible(true);
    }

    public BigDecimal obtenerPrecioIngrediente(TipoIngrediente tipoIngrediente){
        return switch (tipoIngrediente){
            case AZUCAR -> Azucar.precioIngredienteAzucar();
            case CREMA -> Crema.precioIngredienteCrema();
            case PEPPERONI -> Pepperoni.precioIngredientePepperoni();
            case POLLO -> Pollo.precioIngredientePollo();
            case QUESO -> Queso.precioIngredienteQueso();
            case TOMATE -> Tomate.precioIngredienteTomate();
        };
    }

    public void actualizarPrecioIngrediente(){
        TipoIngrediente ingrediente = (TipoIngrediente) cmbIngrediente.getSelectedItem(); //dice al COMPILADOR que lo debe TRATAR como TipoIngrediente
        if(ingrediente != null){
            BigDecimal precio = obtenerPrecioIngrediente(ingrediente);
            lblPrecioIngrediente.setText("$" + precio);
        }
    }


}
