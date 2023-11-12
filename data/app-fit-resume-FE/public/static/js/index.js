import dashboard from "./views/dashboard.js";
import { initializeResumeAppScript } from './scripts/ResumeApp.js';

const navigateTo = url => {
    history.pushState(null, null, url);
    router();
};


const router = async () => {
    const routes = [
        { path: '/', view: dashboard }
        //{ path: '/resume-app', view: () => console.log('Resume App')},
        //{ path: '/resume-upload', view: () => console.log('Resume Upload')},
        //{ path: '/resume-status', view: () => console.log('Resume Status')},
        //{ path: '/chatgpt', view: () => console.log('OpenAI Assistant App')},
        //{ path: '/analytics', view: () => console.log('Analytic App')},
        //{ path: '/support', view: () => console.log('Support')},
        //{ path: '/settings', view: () => console.log('Settings')},
        //{ path: '/404', view: () => console.log('404')},
    ];


    const routeMatches = routes.map(route => {
        return {
            route: route,
            isMatch: location.pathname === route.path
        };
    });

    let match = routeMatches.find(routeMatch => routeMatch.isMatch);

    if (!match) {
        match = {
            route: routes[8],  
            isMatch: true
        };
    }

    //const view = new match.route.view();
    //inject dashboard etc. through view to query select app
    //document.querySelector('#app').innerHTML = await view.getHtml();
    const view = new match.route.view();
    const mainContainer = document.querySelector('#app');
    

    mainContainer.innerHTML = await view.getHtml();
    view.executeScripts(mainContainer);
    
    if (match.route.path === '/') {
        initializeResumeAppScript();
    }

    console.log(match.route.path);

};

window.addEventListener('popstate', router);

document.addEventListener("DOMContentLoaded", () => {
    document.body.addEventListener("click", e => {
        if(e.target.matches("[data-link]")){
            e.preventDefault();
            navigateTo(e.target.href);
        }
    });

    router();
});



