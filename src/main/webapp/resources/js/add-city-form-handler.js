const globalAlert = document.querySelector('.global-alert');

function showAlert(message, type) {
    globalAlert.textContent = message;
    globalAlert.className = `global-alert alert alert-${type} text-center`;
    globalAlert.classList.remove('d-none');

    setTimeout(() => {
        globalAlert.classList.add('d-none');
    }, 1000);
}

document.querySelectorAll('.add-city-form').forEach(form => {
    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const cityName = form.dataset.cityName;
        const cityLongitude = parseFloat(form.dataset.cityLongitude);
        const cityLatitude = parseFloat(form.dataset.cityLatitude);

        try {
            const response = await fetch(form.action, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    name: cityName,
                    longitude: cityLongitude,
                    latitude: cityLatitude
                })
            });
            const result = await response.json();

            showAlert(result.message, result.success ? 'success' : 'danger');
        } catch (err) {
            showAlert('Ошибка сервера', 'danger');
        }
    });
});