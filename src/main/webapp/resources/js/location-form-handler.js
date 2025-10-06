document.querySelectorAll('.location-form').forEach(form => {
    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const locationId = parseInt(form.dataset.locationId);
        const locationName = form.dataset.locationName;
        const locationLongitude = parseFloat(form.dataset.locationLongitude);
        const locationLatitude = parseFloat(form.dataset.locationLatitude);

        try {
            const response = await fetch(form.action, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    id: locationId,
                    location: locationName,
                    longitude: locationLongitude,
                    latitude: locationLatitude
                })
            });
            const result = await response.json();

            showAlert(result.message, result.success ? 'success' : 'danger');

            if (result.success) {
                const card = form.closest('.location-card');
                if (card) card.remove();
            }
        } catch (err) {
            showAlert('Ошибка сервера', 'danger');
        }
    });
});