import React, {useEffect, useState} from 'react'
import { Link } from 'react-router-dom'
import { recipeData, deleteRecipe } from '../data/recipes'
import recipeService from '../service/recipeService'



interface Recipe {
  id: number;
  title: string;
  description: string;
  timeNeeded: number;
  difficulty: string;
  category: string;
  ingredients: string[];
  instruction: string[];
}



const RecipeList = () => {
  const handleDeleteWithoutApi = (id) => {
    if (window.confirm('Are you sure you want to delete this recipe?')) {
      deleteRecipe(id)
    }
  }

  const [recipes, setRecipes] = useState<Recipe[]>([]);
  useEffect(() => {
      const fetchRecipes = async () => {
          try {
              const data = await recipeService.getAllRecipes();
              setRecipes(data);
          } catch (error) {
              console.error('Error fetching recipes:', error);
          }
      };

      fetchRecipes();
  }, []);

  const handleDelete = async (id) => {
    try {
      if (window.confirm('Are you sure you want to delete this recipe?')) {
        await recipeService.deleteRecipe(id);
      }
        console.log('Recipe deleted');
    } catch (error) {
        console.error('Error deleting recipe:', error);
    }
};

  return (
    <div>
      <h2 className="mb-4 text-center">Our Favorite Recipes</h2>
      <div className="row">
        {/* Change recipes below to recipesData if u want to see mocked recipers from data/recipes.js file
            Its only implemented to see some recipes if the backend is not running
        */}
        {recipeData.map((recipe) => (
          <div key={recipe.id} className="col-md-4 mb-4">
            <div className="card h-100">
              <div className="card-body d-flex flex-column">
              <button
                    className="delete-button"
                    // onClick={() => handleDeleteWithoutApi(recipe.id)}
                    onClick={() => handleDelete(recipe.id)}
                  >
                    ✕
                  </button>
                <h5 className="card-title text-center mb-3">{recipe.title}</h5>
                <p className="card-text flex-grow-1">{recipe.description}</p>
                <div className="mt-3">
                  <p className="card-text">
                    <small className="text-muted">
                      ⏱️ {recipe.timeNeeded} min. • 
                      <span className="ms-2">
                        {recipe.difficulty === 'Easy' ? '👶' : '👨‍🍳'} {recipe.difficulty}
                      </span>
                    </small>
                  </p>
                  <Link
                    to={`/recipe/${recipe.id}`}
                    className="btn btn-primary w-100"
                  >
                    View Recipe
                  </Link>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}


export default RecipeList