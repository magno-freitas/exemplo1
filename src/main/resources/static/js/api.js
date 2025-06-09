// API endpoints configuration
const API_BASE_URL = '/api';

// API service object
const ApiService = {
    // Authentication
    login: async (credentials) => {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(credentials)
        });
        return await response.json();
    },

    // Pets
    getAllPets: async () => {
        const response = await fetch(`${API_BASE_URL}/pets`);
        return await response.json();
    },

    getPetById: async (id) => {
        const response = await fetch(`${API_BASE_URL}/pets/${id}`);
        return await response.json();
    },

    createPet: async (petData) => {
        const response = await fetch(`${API_BASE_URL}/pets`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(petData)
        });
        return await response.json();
    },

    // Appointments
    getAllAppointments: async () => {
        const response = await fetch(`${API_BASE_URL}/appointments`);
        return await response.json();
    },

    createAppointment: async (appointmentData) => {
        const response = await fetch(`${API_BASE_URL}/appointments`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(appointmentData)
        });
        return await response.json();
    },

    // Error handling
    handleError: (error) => {
        console.error('API Error:', error);
        // Implement error handling UI feedback
    }
};