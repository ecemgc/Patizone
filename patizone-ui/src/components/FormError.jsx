function FormError({ name, errors }) {
  function capitalize(s) {
    return String(s[0]).toUpperCase() + String(s).slice(1);
  }
  return (
    <>
      {errors[name]?.message ? (
        <p className="text-red-600 text-xs ">{capitalize(errors[name].message)}</p>
      ) : null}
    </>
  );
}

export default FormError;
