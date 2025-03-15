import React from "react";
import HashLoader from "react-spinners/HashLoader";

export default function Spinner({
  active,
  text = "Loading...",
  loader: Loader,
  children,
}) {
  return (
    <div className="relative">
      <div
        className={`transition duration-300 ${
          active ? "pointer-events-none" : ""
        }`}
      >
        {children}
      </div>

      {active && (
        <div className="fixed inset-0 flex flex-col items-center justify-center z-50 backdrop-blur-xs bg-black bg-opacity-10">
          {Loader ? (
            <Loader size={50} color="#000000" />
          ) : (
            <HashLoader size={50} color="#000000" />
          )}
          <p className="mt-4 text-black text-lg font-semibold text-center">
            {text}
          </p>
        </div>
      )}
    </div>
  );
}
