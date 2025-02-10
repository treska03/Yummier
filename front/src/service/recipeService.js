const API_BASE_URL = '/api/v1';


const recipeService = {
    // Fetch all recipes
    getAllRecipes: async () => {
        try {
            const response = await fetch(`${API_BASE_URL}/`);
            if (!response.ok) {
                throw new Error('Failed to fetch recipes');
            }
            return await response.json();
        } catch (error) {
            console.error('Error fetching recipes:', error);
            throw error;
        }
    },

    // Get recipe by ID
    getById: async (id) => {
        try {
            const response = await fetch(`${API_BASE_URL}/${id}`, {
                method: 'GET',
            });
            if (!response.ok) {
                throw new Error('Failed to get recipe');
            }
            return response.json();
        } catch (error) {
            console.error('Error getting recipe:', error);
            throw error;
        }
    },

    // Add a new recipe
    addRecipe: async (recipeDto) => {
        try {
            const response = await fetch(`${API_BASE_URL}/`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(recipeDto),
            });
            if (!response.ok) {
                throw new Error('Failed to add recipe');
            }
            return await response.json();
        } catch (error) {
            console.error('Error adding recipe:', error);
            throw error;
        }
    },

    // Delete a recipe by ID
    deleteRecipe: async (id) => {
        try {
            const response = await fetch(`${API_BASE_URL}/${id}`, {
                method: 'DELETE',
            });
            if (!response.ok) {
                throw new Error('Failed to delete recipe');
            }
        } catch (error) {
            console.error('Error deleting recipe:', error);
            throw error;
        }
    }
};

export default recipeService;