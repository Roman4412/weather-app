document.querySelectorAll("[id^='btn-delete-']").forEach(button => {
    button.addEventListener("click", function () {
        const locationId = this.id.replace('btn-delete-', '');
        console.log(locationId);

        fetch(`/location?id=${locationId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => {
            if (response.ok) {
                window.location.href = '/weather';
            } else {
                alert("Ошибка при удалении");
            }
        }).catch(error => {
            console.error("Ошибка: ", error);
        });
    });
});