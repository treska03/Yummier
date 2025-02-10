import React, {useState, useEffect} from 'react'
import { useParams } from 'react-router-dom'
import recipeService from '../service/recipeService'
import capitalizeFirstLetter from '../helper/capitalize';



function RecipeDetail() {
  const { id } = useParams()
  const [recipe, setRecipes] = useState();
  useEffect(() => {
    const fetchRecipes = async () => {
        try {
          const data = await recipeService.getById(parseInt(id))
          setRecipes(data);
        } catch (error) {
          console.error('Error fetching recipes:', error);
        }
      };

      fetchRecipes();
  }, []);

  if (!recipe) {
    return <div>Recipe not found</div>
  }
  return (
    <div className="recipe-detail">
      <div className="text-center mb-4">
        <h2 className="mb-3">{recipe.title}</h2>
        <p className="lead">{recipe.description}</p>
        <div className="badge bg-light text-dark me-2">⏱️ {recipe.timeNeeded} min.</div>
        <div className="badge bg-light text-dark">
          {recipe.difficulty === 'EASY' ? '👶' : '👨‍🍳'} {capitalizeFirstLetter(recipe.difficulty.toLowerCase())} 
        </div>
      </div>
      
      <div className="row mt-4">
        <div className="col-md-6">
          <h3>Ingredients</h3>
          <ul className="list-group">
            {recipe.ingredients.map((ingredient, index) => (
              <li key={index} className="list-group-item">
                🥘 {ingredient}
              </li>
            ))}
          </ul>
        </div>
        
        <div className="col-md-6">
          <h3>Instructions</h3>
          <ol className="list-group list-group-numbered">
            {recipe.instructions.map((step, index) => (
              <li key={index} className="list-group-item">
                {step}
              </li>
            ))}
          </ol>
        </div>
      </div>
    </div>
  )
}

export default RecipeDetail