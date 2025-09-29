document.querySelectorAll('.add-city-form').forEach(form => {
    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const cityId = form.dataset.cityId;
        const errorMessage = form.querySelector('.error-message');

        try {
            const response = await fetch('/locations/add', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({cityId})
            });
            const result = await response.json();

            if (result.success) {
                errorMessage.textContent = 'Добавлено!';
                errorMessage.style.color = 'green';
            } else {
                errorMessage.textContent = result.error;
                errorMessage.style.color = 'red';
            }
        } catch (err) {
            errorMessage.textContent = 'Ошибка сервера';
            errorMessage.style.color = 'red';
        }
    });
});