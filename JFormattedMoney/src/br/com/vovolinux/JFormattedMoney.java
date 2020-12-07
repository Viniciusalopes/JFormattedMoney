/*
 *  ----------------------------------------------------------------------------------------------->
 *  Licença    : MIT - Copyright 2019 Viniciusalopes (Vovolinux) <suporte@vovolinux.com.br>
 *  Criado em  : 06/12/2020 22:12:11 
 *  Projeto    : JFormattedMoney
 *  Versão     : 1.0.2
 *  ------------------------------------------------------------------------------------------------
 *  Propósito  : (PT-BR) Fornecer componente JFormattedTextField pronto para implementação, em 
 *                       formato de valor numérico e/ou monetário.
 *               (EN-US) Provide JFormattedTextField component ready for implementation, in numerical 
 *                       and/or monetary value format.
 *  ------------------------------------------------------------------------------------------------
 *  Changelog:
 *  Author     : Vovolinux
 *  Date       : 2020-12-07
 *  Version    : 1.0.2
 *  Changes    : (PT-BR) Incluída a classe DecimalFormat.
 *
 *               (EN-BR) Included the DecimalFormat class.
 *  -----------------------------------------------------------------------------------------------| 
 *  Changelog:
 *  Author     : Vovolinux
 *  Date       : 2020-12-07
 *  Version    : 1.0.1
 *  Changes    : (PT-BR) Método KeyTyped alterado para não permitir a repetição de vírgula e de
 *                       sinal de negativo;
 *                       Criado o método toFloat() para retornar o valor do texto em float.
 *
 *               (EN-BR) KeyTyped method changed to not allow repetition of comma and negative sign;
 *                       Created the toFloat () method to return the float text value.
 *  -----------------------------------------------------------------------------------------------| 
 *  Changelog:
 *  Author     : Vovolinux
 *  Date       : YYYY-mm-dd
 *  Version    : n.n.n
 *  Changes    : (PT-BR) DUPLICAR ESTE BLOCO E DESCREVER A(s) ALTERAÇÃO(ões) RELEVANTES NA VERSÃO.
 *                       MANTER INDENTAÇÃO DE LINHAS ABAIXO DA PRIMEIRA LINHA E O LIMITE DE COLUNAS
 *                                                                                       ...AQUI -->
 *               (EN-BR) DUPLICATE THIS BLOCK AND DESCRIBE THE RELEVANT CHANGE (S) IN THE VERSION.
 *                       KEEP LINE INDENTATION UNDER THE FIRST LINE AND THE COLUMN LIMIT ...HERE -->
 *  -----------------------------------------------------------------------------------------------| 
 */
package br.com.vovolinux;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

/**
 *
 * @author vovolinux
 * @version 1.0.2
 * @see
 * <a href="https://github.com/Viniciusalopes/JFormattedMoney">github.com/Viniciusalopes/JFormattedMoney</a>
 */
public class JFormattedMoney extends javax.swing.JFormattedTextField {

    /**
     * Attributes Default values: String validChars = "-01234.567,89" String numberFormat =
     * "#,##0.00"
     */
    private String validChars = "-01234.567,89";
    private String numberFormat = "#,##0.00";
    
    // FONTE: https://javadevnotes.com/java-float-to-string-examples
    private DecimalFormat decimalFormat = new DecimalFormat(numberFormat);

    /**
     * Constructor
     */
    public JFormattedMoney() {
        super();
        initComponent();
    }

    /**
     * Get valid characters.
     *
     * @return
     */
    public String getValidChars() {
        return validChars;
    }

    /**
     * Convert this text to float.
     *
     * @return
     */
    public float toFloat() {
        return Float.parseFloat(this.getText().replace(".", "").replace(",", "."));
    }

    /**
     * Set up valid characters.
     *
     * @param validChars
     */
    public void setValidChars(String validChars) {
        this.validChars = validChars;
    }

    /**
     * Set up text from float value.
     *
     * @param value
     */
    public void setText(float value) {

        super.setText(decimalFormat.format(value));
    }

    /**
     * Initializes the component and includes the events.
     */
    private void initComponent() {

        super.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        super.setForeground(new java.awt.Color(102, 102, 102));
        super.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        super.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(this.numberFormat))));
        super.setPreferredSize(new Dimension(120, 29));
        super.setSize(new Dimension(120, 29));

        super.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                moneyFocusGained();
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                moneyFocusLost();
            }
        });
        super.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                moneyKeyTyped(evt);
            }
        });
        super.setText(decimalFormat.format(0));
    }

    /**
     * Event executed when gaining focus.
     */
    private void moneyFocusGained() {
        if (this.toFloat() == 0) {
            super.setText(null);
        }
    }

    /**
     * Typed event
     *
     * @param evt
     */
    private void moneyKeyTyped(KeyEvent evt) {
        char c = (char) evt.getKeyChar();
        for (char t : this.getText().toCharArray()) {
            if (t == ',' || t == '-') {
                evt.setKeyChar((char) KeyEvent.VK_CLEAR); // don't repeat , and -
                return;
            }
        }
        evt.setKeyChar(validChars.contains(c + "") ? c : (char) KeyEvent.VK_CLEAR);
    }

    /**
     * Event executed when losing focus.
     */
    private void moneyFocusLost() {
        if (super.getText().trim().equals("")) {
            super.setText(decimalFormat.format(0));
        }
    }
}
