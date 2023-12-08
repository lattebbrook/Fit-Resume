import AbstractView from "../controller/AbstractView.js";

export default class extends AbstractView {
    constructor() {
        super();
        this.setTitle("Resume App");
    }

    async getHtml() {
        const response = await fetch('../../resume-app.html');
        const text = await response.text();
        return text;
    }

    // Call this method after the content is inserted into the DOM
    executeScripts(container) {
        // Find all script tags within the container
        const scripts = container.querySelectorAll('script');
        
        scripts.forEach((oldScript) => {
            const newScript = document.createElement('script');
            
            // Copy attributes to the new script tag
            Array.from(oldScript.attributes).forEach((attr) => {
                newScript.setAttribute(attr.name, attr.value);
            });
    
            // Set the script content for inline scripts
            if (!oldScript.src) {
                newScript.textContent = oldScript.textContent;
            }
    
            // Replace the old script with the new script element to execute it
            oldScript.parentNode.replaceChild(newScript, oldScript);
    
            // If the script has a 'src', set the 'src' last to ensure attributes are copied first
            if (oldScript.src) {
                newScript.src = oldScript.src;
            }
        });
    }
}
