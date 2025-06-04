// Load user's appointments
function loadAppointments() {
    fetch('/api/appointments/owner/' + getCurrentUserId())
        .then(response => response.json())
        .then(appointments => {
            const appointmentsList = document.getElementById('appointmentsList');
            appointmentsList.innerHTML = appointments.map(appointment => `
                <div class="appointment-card">
                    <h3>${appointment.serviceType}</h3>
                    <p>Pet: ${appointment.pet.name}</p>
                    <p>Date: ${new Date(appointment.appointmentTime).toLocaleString()}</p>
                    <p>Status: ${appointment.status}</p>
                    ${appointment.status === 'SCHEDULED' ? 
                        `<button onclick="cancelAppointment(${appointment.id})" class="btn btn-danger">Cancel</button>` : 
                        ''}
                </div>
            `).join('');
        })
        .catch(error => console.error('Error loading appointments:', error));
}

// Load user's pets
function loadPets() {
    fetch('/api/pets/owner/' + getCurrentUserId())
        .then(response => response.json())
        .then(pets => {
            const petsList = document.getElementById('petsList');
            petsList.innerHTML = pets.map(pet => `
                <div class="pet-card">
                    <h3>${pet.name}</h3>
                    <p>Species: ${pet.species}</p>
                    <p>Breed: ${pet.breed}</p>
                    <p>Birth Date: ${pet.dateOfBirth}</p>
                </div>
            `).join('');
        })
        .catch(error => console.error('Error loading pets:', error));
}

// Load user profile
function loadProfile() {
    fetch('/api/users/profile')
        .then(response => response.json())
        .then(user => {
            document.getElementById('profileFirstName').value = user.firstName;
            document.getElementById('profileLastName').value = user.lastName;
            document.getElementById('profilePhone').value = user.phone;
            document.getElementById('profileAddress').value = user.address;
        })
        .catch(error => console.error('Error loading profile:', error));
}

// Update profile
document.getElementById('profileForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const formData = new FormData(this);
    const data = {};
    formData.forEach((value, key) => data[key] = value);

    fetch('/api/users/profile', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) {
            alert('Profile updated successfully');
        } else {
            throw new Error('Failed to update profile');
        }
    })
    .catch(error => alert('Error updating profile: ' + error.message));
});

// Helper function to get current user ID (implement based on your auth system)
function getCurrentUserId() {
    // This should be implemented based on how you store the user ID
    // For example, you might get it from a data attribute in the HTML
    return document.body.getAttribute('data-user-id');
}

// Initialize dashboard
document.addEventListener('DOMContentLoaded', function() {
    loadAppointments();
    loadPets();
    loadProfile();
});