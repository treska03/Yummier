import React from 'react'
import { Link } from 'react-router-dom'

function Home() {

  return (
    <div className="home-hero text-center">
      <div className="cooking-animation">
        <span role="img" aria-label="cooking" className="recipe-icon">
          👩‍🍳
        </span>
      </div>
      <h1 className="display-4 mb-4">Welcome to Yummier!</h1>
      <p className="lead">
        Discover delicious recipes and bring warmth to your kitchen.
      </p>
      <hr className="my-4" />
      <p className="mb-4">
        Join us in exploring a world of flavors with our handpicked collection of 
        heartwarming recipes that bring people together.
      </p>
      <Link to="/recipes" className="btn btn-primary btn-lg">
        Start Cooking! 🍳
      </Link>
    </div>
  )
}

export default Home