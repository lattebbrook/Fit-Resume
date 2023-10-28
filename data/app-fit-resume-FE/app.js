// Include the page.js library for client-side routing
const page = require('page');

// Define routes for each of the sections
page('/dashboard', () => { fetchContent('dashboard.html'); });
page('/resume-app', () => { fetchContent('resume-app.html'); });
//other route



// Default route: Load the dashboard content when accessing the base URL
page('/', () => { fetchContent('dashboard.html'); });

page.start();


//TODO