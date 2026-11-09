package icc.pap.pl1.patrones.vista;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
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
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import icc.pap.pl1.patrones.decorator.Azucar;
import icc.pap.pl1.patrones.decorator.Crema;
import icc.pap.pl1.patrones.decorator.DecoradorIngrediente;
import icc.pap.pl1.patrones.decorator.Pepperoni;
import icc.pap.pl1.patrones.decorator.Pollo;
import icc.pap.pl1.patrones.decorator.Queso;
import icc.pap.pl1.patrones.decorator.Tomate;
import icc.pap.pl1.patrones.modelo.Pedido;
import icc.pap.pl1.patrones.modelo.TipoIngrediente;
import icc.pap.pl1.patrones.modelo.TipoProducto;
import icc.pap.pl1.patrones.prototype.Cafe;
import icc.pap.pl1.patrones.prototype.Ensalada;
import icc.pap.pl1.patrones.prototype.Pizza;
import icc.pap.pl1.patrones.prototype.RegistroPrototipos;
import icc.pap.pl1.patrones.servicio.ServicioPedidos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private JButton btnReiniciarPedidos;
    private JLabel lblIdPedido;
    private JLabel lblProductoPedido;
    private JLabel lblIngredientesPedido;
    private JLabel lblTotalPedido;
    private JPanel pnlListaIngredientes;
    private JPanel pnlComposicionVisual;


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
        tablaPedidos.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 14));
        tablaPedidos.getTableHeader().setBackground(new Color(101, 67, 47));
        tablaPedidos.getTableHeader().setForeground(Color.WHITE);
        tablaPedidos.getTableHeader().setPreferredSize(new Dimension(0, 35));
        personalizarFilasTabla();

        tablaPedidos.setFont(new Font("Consolas", Font.PLAIN, 13));
        tablaPedidos.setRowHeight(32);
        tablaPedidos.setShowGrid(true);

        tablaPedidos.setGridColor(new Color(225, 211, 196));
        tablaPedidos.setSelectionBackground( new Color(218, 196, 174));
        tablaPedidos.setSelectionForeground(new Color(70, 48, 34));
        tablaPedidos.setFillsViewportHeight(true); //retirar EL ASPECTO ANTERIOR
        tablaPedidos.setIntercellSpacing(new Dimension(5, 3));

        setTitle("PedidoLab");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //salir al CERRAR
        setLocationRelativeTo(null);

        tablaPedidos.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()){
                int fila = tablaPedidos.getSelectedRow();
                if(fila != -1){
                    String idPedido = modeloTablaPedidos.getValueAt(fila, 0).toString();
                    Pedido pedido = servicioPedidos.buscarPedidoId(idPedido);
                    mostrarDetallePedido(pedido);
                    mostrarIngredientes(pedido);
                }
            }
        });

        JPanel pnlPrincipal = new JPanel(new GridLayout(1, 3)); //1 FILA y 3 COLUMNAS

        JPanel pnlCrear = new JPanel();//
        pnlCrear.setLayout(new BoxLayout(pnlCrear, BoxLayout.Y_AXIS));

        JPanel pnlPedidos = new JPanel();
        pnlPedidos.setLayout(new BorderLayout());
        pnlPedidos.add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);

        lblIngrediente = new JLabel();

        pnlListaIngredientes = new JPanel();
        pnlListaIngredientes.setLayout(new BoxLayout(pnlListaIngredientes, BoxLayout.Y_AXIS));


        btnAgregarIngrediente = new JButton("Agregar ingrediente");
        lblIdPedido = new JLabel("ID de pedido:");
        lblProductoPedido = new JLabel("Producto:");
        lblTotalPedido = new JLabel("TOTAL: $0.00");
        lblTotalPedido.setFont(new Font("Console", Font.BOLD, 18));

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
        btnReiniciarPedidos = new JButton("Reiniciar pedidos");
        btnReiniciarPedidos.setPreferredSize(new Dimension(280, 40));
        btnReiniciarPedidos.setFont(new Font("Consolas", Font.BOLD, 16));

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
        btnAgregarIngrediente.addActionListener(e ->  {
            int fila = tablaPedidos.getSelectedRow();
            if(fila == -1){
                JOptionPane.showMessageDialog(this, "Debe seleccionar un pedido");
                return;
            }
            String idPedido = modeloTablaPedidos.getValueAt(fila, 0).toString();
            TipoIngrediente ingrediente = (TipoIngrediente) cmbIngrediente.getSelectedItem();

            try{
                Pedido pedido = servicioPedidos.agregarIngredienteAPedido(idPedido, ingrediente);
                modeloTablaPedidos.setValueAt(pedido.descripcion(), fila, 2);
                modeloTablaPedidos.setValueAt(pedido.precio(), fila, 3);

                //actualizacion del PANEL de detalles 
                mostrarDetallePedido(pedido);
                mostrarIngredientes(pedido);

            } catch(IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        btnReiniciarPedidos.addActionListener(e -> {
            servicioPedidos.reiniciarPedidos();
            modeloTablaPedidos.setRowCount(0);
        });

        cmbIngrediente = new JComboBox<>(TipoIngrediente.values());
        lblPrecioIngrediente = new JLabel(); //
        chkMostrarPrecio = new JCheckBox("Mostrar precio del ingrediente");

        pnlSelectorIngrediente = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlSelectorIngrediente.add(cmbIngrediente);
        pnlSelectorIngrediente.add(lblPrecioIngrediente);


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
        pnlCrear.add(Box.createVerticalStrut(15));
        pnlCrear.add(btnReiniciarPedidos);

        //la tercera COLUMNA tiene dos SECCIONES
        JPanel pnlDetalles = new JPanel(new GridLayout(2, 1));
        JPanel pnlDetallePedido = new JPanel();
        pnlDetallePedido.setLayout(new BoxLayout(pnlDetallePedido, BoxLayout.Y_AXIS));
        JPanel pnlIngredientes = new JPanel();

        pnlIngredientes.setLayout(new BoxLayout(pnlIngredientes, BoxLayout.Y_AXIS));

        pnlListaIngredientes = new JPanel();

        pnlListaIngredientes.setLayout(new BoxLayout(pnlListaIngredientes, BoxLayout.Y_AXIS));

        //TITULOS
        TitledBorder bordeCrearPedido = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(181, 139, 108), 2),
        "Crear pedido",
        TitledBorder.LEFT,
        TitledBorder.TOP,
        new Font("Consolas", Font.BOLD, 20),
        new Color(101, 67, 47));

        pnlCrear.setBorder(bordeCrearPedido);
        Font fuenteTitulo = new Font("Consolas", Font.BOLD, 20);
        Color colorTitulo = new Color(101, 67, 47);
        Color colorBorde = new Color(181, 139, 108);

        pnlCrear.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(colorBorde, 2),
        "Crear pedido",
        TitledBorder.LEFT,
        TitledBorder.TOP,
        fuenteTitulo,
        colorTitulo));

        pnlPedidos.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(colorBorde, 2),
        "Pedidos creados",
        TitledBorder.LEFT,
        TitledBorder.TOP,
        fuenteTitulo, colorTitulo));

        pnlDetalles.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(colorBorde, 2),
        "Detalle del pedido",
        TitledBorder.LEFT,
        TitledBorder.TOP,
        fuenteTitulo, colorTitulo));

        pnlDetallePedido.add(lblIdPedido);
        pnlDetallePedido.add(lblProductoPedido);
        pnlComposicionVisual = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        pnlComposicionVisual.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblProductoPedido.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblIdPedido.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlDetallePedido.add(pnlComposicionVisual);


        pnlIngredientes.setBorder(BorderFactory.createTitledBorder("Agregar ingredientes"));

        pnlIngredientes.add(lblTotalPedido);

        pnlDetalles.add(pnlDetallePedido);
        pnlDetalles.add(pnlIngredientes);


        pnlDetallePedido.add(Box.createVerticalStrut(15));
        pnlDetallePedido.add(lblIdPedido);

        pnlDetallePedido.add(Box.createVerticalStrut(10));
        pnlDetallePedido.add(lblProductoPedido);

        pnlDetallePedido.add(Box.createVerticalStrut(15));
        pnlDetallePedido.add(pnlComposicionVisual);
   
        TitledBorder bordeIngredientes =BorderFactory.createTitledBorder("Ingredientes");

        bordeIngredientes.setTitleFont(new Font("Console", Font.PLAIN, 18));

        pnlIngredientes.setBorder(bordeIngredientes);

        pnlIngredientes.add(pnlListaIngredientes);
        pnlIngredientes.add(Box.createVerticalStrut(25));
        pnlIngredientes.add(lblTotalPedido);


        pnlIngredientes.add(lblTotalPedido);

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


        Dimension tamanoBoton = new Dimension(280, 40);

        btnCrearPedido.setPreferredSize(tamanoBoton);
        btnCrearPedido.setMaximumSize(tamanoBoton);

        btnAgregarIngrediente.setPreferredSize(tamanoBoton);
        btnAgregarIngrediente.setMaximumSize(tamanoBoton);

        btnReiniciarPedidos.setPreferredSize(tamanoBoton);
        btnReiniciarPedidos.setMaximumSize(tamanoBoton);

        pnlCrear.setBackground(new Color(245, 233, 220));
        pnlPedidos.setBackground(new Color(248, 243, 237));
        pnlDetalles.setBackground(new Color(239, 227, 214));

        tablaPedidos.setBackground(new Color(248, 243, 237));
        tablaPedidos.setFillsViewportHeight(true);

        Color fondoCrear = new Color(245, 233, 220);

        pnlCrear.setBackground(fondoCrear);
        pnlBotonCrearPedido.setBackground(fondoCrear);
        pnlBotonAgregarIngrediente.setBackground(fondoCrear);
        pnlSelectorIngrediente.setBackground(fondoCrear);
        chkMostrarPrecio.setBackground(fondoCrear);

                // Agregar los dos paneles a pnlDetalles
        pnlDetalles.add(pnlDetallePedido);
        pnlDetalles.add(pnlIngredientes);


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

    private void mostrarDetallePedido(Pedido pedido){
    lblIdPedido.setText("ID de pedido: " + pedido.id());

    // Obtener producto e ingredientes en orden
    List<Pedido> elementos = obtenerElementosPedido(pedido);

    // Mostrar únicamente la descripción del producto base
    lblProductoPedido.setText("Producto: " + elementos.get(0).descripcion());
    lblProductoPedido.setFont(new Font("Consolas", Font.BOLD, 20));

    // Limpiar composición anterior
    pnlComposicionVisual.removeAll();

    // Recorrer producto e ingredientes
    for (int i = 0; i < elementos.size(); i++) {

        Pedido elemento = elementos.get(i);

        // Agregar signo + entre los elementos
        if (i > 0) {
            JLabel lblMas = new JLabel("+");
            lblMas.setFont(new Font("Consolas", Font.BOLD, 25));
            pnlComposicionVisual.add(lblMas);
        }

        // Agregar imagen y nombre
        pnlComposicionVisual.add(crearPanelImagen(elemento));
    }

    pnlComposicionVisual.revalidate();
    pnlComposicionVisual.repaint();

    }

    private void mostrarIngredientes(Pedido pedido){
        pnlListaIngredientes.removeAll();
        Pedido actual = pedido;
        System.out.println("Pedido recibido: " + pedido.getClass().getSimpleName());

        while(actual instanceof DecoradorIngrediente decorador){
            System.out.println("Decorador encontrado: " + decorador.getClass().getSimpleName());

            JLabel lblIngrediente =  new JLabel(decorador.getClass().getSimpleName());
            lblIngrediente.setFont(new Font("Consolas", Font.PLAIN, 15));
            pnlListaIngredientes.add(lblIngrediente);
            actual = decorador.obtenerPedidoEnvuelto();
        }
        lblTotalPedido.setText("TOTAL: $" + pedido.precio());
        lblTotalPedido.setFont(new Font("Consola", Font.BOLD, 30));
        pnlListaIngredientes.revalidate();
        pnlListaIngredientes.repaint();
    }
    private void personalizarFilasTabla() {

    DefaultTableCellRenderer renderizador =
            new DefaultTableCellRenderer() {

        @Override
        public Component getTableCellRendererComponent(
                JTable tabla, Object valor,
                boolean seleccionado, boolean tieneFoco,
                int fila, int columna) {

            Component componente = super.getTableCellRendererComponent(
                    tabla, valor, seleccionado,
                    tieneFoco, fila, columna
            );

            if (seleccionado) {
                componente.setBackground(new Color(218, 196, 174));
                componente.setForeground(new Color(70, 48, 34));
            } else {
                componente.setBackground(
                        fila % 2 == 0
                                ? new Color(248, 243, 237)
                                : new Color(237, 228, 217)
                );

                componente.setForeground(new Color(70, 48, 34));
            }

            setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));

            return componente;
        }
    };

    for (int i = 0; i < tablaPedidos.getColumnCount(); i++) {
        tablaPedidos.getColumnModel()
                .getColumn(i)
                .setCellRenderer(renderizador);
    }
    }

    private List<Pedido> obtenerElementosPedido(Pedido pedido) {

    List<Pedido> elementos = new ArrayList<>();

    Pedido actual = pedido;

    // Recorrer los DECORADORES
    while (actual instanceof DecoradorIngrediente decorador) {

        elementos.add(actual);

        actual = decorador.obtenerPedidoEnvuelto();
    }

    elementos.add(actual);

    // Invertir el ORDEN
    Collections.reverse(elementos);

    return elementos;
    }
    //a partir del OBJETO devolver la RUTA DE LA IMAGEN
    private String obtenerRutaImagen(Pedido elementoPedido) {

    if (elementoPedido instanceof Cafe) {
        return "/imagenes/cafe.png";

    } else if (elementoPedido instanceof Ensalada) {
        return "/imagenes/ensalada.png";

    } else if (elementoPedido instanceof Pizza) {
        return "/imagenes/pizzasencilla.png";

    } else if (elementoPedido instanceof Azucar) {
        return "/imagenes/azucar.png";

    } else if (elementoPedido instanceof Crema) {
        return "/imagenes/crema.png";

    } else if (elementoPedido instanceof Tomate) {
        return "/imagenes/tomate.png";

    } else if (elementoPedido instanceof Pollo) {
        return "/imagenes/pollo.png";

    } else if (elementoPedido instanceof Queso) {
        return "/imagenes/queso.png";

    } else if (elementoPedido instanceof Pepperoni) {
        return "/imagenes/pepperoni.png";
    }

    return null;
    }
    private JPanel crearPanelImagen(Pedido elemento) {

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setOpaque(false);

    String ruta = obtenerRutaImagen(elemento);

    JLabel lblImagen = new JLabel();
    lblImagen.setAlignmentX(Component.CENTER_ALIGNMENT);

    if (ruta != null) {

        java.net.URL recurso = getClass().getResource(ruta);

        if (recurso != null) {

            ImageIcon icono = new ImageIcon(recurso);

            Image imagen = icono.getImage().getScaledInstance(
                110, 110, Image.SCALE_SMOOTH
            );

            lblImagen.setIcon(new ImageIcon(imagen));

        } else {
            lblImagen.setText("Imagen no encontrada");
        }

    } else {
        lblImagen.setText("Sin imagen");
    }

    JLabel lblNombre = new JLabel(elemento.getClass().getSimpleName());
    lblNombre.setFont(new Font("Consolas", Font.BOLD, 14));
    lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);

    panel.add(lblImagen);
    panel.add(lblNombre);

    return panel;
}

}
