const categoryToEmoji = (category) => {
    switch (category) {
      case "LUNCH":
        return "🥪";
      case "BREAKFAST":
        return "🍳";
      case "DINNER":
        return "🍲";
      case "SNACK":
        return "🍩";
      default:
        return "This is a recipe of an unknown category.";
    }
  };
  export default categoryToEmoji