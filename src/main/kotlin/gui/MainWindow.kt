package gui

import Entity.Player
import GameConstants
import InputInterpreter
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.Font
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.*

class MainWindow : JFrame(){
    val f = JFrame()
    var textQueue = ""
    var player: Player? = null
    val inputField = JTextField()
    val outputField = JTextArea()
    val sendButton = JButton("Send")
    val scrollPane = JScrollPane(outputField,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)
    val enterListener = object : KeyListener{
        override fun keyTyped(p0: KeyEvent?) {
        }

        override fun keyPressed(p0: KeyEvent?) {
            if(p0?.keyCode == 10) {
                InputInterpreter.handle(inputField.text,player!!)
            }
        }

        override fun keyReleased(p0: KeyEvent?) {
        }

    }

    fun open(player: Player) {
        this.player = player
        val cp = contentPane
        val currFont: Font = cp.font
        cp.font = Font("monospaced", currFont.style, currFont.size)
        cp.layout = FlowLayout()

        outputField.isEditable = false
        outputField.lineWrap = true
        outputField.font = cp.font

        scrollPane.autoscrolls = true
        scrollPane.preferredSize = Dimension(500,400)
        cp.add(scrollPane)

        cp.add(JLabel("Enter command: "))

        inputField.preferredSize = Dimension(300,20)
        inputField.isEditable = true
        inputField.addKeyListener(enterListener)
        cp.add(inputField)

        sendButton.isDefaultCapable = true
        sendButton.preferredSize = Dimension(70,20)
        cp.add(sendButton)

        setSize(510,500)
        isVisible = true
        title = "ScapeSUD"
        defaultCloseOperation = EXIT_ON_CLOSE
        refresh()
    }

    fun refresh() {
        if(outputField.text.length > 10000){
            outputField.text = outputField.text.substring(1000)
        }
        if(inputField.text.isNotEmpty()){
            System.lineSeparator() + ">" + inputField.text
        }
        outputField.text += GameConstants.textQueue + System.lineSeparator() + "------------------------------------------------------"
        GameConstants.textQueue = ""
        GameConstants.gui.inputField.text = ""
    }
}