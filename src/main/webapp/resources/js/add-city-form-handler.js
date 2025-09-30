document.querySelectorAll('.add-city-form').forEach(form => {
    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const cityName = form.dataset.cityName;
        const cityLongitude = parseFloat(form.dataset.cityLongitude);
        const cityLatitude = parseFloat(form.dataset.cityLatitude);
        const errorMessage = form.querySelector('.error-message');

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

            if (result.success) {
                errorMessage.textContent = result.message;
                errorMessage.style.color = 'green';
            } else {
                errorMessage.textContent = result.message;
                errorMessage.style.color = 'red';
            }
        } catch (err) {
            errorMessage.textContent = 'Ошибка сервера';
            errorMessage.style.color = 'red';
        }
    });
});