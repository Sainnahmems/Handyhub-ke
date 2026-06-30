package com.example.handyhubke

/**
 * HANDY HUB ASSIGNMENT - BIT4107 Week 8
 * 
 * This Kotlin implementation demonstrates Object-Oriented Programming (OOP) principles
 * such as Encapsulation, Modularity, and Responsibility Separation to handle
 * user inputs and gestures in a mobile application environment.
 */

// --- 1. INPUT HANDLING & VALIDATION ---

/**
 * KeyboardController: Simulates the hardware/software keyboard interface.
 * Responsible for capturing raw input strings.
 */
class KeyboardController {
    fun typeInput(fieldName: String, value: String): String {
        println("[Keyboard] Typing into $fieldName: \"$value\"")
        return value
    }
}

/**
 * LoginForm: Orchestrates the login process.
 * Uses KeyboardController for input and contains validation logic.
 */
class LoginForm(private val keyboard: KeyboardController) {
    
    fun validateCredentials(username: String, email: String): Boolean {
        println("[LoginForm] Commencing validation for user: $username")
        
        // Validation 1: Username length (must be at least 4 characters)
        val isUsernameValid = username.length >= 4
        
        // Validation 2: Email format check
        val isEmailValid = email.contains("@") && email.endsWith(".com")
        
        if (isUsernameValid && isEmailValid) {
            println("[LoginForm] SUCCESS: Credentials are valid.")
            return true
        } else {
            if (!isUsernameValid) println("[LoginForm] ERROR: Username too short!")
            if (!isEmailValid) println("[LoginForm] ERROR: Invalid email format!")
            return false
        }
    }

    fun submitLogin(username: String, email: String) {
        val capturedUser = keyboard.typeInput("Username", username)
        val capturedEmail = keyboard.typeInput("Email", email)
        
        if (validateCredentials(capturedUser, capturedEmail)) {
            println("[LoginForm] Logging in as $capturedUser...")
        } else {
            println("[LoginForm] Login aborted due to validation errors.")
        }
    }
}

// --- 2. GESTURE HANDLING ---

/**
 * GestureHandler: Handles standard touch interactions.
 * Responsible for taps and long presses on the UI.
 */
class GestureHandler {
    fun handleTap(elementName: String) {
        println("[Gesture] Tap detected on: $elementName. Action: Selecting/Opening.")
    }

    fun handleLongPress(elementName: String) {
        println("[Gesture] Long Press detected on: $elementName. Action: Opening Context Menu.")
    }
}

/**
 * SwipeController: Specialized class for handling directional swipe gestures.
 * Primarily used for navigation between service providers.
 */
class SwipeController {
    fun swipeLeft() {
        println("[Swipe] Swiped LEFT: Moving to NEXT service provider.")
    }

    fun swipeRight() {
        println("[Swipe] Swiped RIGHT: Returning to PREVIOUS service provider.")
    }
}

// --- 3. MASTER APPLICATION CLASS ---

/**
 * HandyHubApp: The orchestrator class (equivalent to MobileApp).
 * It instantiates all components and manages the overall application flow.
 */
class HandyHubApp {
    // Instantiate components
    private val keyboard = KeyboardController()
    private val loginForm = LoginForm(keyboard)
    private val gestures = GestureHandler()
    private val swipes = SwipeController()

    /**
     * Simulates a complete user journey from login to browsing handymen.
     */
    fun startAppSimulation() {
        println("==========================================")
        println("   WELCOME TO HANDY HUB SIMULATION   ")
        println("==========================================\n")

        // Step 1: User Login
        println("--- PHASE 1: User Authentication ---")
        loginForm.submitLogin("NickoleDev", "nickole@handyhub.com")
        println()

        // Step 2: Selecting a Category
        println("--- PHASE 2: Service Selection ---")
        gestures.handleTap("Plumbing Services Card")
        println()

        // Step 3: Browsing through Providers
        println("--- PHASE 3: Browsing Providers ---")
        swipes.swipeLeft()  // Moving to next
        swipes.swipeLeft()  // Moving to next again
        swipes.swipeRight() // Going back one
        println()

        // Step 4: Interacting with a Provider
        println("--- PHASE 4: Detailed Interaction ---")
        gestures.handleLongPress("Expert Plumber: John Doe")
        gestures.handleTap("Book Now Button")
        println()

        println("==========================================")
        println("   SIMULATION SUCCESSFULLY COMPLETED   ")
        println("==========================================")
    }
}

/**
 * Main execution point.
 */
fun main() {
    val app = HandyHubApp()
    app.startAppSimulation()
}
