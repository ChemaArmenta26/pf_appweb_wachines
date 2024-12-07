document.addEventListener('DOMContentLoaded', function() {
    window.history.pushState(null, '', window.location.href);
    window.addEventListener('popstate', function() {
        window.history.pushState(null, '', window.location.href);
    });

    const loginForm = document.querySelector('form');
    if (loginForm) {
        loginForm.reset();
    }

    window.onunload = function() {
        loginForm.reset();
    };
});


