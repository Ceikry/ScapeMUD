package gui

import Entity.Player
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.Font
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.io.File
import javax.swing.*

class Login : JFrame() {
    var newResponse = false
    var makeNew = false
    var creating = false

    val f = JFrame()
    val greetingArea = JTextArea()
    val greetingScroll = JScrollPane(greetingArea)
    val inputField = JTextField()
    val enterListener = object : KeyListener {
        override fun keyTyped(p0: KeyEvent?) {
        }

        override fun keyPressed(p0: KeyEvent?) {
            if(p0?.keyCode == 10) {
                attemptLogin()
            }
        }

        override fun keyReleased(p0: KeyEvent?) {
        }

    }

    fun open() {
        val cp = contentPane
        val currFont: Font = cp.font
        cp.font = Font("monospaced", currFont.style, currFont.size)
        cp.layout = FlowLayout()

        greetingArea.isEditable = false
        greetingArea.lineWrap = true

        greetingScroll.preferredSize = Dimension(400,150)
        greetingScroll.autoscrolls = true
        cp.add(greetingScroll)

        cp.add(JLabel("Name: "))

        inputField.preferredSize = Dimension(100,20)
        inputField.isEditable = true
        inputField.addKeyListener(enterListener)
        cp.add(inputField)

        setSize(400,200)
        title = "ScapeSUD"
        GameConstants.sendWelcome()
        defaultCloseOperation = EXIT_ON_CLOSE
        refresh()
        isVisible = true

    }

    fun refresh() {
        greetingArea.text += GameConstants.textQueue
        GameConstants.textQueue = ""
    }

    fun attemptLogin(){
        if(newResponse){
            val response = inputField.text.toLowerCase()
            makeNew = response.contains("y")
            newResponse = false
            return
        }

        if(makeNew){
            GameConstants.addLine("OK, enter the name for the character you wish to make: ")
            refresh()
            makeNew = false
            creating = true
            return
        }

        if(creating){
            GameConstants.loadedPlayer = Player()
            GameConstants.gui.open(GameConstants.loadedPlayer!!)
            this.isVisible = false
            return
        }

        val playerName = inputField.text.toLowerCase().replace(" ","_") + ".json"
        if(File("data/saves/$playerName").exists()){
            login(playerName)
        } else {
            GameConstants.addLine("That character does not yet exist.")
            GameConstants.addLine("Would you like to create one? (Y/N)")
            newResponse = true
            refresh()
        }
    }

    fun login(path: String){

    }
}