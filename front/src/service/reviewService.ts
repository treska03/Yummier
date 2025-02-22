const API_BASE_URL = '/api/v1/recipes';


const reviewService = {
    // Reviews section

    // Get all reviews for a specific review
    getReviews: async (recipeId: number, pageNumber: number) => {
        try {
            const response = await fetch(`${API_BASE_URL}/${recipeId}/reviews?page=${pageNumber}`);
            if (!response.ok) {
                throw new Error(`Failed to fetch reviews for recipe ${recipeId}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Error fetching reviews for ${recipeId}:`, error);
            throw error;
        }
    },

    // Add a review for a specific recipe
    addReview: async (recipeId: number, reviewDto) => {
        try {
            const response = await fetch(`${API_BASE_URL}/${recipeId}/reviews`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(reviewDto),
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
    deleteReview: async (recipeId: number, reviewId: number) => {
        try {
            const response = await fetch(`${API_BASE_URL}/${recipeId}/reviews/${reviewId}`, {
                method: 'DELETE',
            });
            if (!response.ok) {
                throw new Error('Failed to delete review');
            }
        } catch (error) {
            console.error('Error deleting review:', error);
            throw error;
        }
    }
}
export default reviewService;