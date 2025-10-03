const globalAlert = document.querySelector('.global-alert');

function showAlert(message, type) {
    globalAlert.textContent = message;
    globalAlert.className = `global-alert alert alert-${type} text-center`;
    globalAlert.classList.remove('d-none');

    setTimeout(() => {
        globalAlert.classList.add('d-none');
    }, 1000);
}