import { Menu, MenuButton, MenuItem, MenuItems, Transition } from '@headlessui/react';
import {
  ArrowRightOnRectangleIcon,
  ChevronDownIcon,
  Cog6ToothIcon,
  UserIcon
} from '@heroicons/react/24/outline';
import { useState } from 'react';

const ProfileDropdown = ({ imageUrl }) => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <div className="relative">
      <Menu>
        <MenuButton
          className="flex items-center space-x-2 focus:outline-none"
          onClick={() => setIsOpen(!isOpen)}>
          <img
            src={imageUrl}
            alt="Profile"
            className="w-10 h-10 rounded-full border-2 border-gray-300 hover:border-blue-500 cursor-pointer"
          />
          <ChevronDownIcon className="w-5 h-5 text-gray-600" />
        </MenuButton>
        <Transition
          show={isOpen}
          enter="transition ease-out duration-100"
          enterFrom="transform opacity-0 scale-95"
          enterTo="transform opacity-100 scale-100"
          leave="transition ease-in duration-75"
          leaveFrom="transform opacity-100 scale-100"
          leaveTo="transform opacity-0 scale-95">
          <MenuItems className="absolute right-0 mt-2 w-48 bg-white border border-gray-200 rounded-lg shadow-lg py-2 z-50">
            <MenuItem>
              {({ active }) => (
                <button
                  className={`${
                    active ? 'bg-gray-100' : ''
                  } flex w-full items-center px-4 py-2 text-gray-700`}>
                  <UserIcon className="w-5 h-5 mr-3" /> Profile
                </button>
              )}
            </MenuItem>
            <MenuItem>
              {({ active }) => (
                <button
                  className={`${
                    active ? 'bg-gray-100' : ''
                  } flex w-full items-center px-4 py-2 text-gray-700`}>
                  <Cog6ToothIcon className="w-5 h-5 mr-3" /> Settings
                </button>
              )}
            </MenuItem>
            <MenuItem>
              {({ active }) => (
                <button
                  className={`${
                    active ? 'bg-red-100 text-red-600' : 'text-gray-700'
                  } flex w-full items-center px-4 py-2`}>
                  <ArrowRightOnRectangleIcon className="w-5 h-5 mr-3" /> Logout
                </button>
              )}
            </MenuItem>
          </MenuItems>
        </Transition>
      </Menu>
    </div>
  );
};

const Navbar = ({ imageUrl }) => {
  return (
    <nav className="sticky top-0 w-full bg-white shadow-md py-3 px-6 flex justify-between items-center z-50">
      <div className="text-xl font-semibold text-gray-800">Patizone</div>
      <ProfileDropdown imageUrl={imageUrl} />
    </nav>
  );
};

export default Navbar;
